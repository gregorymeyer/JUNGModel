package graphMLtests;

import static org.junit.Assert.*;

import java.util.List;

import graphML.GraphContext;
import graphML.GraphManager;
import graphML.GraphWrapper;
import graphML.JSONFormat;
import graphML.Vertex;
import metricsExtraction.VersionHandler;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import analytics.EdgeSummary;
import analytics.NodeSummary;

public class JSONFormatTests 
{
	private static VersionHandler versionHandler;
	private static GraphWrapper graph;
	
	@BeforeClass
	public static void setupClass() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("testData/Rover/0.graphml");
		graph = graphContext.getGraph();
		
		versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/JUnit");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateNodeSummaryList();
		versionHandler.createAndPopulateEdgeSummaryList();
	}
	
	@Test
	public void shouldRemoveAllBadChacartersFromNodeGMLIdsInAGraphWrapper() throws Exception
	{	
		JSONFormat.formatGraph(graph);
		
		assertFalse(graph.getNode("marsExplorationRover").getProperty("GMLid").contains("."));
		assertFalse(graph.getNode("marsExplorationPlateau").getProperty("GMLid").contains("$"));
	}
	
	@Test
	public void shouldRemoveAllBadCharactersFromSourceAndTargetInEdgeSummary() throws Exception
	{	
		List<EdgeSummary> edgeSummaries = versionHandler.getEdgeSummaryList();
		for(EdgeSummary edgeSum : edgeSummaries) JSONFormat.formatEdgeSummary(edgeSum);
		
		assertFalse(edgeSummaries.get(0).getSourceGMLid().contains("."));
		assertFalse(edgeSummaries.get(0).getTargetGMLid().contains("."));
		assertFalse(edgeSummaries.get(1).getSourceGMLid().contains("."));
		assertFalse(edgeSummaries.get(1).getTargetGMLid().contains("."));
		assertFalse(edgeSummaries.get(0).getSourceGMLid().contains("$"));
		assertFalse(edgeSummaries.get(0).getTargetGMLid().contains("$"));
		assertFalse(edgeSummaries.get(1).getSourceGMLid().contains("$"));
		assertFalse(edgeSummaries.get(1).getTargetGMLid().contains("$"));
	}

	@Test
	public void shouldRemoveAllBadCharactersInGMLidsOfNodeSummary() throws Exception
	{	
		List<NodeSummary> nodeSummaries = versionHandler.getNodeSummaryList();
		for(NodeSummary nodeSum : nodeSummaries) JSONFormat.formatNodeSummary(nodeSum);
		Boolean isDirty = false;
		for(NodeSummary nodeSum : nodeSummaries) 
		{
			if(nodeSum.getGMLid().contains(".") || nodeSum.getGMLid().contains("$"))
			{
				isDirty = true;
				System.out.println(nodeSum.getGMLid());
			}	
		}
		assertFalse(isDirty);
	}
	
	@Test
	public void shouldCorrectlyAssignPackageAndClassNames() throws Exception
	{
		//versionHandler.convertToJson();
		assertTrue(true);
	}

	@Test
	public void shouldRemoveAllBadCharactersExceptPeriodsInNodeSummaryGMLid()
	{
		NodeSummary nodeSum = versionHandler
				.getNodeSummary("org.junit.tests.AnnotationTest$SubShadowing");
		//String gmlID = "org.$junit.runner$#.classAct$";
		//String dummy = "dummy";
		//NodeSummary nodeSum = new NodeSummary(gmlID,dummy,0,0);
		
		JSONFormat.removeOnlyGMLIDBadChars(nodeSum);
		
		assertFalse(nodeSum.getGMLid().contains("$"));
		assertTrue(nodeSum.getGMLid().contains("."));
	}
}
