package metricsExtraction;

import graphML.GraphContext;
import graphML.GraphManager;
import graphML.GraphWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import analytics.GraphComparison;
import analytics.NodeChange;
import analytics.NodeSummary;


public class VersionHandler {
	
	List<String> graphMLList;
	List<String> xmlList;
	List<GraphWrapper> graphList = new ArrayList<>();
	List< List<NodeChange>> nodeChangeList = new ArrayList<>();
	List<NodeSummary> nodeSummaryList = new ArrayList<>();

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
					
					int eventIndex;
					if (nodeChange.isNew()){
						eventIndex = nodeChangeList.indexOf(changeList) + 1;
					}
					
					else {eventIndex = nodeChangeList.indexOf(changeList); }		
					NodeSummary nodeSummary = new NodeSummary(nodeChange.getGMLid(), 
													eventIndex);
					populateNodeSummary(nodeSummary);
					nodeSummaryList.add(nodeSummary);
				}
			}
		}
	}

<<<<<<< HEAD
	private NodeSummary populateNodeSummary(NodeSummary nodeSummary) {
		//Thought about using integer-indexed "for" loop starting from first
		//appearance but....meh
		Integer startPoint;
		//If node is not there from beginning then go back 1 version to translate to correct NodeChange
		//comparison version to get data.
		if (nodeSummary.getFirstAppearance() != 0){startPoint = nodeSummary.getFirstAppearance() - 1;}
		else {startPoint = nodeSummary.getFirstAppearance();}
			for (int i = startPoint; i < nodeChangeList.size(); i++){
				for (NodeChange nodeChange: nodeChangeList.get(i)){
					if (nodeChange.getGMLid().equals(nodeSummary.getGMLid())
							&& nodeChange.isDeleted()){
						
						nodeSummary.deletedAt(i);
					}
=======
	private void populateNodeSummary(NodeSummary nodeSummary) {
		findDeletedAt(nodeSummary);
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
>>>>>>> refs/heads/master
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

	private void findDeletedAt(NodeSummary nodeSummary) 
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

}
