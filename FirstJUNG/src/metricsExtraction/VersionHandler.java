package metricsExtraction;

import graphML.GraphContext;
import graphML.GraphManager;
import graphML.GraphWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import analytics.EdgeSummary;
import analytics.GraphComparison;
import analytics.NodeChange;
import analytics.NodeSummary;


public class VersionHandler {
	
	List<String> graphMLList;
	List<String> xmlList;
	List<GraphWrapper> graphList = new ArrayList<>();
	List< List<NodeChange>> nodeChangeList = new ArrayList<>();
	List<NodeSummary> nodeSummaryList = new ArrayList<>();
	private List<EdgeSummary> edgeSummaryList = new ArrayList<>();

	public Boolean createGraphsFromFolder(String folderName) throws Exception {
		
		final File folder = new File(folderName);
		//getFilesFromFolder(folder);
		
		int versionSize = folder.listFiles().length/2;
		
		createAndPopulateGraphList(versionSize, folderName);
		
		

		return !graphList.isEmpty();
	}
	
	private void createAndPopulateGraphList(int versionSize, String folderName) throws Exception {
	
		for (Integer i = 0; i < versionSize; i++){
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

	public List< List<NodeChange>> getNodeChangeList() {
		
		return nodeChangeList;
	}

	public void createNodeChangeList() {
	
		for (int i = 0; i < graphList.size() - 1; i++){
			nodeChangeList.add(new GraphComparison(graphList.get(i), graphList.get(i+1))
									.nodeChanges());
		}
		
	}

	public void createAndPopulateNodeSummaryList() {
		for (List<NodeChange> changeList: nodeChangeList){
			for (NodeChange nodeChange: changeList){
				if (!nodeSummaryExists(nodeChange)){
					
					int eventIndex = 0;
					if (nodeChange.isNew()){
						eventIndex = nodeChangeList.indexOf(changeList) + 1;
					}	
					NodeSummary nodeSummary = new NodeSummary(nodeChange.getGMLid(), 
													eventIndex);
					populateNodeSummary(nodeSummary);
					nodeSummaryList.add(nodeSummary);
				}
			}
		}
	}

	private void populateNodeSummary(NodeSummary nodeSummary) {
		findLastAppearance(nodeSummary);
		findChangeCount(nodeSummary);
	}

	private void findChangeCount(NodeSummary nodeSummary) 
	{
		// If the node has a finite end appearance  
		if(nodeSummary.getLastAppearance() != null)
		{
			Integer startPoint;
			if(nodeSummary.getFirstAppearance() !=0){startPoint = nodeSummary.getFirstAppearance()-1;}
			else startPoint = nodeSummary.getFirstAppearance();
			
			for(int i = startPoint; i<= nodeSummary.getLastAppearance(); i++)
			{
				for(NodeChange nodeChange : nodeChangeList.get(i))
				{
					if(nodeSummary.getGMLid().equals(nodeChange.getGMLid()) &&
							nodeChange.hasChanged())
						nodeSummary.incrementChangeCount();
				}
			}
			
		}
		// If the node exists until the last version
		else
		{
			Integer startPoint;
			if(nodeSummary.getFirstAppearance() !=0){startPoint = nodeSummary.getFirstAppearance()-1;}
			else startPoint = nodeSummary.getFirstAppearance();
			
			for(int i = startPoint; i< nodeChangeList.size(); i++)
			{
				for(NodeChange nodeChange : nodeChangeList.get(i))
				{
					if(nodeSummary.getGMLid().equals(nodeChange.getGMLid()) &&
							nodeChange.hasChanged())
						nodeSummary.incrementChangeCount();
				}
			}
		}
	}
	
	private void findSourceTargetChangeCount(EdgeSummary edgeSummary) 
	{
		// If the node has a finite end appearance  
		if(edgeSummary.getLastAppearance() != null)
		{
			Integer startPoint = 0;
			if(edgeSummary.getFirstAppearance() !=0){startPoint = edgeSummary.getFirstAppearance()-1;}
			
			for(int i = startPoint; i<= edgeSummary.getLastAppearance(); i++)
			{
				for(NodeChange nodeChange : nodeChangeList.get(i))
				{
					for(NodeChange successor: nodeChange.getSuccessors()){
						if(edgeSummary.getSourceGMLid().equals(nodeChange.getGMLid()) 
								&& successor.getGMLid().equals(edgeSummary.getTargetGMLid()))
						{	
							if (nodeChange.hasChanged()){
								edgeSummary.incrementSourceChangeCount();
								if(successor.hasChanged()){
									edgeSummary.incrementTargetChangeCount();
								}
							}
						}
					}
					
				}
			}
			
		}
		// If the node exists until the last version
		else
		{
			Integer startPoint = 0;
			if(edgeSummary.getFirstAppearance() !=0){startPoint = edgeSummary.getFirstAppearance()-1;}
			
			for(int i = startPoint; i< nodeChangeList.size(); i++)
			{
				for(NodeChange nodeChange : nodeChangeList.get(i))
				{
					for(NodeChange successor: nodeChange.getSuccessors()){
						if(edgeSummary.getSourceGMLid().equals(nodeChange.getGMLid()) 
								&& successor.getGMLid().equals(edgeSummary.getTargetGMLid()))
						{	
							if (nodeChange.hasChanged()){
								edgeSummary.incrementSourceChangeCount();
								if(successor.hasChanged()){
									edgeSummary.incrementTargetChangeCount();
								}
							}
						}
					}
				}
			}
		}
	}

	private void findLastAppearance(NodeSummary nodeSummary) 
	{
		// Last version that it was seen in
		for (int i = nodeSummary.getFirstAppearance(); i < nodeChangeList.size(); i++){
			for (NodeChange nodeChange: nodeChangeList.get(i)){
				if (nodeChange.getGMLid().equals(nodeSummary.getGMLid())
						&& nodeChange.isDeleted()){
					
					nodeSummary.setLastAppearance(i);
				}
			}
		}
	}
	
	private void findLastAppearance(EdgeSummary edgeSummary) 
	{
		// Last version that it was seen in
		for (int i = edgeSummary.getFirstAppearance(); i < nodeChangeList.size(); i++){
			for (NodeChange nodeChange: nodeChangeList.get(i)){
				for (NodeChange successor: nodeChange.getSuccessors()){
					if (successor.getGMLid().equals(edgeSummary.getTargetGMLid())
							&& nodeChange.getGMLid().equals(edgeSummary.getSourceGMLid())
							&& (successor.isDeleted() | nodeChange.isDeleted())){
		
						edgeSummary.setLastAppearance(i);
					}
				}			
			}
		}
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
		for(List<NodeChange> changeList : nodeChangeList)
		{
			for(NodeChange nodeChange : changeList)
			{
				if(isClassNode(nodeChange))
					createEdgeSummary(nodeChange,nodeChangeList.indexOf(changeList));
			}	
		}
	}
	
	private void createEdgeSummary(NodeChange nodeChange, int comparisonNumber) 
	{
		for(NodeChange successorNodeChange : nodeChange.getSuccessors())
		{
			if(isClassNode(successorNodeChange ))
			{
				if(!edgeSummaryExists(nodeChange,successorNodeChange))
				{
					int eventIndex = 0;
					if (nodeChange.isNew() | successorNodeChange.isNew())
					{
						eventIndex = comparisonNumber + 1;
					}
					EdgeSummary edgeSummary = new EdgeSummary(nodeChange.getGMLid(), 
							successorNodeChange.getGMLid(),eventIndex);
					
					populateEdgeSummary(edgeSummary);
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
/*
	private void findSourceChangeCount(EdgeSummary edgeSummary) {
		for
			for(NodeChange successorNodeChange : nodeChange.getSuccessors())
			{
				if(isClassNode(successorNodeChange ))
				{
					if(!edgeSummaryExists(nodeChange,successorNodeChange))
					{
						int eventIndex = 0;
						if (nodeChange.isNew() | successorNodeChange.isNew())
						{
							eventIndex = comparisonNumber + 1;
						}
						EdgeSummary edgeSummary = new EdgeSummary(nodeChange.getGMLid(), 
								successorNodeChange.getGMLid(),eventIndex);
						
						populateEdgeSummary(edgeSummary);
						edgeSummaryList.add(edgeSummary);
					}
				}
			}	
		
	}
*/
	private boolean edgeSummaryExists(NodeChange nodeChange, NodeChange successorNodeChange) 
	{
		for(EdgeSummary edgeSummary : edgeSummaryList)
		{
			if(edgeSummary.getSourceGMLid().equals(nodeChange.getGMLid()) && 
					edgeSummary.getTargetGMLid().equals(successorNodeChange.getGMLid()))
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

}
