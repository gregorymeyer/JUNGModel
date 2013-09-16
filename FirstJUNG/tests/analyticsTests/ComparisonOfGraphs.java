package analyticsTests;

import static org.junit.Assert.*;
import graphML.*;

import org.junit.Test;

import analytics.GraphComparison;

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
		assertEquals( 7, graphComparison.nodeListDifference().size());
		assertEquals( 19, graphComparison.edgeListDifference().size());
	}
	
	@Test
	public void canReturnListOfNodesWhichExistInNewGraphButNotInOldGraph() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/myfile.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/sorted.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph(); 
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
		assertTrue(graphComparison.isNodeListPresentInNew(graphComparison.nodeListDifference()));
		//assertFalse(graphComparison.isNodeListUniqueToOld(graphComparison.nodeListDifference()));
	}
	
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

}
