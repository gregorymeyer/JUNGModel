package analyticsTests;

import static org.junit.Assert.*;

import java.util.List;

import graphML.*;

import org.junit.Ignore;
import org.junit.Test;

import metricsExtraction.GraphPopulator;
import analytics.GraphComparison;
import analytics.NodeChange;

public class ComparisonOfGraphs {
	
	@Test
	public void canDetermineIfNewGraphHasMoreNodesOrEdgesThanOldGraph() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/myfile.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/sorted.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph(); 
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
		assertTrue(graphComparison.isNodesGreaterInNew());
		assertTrue(graphComparison.isEdgesGreaterInNew());
	}
	
	@Test
	public void canDetermineDifferenceInNumberOfNodesOrEdgesBetweenGraphs() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/myfile.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/sorted.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph(); 
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
		assertEquals( 7, graphComparison.addedNodes().size());
		assertEquals( 19, graphComparison.addedEdges().size());
	}
	

	@Test
	@Ignore
	public void canReturnListOfNodeChangesBetweenGraphVersions() throws Exception {
		GraphManager graphManager = new GraphManager();
		//GraphContext oldGraphContext = graphManager.captureGraphMLFile("C:/Users/JSM/Desktop/JUnit 4-10.graphml");
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/oRover.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		//GraphContext newGraphContext = graphManager.captureGraphMLFile("C:/Users/JSM/Desktop/JUnit-4.11.graphml");
		GraphContext newGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/nRover.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph(); 
		
		//Integer oldSLOC = Integer.parseInt(oldGraph.getNode("name").getProperty("SLOC"));
		//Integer newSLOC = Integer.parseInt(oldGraph.getNode("name").getProperty("SLOC"));
		GraphPopulator graphPopulator = new GraphPopulator();
		graphPopulator.populate(oldGraph, "C:/Users/JSM/Desktop/JUnit 4-10 OOMetrics.xml");
		graphPopulator.populate(newGraph, "C:/Users/JSM/Desktop/JUnit 4-11 OOMetrics.xml");
		graphPopulator.populate(oldGraph, "C:/Users/Etai/workspace/oRoverOOMetrics.xml");
		graphPopulator.populate(newGraph, "C:/Users/Etai/workspace/nRoverOOMetrics.xml");
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
			
		List<NodeChange> nodeChangeList = graphComparison.nodeChanges();
	
		assertTrue(nodeChangeList.isEmpty());
		//assertEquals();
	
	}
/*
	
	
	@Test
	public void canReturnListOfNewNodesNotPresentInOldGraph() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/myfile.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/sorted.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph(); 
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
	}
	
	@Test
	public void canReturnListOfNodesWithChangedDependenciesInNewGraph() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/myfile.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/sorted.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph(); 
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
	}
	
	@Test
	public void canReturnListOfNodesWithIncreasedDependenciesInNewGraph() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/myfile.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/sorted.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph(); 
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
	}
	
	@Test
	public void canReturnListOfNodesWithDecreasedDependenciesInNewGraph() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/myfile.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/sorted.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph(); 
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
	}
	*/

}
