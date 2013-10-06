package graphMLtests;



import static org.junit.Assert.*;

import java.util.List;

import graphML.GraphContext;
import graphML.GraphManager;
import graphML.GraphWrapper;
import graphML.JSONFormat;
import graphML.Vertex;
import metricsExtraction.VersionHandler;

import org.junit.Test;

import analytics.EdgeSummary;
import analytics.NodeSummary;

public class JSONFormatTests 
{
	@Test
	public void shouldRemoveAllBadChacartersFromNodeGMLIdsInAGraphWrapper() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("testData/Rover/0.graphml");
		GraphWrapper graph = graphContext.getGraph();
		
		JSONFormat.removeGMLidBadChars(graph);
		
		assertFalse(graph.getNode("marsExplorationRover").getProperty("GMLid").contains("."));
		assertFalse(graph.getNode("marsExplorationPlateau").getProperty("GMLid").contains("$"));
	}
	
	@Test
	public void shouldRemoveAllBadCharactersFromSourceAndTargetInEdgeSummary() throws Exception
	{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateEdgeSummaryList();
		
		List<EdgeSummary> edgeSummaries = versionHandler.getEdgeSummaryList();
		for(EdgeSummary edgeSum : edgeSummaries) JSONFormat.removeBadCharsInSorTar(edgeSum);
		
		assertFalse(edgeSummaries.get(0).getSourceGMLid().contains("."));
		assertFalse(edgeSummaries.get(0).getTargetGMLid().contains("."));
		assertFalse(edgeSummaries.get(1).getSourceGMLid().contains("."));
		assertFalse(edgeSummaries.get(1).getTargetGMLid().contains("."));
	}

	@Test
	public void shouldRemoveAllBadCharactersInGMLidsOfNodeSummary() throws Exception
	{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateNodeSummaryList();
		versionHandler.createAndPopulateEdgeSummaryList();
		versionHandler.convertToJson();
		
		List<NodeSummary> nodeSummaries = versionHandler.getNodeSummaryList();
		for(NodeSummary nodeSum : nodeSummaries) JSONFormat.removeBadCharsInGMLids(nodeSum);
		
		assertFalse(nodeSummaries.get(0).getGMLid().contains("."));
		assertFalse(nodeSummaries.get(1).getGMLid().contains("."));
		assertFalse(nodeSummaries.get(2).getGMLid().contains("."));
		assertFalse(nodeSummaries.get(3).getGMLid().contains("."));
		assertFalse(nodeSummaries.get(4).getGMLid().contains("."));
		assertFalse(nodeSummaries.get(5).getGMLid().contains("."));
	}
}
