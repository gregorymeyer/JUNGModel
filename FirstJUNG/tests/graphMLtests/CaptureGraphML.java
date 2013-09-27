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
		assertEquals( 10, graph.getNodes().size());
	}
	
	@Test
	public void graphKnowsSizeOfEdgesList() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("SortedGraphML.graphml");
		GraphWrapper graph = graphContext.getGraph();
		assertEquals( 22, graph.getEdges().size());
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
	
	@Test
	public void nodeEqualityIsEvaluatedOnValueNotIdentity() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("SortedGraphML.graphml");
		GraphWrapper graph = graphContext.getGraph();
		
		Vertex node1 = graph.getNode("AntTests.ClassA");
		Vertex node2 = graph.getNode("AntTests.ClassA");
		assertEquals(node1,node2);
	}
	
	@Test
	public void modifiedNodeEqualityIsEvaluatedOnValueNotIdentity() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("SortedGraphML.graphml");
		GraphWrapper graph = graphContext.getGraph();
		
		List<Vertex> nodes1 = graph.getNodes();
		List<Vertex> nodes2 = graph.getNodes();
		Vertex node1 = nodes1.get(3);
		Vertex node2 = nodes1.get(3);
		node1.addData("loc", new Integer(8).toString());
		//assertNotSame(node1,node2);
		assertEquals(node1,node2);
	}

	@Test
	public void shouldReturnNotNullWhenSearchedByByGMLidIfVertexExistsInGraph() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("SortedGraphML.graphml");
		GraphWrapper graph = graphContext.getGraph();
		
		assertNotNull(graph.getNode("AntTests.ClassA"));
	}
	
	@Test
	public void shouldReturnNullWhenSearchedByGMLidIfVertexDoesNotExistInGraph() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("SortedGraphML.graphml");
		GraphWrapper graph = graphContext.getGraph();
		
		assertNull(graph.getNode("aNonExistantNode"));
	}
	
	@Test
	public void graphWrapperCanWriteObjectDataToJsonFile() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("SortedGraphML.graphml");
		GraphWrapper graph = graphContext.getGraph();
		graph.removeBadCharacters();
		graph.convertToJson();
		assertNull(10);
	}
	
	@Test
	public void shouldRemoveBadCharactersInGMLidsOfAllGraphNodes() throws Exception
	{
		GraphManager graphManager =  new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("SortedGraphML.graphml");
		GraphWrapper graph = graphContext.getGraph(); 
		
		graph.removeBadCharacters();
		List<Vertex> nodes = graph.getNodes();
		
		assertFalse(nodes.get(0).getProperty("GMLid").contains("."));
		assertFalse(nodes.get(0).getProperty("GMLid").contains("$"));
	}
}
