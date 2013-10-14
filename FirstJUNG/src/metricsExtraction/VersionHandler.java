package metricsExtraction;

import graphML.GraphContext;
import graphML.GraphManager;
import graphML.GraphWrapper;
import graphML.JSONFormat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;




import analytics.EdgeSummary;
import analytics.GraphComparison;
import analytics.NodeChange;
import analytics.NodeSummary;
import analytics.PackageStructure;
import analytics.Packing;


public class VersionHandler {
	
	List<String> graphMLList;
	List<String> xmlList;
	List<GraphWrapper> graphList = new ArrayList<>();
	List< List<NodeChange>> nodeChangeList = new ArrayList<>();
	List<NodeSummary> nodeSummaryList = new ArrayList<>();
	private List<EdgeSummary> edgeSummaryList = new ArrayList<>();
	private List<Packing> packageList = new ArrayList<>();

	public Boolean createGraphsFromFolder(String folderName) throws Exception 
	{	
		final File folder = new File(folderName);
		//getFilesFromFolder(folder);	
		int versionSize = folder.listFiles().length/2;
		createAndPopulateGraphList(versionSize, folderName);
		return !graphList.isEmpty();
	}
	
	private void createAndPopulateGraphList(int versionSize, String folderName) throws Exception 
	{
		for (Integer i = 0; i < versionSize; i++)
		{
			GraphManager graphManager = new GraphManager();
			GraphContext context = graphManager.captureGraphMLFile(folderName + "/" + 
										i.toString() + ".graphml");
			GraphWrapper graph = context.getGraph();
			GraphPopulator graphPopulator = new GraphPopulator();
			graphPopulator.populate(graph, folderName + "/" + 
											i.toString() + ".xml");
			graphList.add(graph);	
		}
	}

	public List< List<NodeChange>> getNodeChangeList() 
	{	
		return nodeChangeList;
	}

	public void createNodeChangeList() 
	{
		for (int i = 0; i < graphList.size() - 1; i++)
		{
			nodeChangeList.add(new GraphComparison(graphList.get(i), graphList.get(i+1))
									.nodeChanges());
		}
	}

	public void createAndPopulateNodeSummaryList() 
	{
		for (List<NodeChange> changeList: nodeChangeList)
		{
			for (NodeChange nodeChange: changeList)
			{
				if (!nodeSummaryExists(nodeChange))
				{
					int eventIndex = 0;
					if (nodeChange.isNew())
					{
						// It should appear in the upcoming version
						eventIndex = nodeChangeList.indexOf(changeList) + 1;
					}	
					NodeSummary nodeSummary = new NodeSummary(nodeChange.getGMLid(),
													nodeChange.getNodeType(),
													eventIndex,graphList.size());
					populateNodeSummary(nodeSummary);
					nodeSummary.calculateVersionProbList();
					nodeSummaryList.add(nodeSummary);
				}
			}
		}
	}

	private void populateNodeSummary(NodeSummary nodeSummary) 
	{
		findLastAppearance(nodeSummary);
		findChangeCount(nodeSummary); 
	}

	private void findChangeCount(NodeSummary nodeSummary) 
	{
		Integer startPoint;
		if(nodeSummary.getFirstAppearance() !=0){startPoint = nodeSummary.getFirstAppearance()-1;}
		else startPoint = nodeSummary.getFirstAppearance();
		
		for(int i = startPoint; i <= nodeSummary.getLastAppearance()-1; i++)
		{
			for(NodeChange nodeChange : nodeChangeList.get(i))
			{
				// Record that the node changed at the specific version
				if(nodeSummary.getGMLid().equals(nodeChange.getGMLid()) &&
						nodeChange.hasChanged())
				{
					nodeSummary.addVersionToChangeList(i+1);
					nodeSummary.setVersionDeltaSLOC(i,nodeChange.getSLOC());
				}
					
			}
		}
	}
	
	private void findSourceTargetChangeCount(EdgeSummary edgeSummary) 
	{
			Integer startPoint = 0;
			if(edgeSummary.getFirstAppearance() !=0){startPoint = edgeSummary.getFirstAppearance()-1;}
			
			for(int i = startPoint; i<= edgeSummary.getLastAppearance()-1; i++)
			{
				for(NodeChange nodeChange : nodeChangeList.get(i))
				{
					for(NodeChange predecessor: nodeChange.getPredecessors()){
						if(edgeSummary.getSourceGMLid().equals(predecessor.getGMLid()) 
								&& nodeChange.getGMLid().equals(edgeSummary.getTargetGMLid()))
						{	
							// Deletions not counted as changes
							if (nodeChange.hasChanged() && !nodeChange.isDeleted())
							{
								edgeSummary.addVersionToTargetChanges(i+1);
								if(predecessor.hasChanged() && !predecessor.isDeleted())
								{
									edgeSummary.addVersionToSourceAndTargetChanges(i+1);
								}
							}
						}
					}
					
				}
			}	
	}

	
	private void findLastAppearance(NodeSummary nodeSummary)  
	{
		Boolean isDeleted = false;
		// Find the last version that it was seen in
		for(int i = nodeSummary.getFirstAppearance(); i < nodeChangeList.size(); i++)
		{
			for (NodeChange nodeChange: nodeChangeList.get(i))
			{
				if (nodeChange.getGMLid().equals(nodeSummary.getGMLid())
						&& nodeChange.isDeleted())
				{
					isDeleted = true;
					nodeSummary.setLastAppearance(i);
				}
			}
		}
		if(!isDeleted)
			nodeSummary.setLastAppearance(graphList.size()-1);
	}
	
	private void findLastAppearance(EdgeSummary edgeSummary) 
	{
		if(!disappearedByDeletion(edgeSummary)) 
		{
			if(!disappearedByNoLink(edgeSummary))
			{
				edgeSummary.setLastAppearance(graphList.size()-1);
			}
		}
	}
		
	private Boolean disappearedByNoLink(EdgeSummary edgeSummary) 
	{
		Boolean isLinkPresent = false;
		for (int i = edgeSummary.getFirstAppearance(); i < nodeChangeList.size(); i++)
		{
			for (NodeChange nodeChange: nodeChangeList.get(i))
			{
				for (NodeChange predecessor: nodeChange.getPredecessors()){
					if (predecessor.getGMLid().equals(edgeSummary.getSourceGMLid())
							&& nodeChange.getGMLid().equals(edgeSummary.getTargetGMLid()))
					{
						isLinkPresent = true;
					}
				}		
			}
			if(!isLinkPresent) // Relationship no longer exists
			{
				// were last seen together a version ago
				edgeSummary.setLastAppearance(i-1); 
				return true;
			}
			isLinkPresent = false;
		}
		// Relationship does not end
		return false;
	}

	private Boolean disappearedByDeletion(EdgeSummary edgeSummary) 
	{
		Boolean doesDisappear = false;
		for (int i = edgeSummary.getFirstAppearance(); i < nodeChangeList.size(); i++){
			for (NodeChange nodeChange: nodeChangeList.get(i)){
				for (NodeChange predecessor: nodeChange.getPredecessors()){
					if (predecessor.getGMLid().equals(edgeSummary.getSourceGMLid())
							&& nodeChange.getGMLid().equals(edgeSummary.getTargetGMLid()))
					{
						// Check if either has been deleted
						if((predecessor.isDeleted() || nodeChange.isDeleted()))
						{
							//end the relationship
							edgeSummary.setLastAppearance(i);
							return true;
						}
					}
				}			
			}
		}
		return doesDisappear;
	}



	private boolean nodeSummaryExists(NodeChange nodeChange) {
		for (NodeSummary nodeSummary: nodeSummaryList){
			if (nodeSummary.getGMLid().equals(nodeChange.getGMLid())){
				return true;
			}
		}
		return false;
	}

	public NodeSummary getNodeSummary(String gmlid) {
		
		for (NodeSummary nodeSummary: nodeSummaryList){
			if (nodeSummary.getGMLid().equals(gmlid)){
				return nodeSummary;
			}
		}
		return null;
	}

	public void createAndPopulateEdgeSummaryList() 
	{
		for(List<NodeChange> comparisonList : nodeChangeList)
		{
			for(NodeChange nodeComparison : comparisonList)
			{
				if(isClassNode(nodeComparison))
					createEdgeSummary(nodeComparison,nodeChangeList.indexOf(comparisonList));
			}	
		}
	}
	
	private void createEdgeSummary(NodeChange nodeComparison, int comparisonNumber) 
	{
		for(NodeChange predecessorNodeComparison : nodeComparison.getPredecessors())
		{
			if(isClassNode(predecessorNodeComparison))
			{
				if(!edgeSummaryExists(nodeComparison,predecessorNodeComparison))
				{
					int versionOfAppearance = 0;
					// Neither are new
					if(!nodeComparison.isNew() && !predecessorNodeComparison.isNew())
					  versionOfAppearance = comparisonNumber;
					// Either is new
					else if(nodeComparison.isNew() || predecessorNodeComparison.isNew())
					versionOfAppearance = comparisonNumber + 1;
						
					EdgeSummary edgeSummary = new EdgeSummary(predecessorNodeComparison.getGMLid(),
							nodeComparison.getGMLid(),
							versionOfAppearance, graphList.size());
					
					populateEdgeSummary(edgeSummary);
					edgeSummary.calculateVersionProbList();
					edgeSummaryList.add(edgeSummary);
				}
			}
		}	
	}

	private boolean isClassNode(NodeChange nodeChange) 
	{
		if(nodeChange.getNodeType().equals("CLASSNODE"))
			return true;
		return false;
	}

	private void populateEdgeSummary(EdgeSummary edgeSummary)
	{
		findLastAppearance(edgeSummary);
		findSourceTargetChangeCount(edgeSummary);
	}
	
	private boolean edgeSummaryExists(NodeChange nodeChange, NodeChange predecessorNodeChange) 
	{
		for(EdgeSummary edgeSummary : edgeSummaryList)
		{
			if(edgeSummary.getSourceGMLid().equals(predecessorNodeChange.getGMLid()) && 
					edgeSummary.getTargetGMLid().equals(nodeChange.getGMLid()))
				return true;
		}
		return false;
	}

	public List<EdgeSummary> getEdgeSummaryList() 
	{
		return this.edgeSummaryList;
	}

	public EdgeSummary getEdgeSummary(String source,String target) {
		for (EdgeSummary edgeSummary: edgeSummaryList){
			if(edgeSummary.getSourceGMLid().equals(source) && edgeSummary.getTargetGMLid().equals(target))
				return edgeSummary;
		}
		return null;
	}
	
	public Boolean convertToJson()
	{
		Boolean completed = false;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		for(EdgeSummary edgeSummary : edgeSummaryList)
		{JSONFormat.formatEdgeSummary(edgeSummary);}
		for(NodeSummary nodeSummary: nodeSummaryList)
		{
			// Remove bad characters & set class and package names
			JSONFormat.formatNodeSummary(nodeSummary);
		}
		
		String nodeSummJson = gson.toJson(nodeSummaryList);
		String edgeSummJson = gson.toJson(edgeSummaryList);
		try
		{
			FileWriter writer = new FileWriter("JSONfiles/JUnit.json");
			writer.write("{\n\"nodes\": " + nodeSummJson + ",\n\"links\": " + edgeSummJson + "\n}");
			writer.close();
			completed = true;
		}
		catch (IOException e){e.printStackTrace();}
		
		String packageStructureJson = gson.toJson(packageList);
		try
		{
			FileWriter writer = new FileWriter("JSONfiles/JUnitPackages.json");
			writer.write(packageStructureJson);
			writer.close();
			completed = true;
		}
		catch (IOException e){e.printStackTrace();}
		
		return completed;
	}

	public List<NodeSummary> getNodeSummaryList() 
	{
		return this.nodeSummaryList;
	}

	public void createPackageStructure() {
		
		List< List<String>> nameHierarchyLists = new ArrayList<>(captureNameStructure()); 
		
			for(List<String> hierarchy: nameHierarchyLists){
					
					if(packageList.isEmpty()){
						packageList.add(new PackageStructure(hierarchy.get(0)));
					}
					
					Integer baseIndex = setBasePackage(hierarchy.get(0));
					Integer hierIndex = 1;
					
					searchAndInsert(packageList.get(baseIndex),hierarchy,hierIndex);
			}
				
				
	}
	
		private Integer setBasePackage(String name) {
			Integer basePoint = 0;
			Boolean isPresent = false;
			for(Packing basePackage: packageList){
				
				if(basePackage.getName().equals(name)) 
				{
						isPresent = true;
						return packageList.indexOf(basePackage);
				}
			}
			if(!isPresent)
			{
				packageList.add(new PackageStructure(name));
			}
			
			for(Packing basePackage: packageList){
				if(basePackage.getName().equals(name))
					basePoint = packageList.indexOf(basePackage);
			}
			return basePoint;
		}
	
	private void searchAndInsert(Packing p,
			List<String> hierarchy, Integer index) {
		
		if(index < hierarchy.size()){
			//Enter PackageStructure with same name
			if(p.containsName(hierarchy.get(index))){
				
				Integer nameIndex = p.getNameIndex(hierarchy.get(index));
				index +=1;
	
				searchAndInsert(p.getChildren().get(nameIndex),
									hierarchy, index);
			}
			//First add then enter the added PackageStructure
			else {
				
				if(Character.isUpperCase(hierarchy.get(index).charAt(0))
						|| hierarchy.get(index).equals("package-info")){
				    
					String gmlid = hierarchy.get(0);
					for(int i = 1; i < hierarchy.size(); i++){
						if(i == hierarchy.size() - 1){
							gmlid += "." + hierarchy.get(i) + ".";
						}
						else {
							gmlid += "."+ hierarchy.get(i);
						}
					}
					
					p.addClassToChildren(hierarchy.get(index), getNodeSummary(gmlid).getVersionProbabilities());
				}
				
				else{
				 p.addPackageToChildren(hierarchy.get(index));
				 
				 Integer nameIndex = p.getNameIndex(hierarchy.get(index));
				 index +=1;
				
				 searchAndInsert(p.getChildren().get(nameIndex),
										hierarchy, index);
				}
			}
			
		}
	}

	private List<List<String>> captureNameStructure(){
		
		List< List< String>> nameHierarchy = new ArrayList<>();
		
		for (NodeSummary nodeSummary: nodeSummaryList){
			JSONFormat.removeOnlyGMLIDBadChars(nodeSummary);
			String[] hierarchy = nodeSummary.getGMLid().split("\\.");
			List<String> tempList = new ArrayList<>();
			
			for(int i = 0; i < hierarchy.length; i++){
				tempList.add(hierarchy[i]);
			}
			nameHierarchy.add(tempList);
		}
		return nameHierarchy;
	}

}
