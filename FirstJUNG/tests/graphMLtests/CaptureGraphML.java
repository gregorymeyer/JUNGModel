package graphMLtests;

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
	public void graphCanFindEdgeCount() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("SortedGraphML.graphml");
		GraphWrapper graph = graphContext.getGraph();
		assertEquals(graph.getEdgeCount(), graph.getEdges().size());
		//Vertex v1 = graph.getNodes().iterator().;
		//Vertex v2 = graph.getNodes().get(graph.getNodes())
		//Edge myEdge = graph.findEdge(v1,v2);
		//assertNotNull(myEdge);
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
		assertEquals( 11, graph.getNodes().size());
	}
	
	@Test
	public void graphKnowsSizeOfEdgesList() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/formatUTF8.graphml");
		GraphWrapper graph = graphContext.getGraph();
		assertEquals( 22, graph.getEdges().size());
	}
	
	@Test
	public void graphKnowsIfItContainsSpecificNode() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/formatUTF8.graphml");
		GraphWrapper graph = graphContext.getGraph();
		assertTrue(graph.containsNode("AntTests.ClassA"));	
	}
	
	@Test
	public void graphKnowsIfItContainsAnEdgeLinkedToSpecificNode() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/formatUTF8.graphml");
		GraphWrapper graph = graphContext.getGraph();
		assertTrue(graph.containsEdge("AntTests.ClassA"));	
	}
	
	@Test
	public void graphKnowsIfItContainsAnEdgeLinkedToTwoSpecificNodes() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/formatUTF8.graphml");
		GraphWrapper graph = graphContext.getGraph();
		assertTrue(graph.containsEdge("AntTests.ClassA", "AntTests"));
	}
	
	
	

}
