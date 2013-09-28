package analyticsTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import graphML.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import metricsExtraction.GraphPopulator;
import analytics.GraphComparison;
import analytics.NodeChange;

public class ComparisonOfGraphs {
	
	@Test
	@Ignore
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
	@Ignore
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
	
	@Test
	@Ignore
	public void canReturnListOfNewNodesNotPresentInOldGraph() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/myfile.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/sorted.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph(); 
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
	}
	
	@Test
	@Ignore
	public void canReturnListOfNodesWithChangedDependenciesInNewGraph() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/myfile.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/sorted.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph(); 
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
	}
	
	@Test
	@Ignore
	public void canReturnListOfNodesWithIncreasedDependenciesInNewGraph() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/myfile.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/sorted.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph(); 
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
	}
	
	@Test
	@Ignore
	public void canReturnListOfNodesWithDecreasedDependenciesInNewGraph() throws Exception {
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/myfile.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("C:/Users/Etai/workspace/sorted.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph(); 
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
	}
	
	
	
	@Test
	public void shouldCorrectlyIdentifyIfAClassNodeHasChangedBetweenTwoGraphs() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("testData/TWRover_v1.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("testData/TWRover_v2.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph();
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
		GraphPopulator graphPopulator = new GraphPopulator();
		NodeChange Plateau = null;
		NodeChange Rover = null;
		
		graphPopulator.populate(oldGraph, "testData/TWRover_v1.xml");
		graphPopulator.populate(newGraph, "testData/TWRover_v2.xml");
		List<NodeChange> nodeChanges = graphComparison.nodeChanges();
		for(NodeChange nodeChange : nodeChanges)
		{
			if(nodeChange.getGMLid().equals("marsExploration.Plateau"))
				Plateau = nodeChange;
			else if(nodeChange.getGMLid().equals("marsExploration.Rover"))
				Rover = nodeChange;
			//System.out.println(nodeChange.getGMLid() + " - " + nodeChange.getNodeType());
		}
		
		assertTrue(Rover.hasChanged());
		assertTrue(Plateau.hasChanged());
	}
	
	@Test
	public void shouldCorrectlyIdentifyIfAClassNodeHasNotChangedBetweenTwoGraphs() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("testData/TWRover_v1.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("testData/TWRover_v2.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph();
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
		GraphPopulator graphPopulator = new GraphPopulator();
		NodeChange Location = null;
		
		graphPopulator.populate(oldGraph, "testData/TWRover_v1.xml");
		graphPopulator.populate(newGraph, "testData/TWRover_v2.xml");
		List<NodeChange> nodeChanges = graphComparison.nodeChanges();
		for(NodeChange nodeChange : nodeChanges)
		{
			if(nodeChange.getGMLid().equals("marsExploration.Location"))
				Location = nodeChange;
		}
		
		assertFalse(Location.hasChanged());
	}
	
	@Test
	public void shouldCorrectlyIdentifyThatANewClassNodeHasChanged() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("testData/TWRover_v1.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("testData/TWRover_v2.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph();
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
		GraphPopulator graphPopulator = new GraphPopulator();
		NodeChange Alien = null;
		
		graphPopulator.populate(oldGraph, "testData/TWRover_v1.xml");
		graphPopulator.populate(newGraph, "testData/TWRover_v2.xml");
		List<NodeChange> nodeChanges = graphComparison.nodeChanges();
		for(NodeChange nodeChange : nodeChanges)
		{
			if(nodeChange.getGMLid().equals("marsExploration.Alien"))
				Alien = nodeChange;
		}
		
		assertTrue(Alien.hasChanged());
	}
	
	@Test
	public void shouldCorrectlyIdentifyThatTheSamePackageNodeHasNotChanged() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("testData/TWRover_v1.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("testData/TWRover_v2.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph();
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
		GraphPopulator graphPopulator = new GraphPopulator();
		NodeChange MarsExploration = null;
		
		graphPopulator.populate(oldGraph, "testData/TWRover_v1.xml");
		graphPopulator.populate(newGraph, "testData/TWRover_v2.xml");
		List<NodeChange> nodeChanges = graphComparison.nodeChanges();
		for(NodeChange nodeChange : nodeChanges)
		{
			if(nodeChange.getGMLid().equals("marsExploration"))
				MarsExploration = nodeChange;
		}
		
		assertFalse(MarsExploration.hasChanged());
	}
	
	@Test
	public void shouldCorrectlyIdentifyThatADeletedClassNodeHasChanged() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("testData/TWRover_v2.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("testData/TWRover_v3.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph();
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
		GraphPopulator graphPopulator = new GraphPopulator();
		NodeChange Alien = null;
		
		graphPopulator.populate(oldGraph, "testData/TWRover_v2.xml");
		graphPopulator.populate(newGraph, "testData/TWRover_v3.xml");
		List<NodeChange> nodeChanges = graphComparison.nodeChanges();
		for(NodeChange nodeChange : nodeChanges)
		{
			if(nodeChange.getGMLid().equals("marsExploration.Alien"))
				Alien = nodeChange;
		}
		
		assertTrue(Alien.hasChanged());	
	}
	
	@Test
	public void shouldCorrectlyIdentifyThatADeletedPackageNodeHasChanged() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("testData/TWRover_v3.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("testData/TWRover_v4.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph();
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
		GraphPopulator graphPopulator = new GraphPopulator();
		NodeChange jupiterExploration = null;
		
		graphPopulator.populate(oldGraph, "testData/TWRover_v3.xml");
		graphPopulator.populate(newGraph, "testData/TWRover_v4.xml");
		List<NodeChange> nodeChanges = graphComparison.nodeChanges();
		for(NodeChange nodeChange : nodeChanges)
		{
			if(nodeChange.getGMLid().equals("jupiterExploration"))
				jupiterExploration = nodeChange;
		}
		
		assertTrue(jupiterExploration.hasChanged());	
	}
	
	@Test
	public void shouldCorrectlyIdentifyThatANewPackageNodeIsChanged() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("testData/TWRover_v2.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("testData/TWRover_v3.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph();
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
		GraphPopulator graphPopulator = new GraphPopulator();
		NodeChange jupiterExploration = null;
		
		graphPopulator.populate(oldGraph, "testData/TWRover_v2.xml");
		graphPopulator.populate(newGraph, "testData/TWRover_v3.xml");
		List<NodeChange> nodeChanges = graphComparison.nodeChanges();
		for(NodeChange nodeChange : nodeChanges)
		{
			if(nodeChange.getGMLid().equals("jupiterExploration"))
				jupiterExploration = nodeChange;
		}
		
		assertTrue(jupiterExploration.hasChanged());
	}
	
	@Test
	public void shouldCorrectlyIdentifyWhichNeighbouringNodesAlsoChangedBetweenGraphs() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("testData/TWRover_v2.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("testData/TWRover_v3.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph();
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
		GraphPopulator graphPopulator = new GraphPopulator();
		NodeChange Plateau = null;
		NodeChange Rover = null;
		NodeChange Location = null;
		
		graphPopulator.populate(oldGraph, "testData/TWRover_v2.xml");
		graphPopulator.populate(newGraph, "testData/TWRover_v3.xml");
		List<NodeChange> nodeChanges = graphComparison.nodeChanges();
		for(NodeChange nodeChange : nodeChanges)
		{
			if(nodeChange.getGMLid().equals("marsExploration.Plateau"))
				Plateau = nodeChange;
			else if(nodeChange.getGMLid().equals("marsExploration.Rover"))
				Rover = nodeChange;
			else if(nodeChange.getGMLid().equals("marsExploration.Location"))
				Location = nodeChange;
		}
		List<NodeChange> changedNeighbours =  Rover.getChangedNeighbours();
		for(NodeChange nodeChange : changedNeighbours)
		{
			System.out.println("Rover's changed neighbours are: " + nodeChange.getGMLid());
		}
	
		assertTrue(changedNeighbours.contains(Plateau));
		assertTrue(changedNeighbours.contains(Location));
		//assertFalse(changedNeighbours.contains(Plateau));
	}
	
	@Test
	public void shouldCorrectlyIdentifyWhichDeletedNeighbouringNodesChangedBetweenGraphs() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext oldGraphContext = graphManager.captureGraphMLFile("testData/TWRover_v2.graphml");
		GraphWrapper oldGraph = oldGraphContext.getGraph();
		GraphContext newGraphContext = graphManager.captureGraphMLFile("testData/TWRover_v3.graphml");
		GraphWrapper newGraph = newGraphContext.getGraph();
		GraphComparison graphComparison = new GraphComparison(oldGraph,newGraph);
		GraphPopulator graphPopulator = new GraphPopulator();
		NodeChange Location = null;
		NodeChange Alien = null;
		
		graphPopulator.populate(oldGraph, "testData/TWRover_v2.xml");
		graphPopulator.populate(newGraph, "testData/TWRover_v3.xml");
		List<NodeChange> nodeChanges = graphComparison.nodeChanges();
		for(NodeChange nodeChange : nodeChanges)
		{
			if(nodeChange.getGMLid().equals("marsExploration.Location"))
				Location = nodeChange;
			else if (nodeChange.getGMLid().equals("marsExploration.Alien"))
				Alien = nodeChange;
		}
		List<NodeChange> neighbours = Location.getChangedNeighbours();
		
		assertTrue(neighbours.contains(Alien));
	}
}
















