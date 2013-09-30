package metricsExtraction;

import graphML.GraphContext;
import graphML.GraphManager;
import graphML.GraphWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import analytics.GraphComparison;
import analytics.NodeChange;


public class VersionHandler {
	
	List<String> graphMLList;
	List<String> xmlList;
	List<GraphWrapper> graphList = new ArrayList<>();
	List< List<NodeChange>> nodeChangeList = new ArrayList<>();

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

}
