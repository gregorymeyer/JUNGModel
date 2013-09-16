package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import graphML.*;

public class CaptureGraphML {
	

	@Test
	public void graphHasListOfVertices() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("SortedGraphML.graphml");
		GraphWrapper graph = graphContext.getGraph();
		assertNotNull(graph.getNodes());
	}
	
	@Test
	public void graphHasListOfEdges() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("SortedGraphML.graphml");
		GraphWrapper graph = graphContext.getGraph();
		assertNotNull(graph.getEdges());
	}
	
	@Test
	public void graphKnowsSizeOfNodesList() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("SortedGraphML.graphml");
		GraphWrapper graph = graphContext.getGraph();
		assertEquals( 4, graph.getNodes().size());
	}
	
	@Test
	public void graphKnowsSizeOfEdgesList() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("SortedGraphML.graphml");
		GraphWrapper graph = graphContext.getGraph();
		assertEquals( 3, graph.getEdges().size());
	}
	
	@Test
	public void graphKnowsIfItContainsSpecificNode() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("SortedGraphML.graphml");
		GraphWrapper graph = graphContext.getGraph();
		assertTrue(graph.containsNode("AntTests.ClassA"));	
	}
	
	@Test
	public void graphKnowsIfItContainsAnEdgeLinkedToSpecificNode() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("SortedGraphML.graphml");
		GraphWrapper graph = graphContext.getGraph();
		assertTrue(graph.containsEdge("AntTests.ClassA"));	
	}
	
	@Test
	public void graphKnowsIfItContainsAnEdgeLinkedToTwoSpecificNodes() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("SortedGraphML.graphml");
		GraphWrapper graph = graphContext.getGraph();
		assertTrue(graph.containsEdge("AntTests.ClassA", "AntTests"));
	}
	

}
