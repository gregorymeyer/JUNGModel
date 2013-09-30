package metricsExtractionTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import graphML.*;
import metricsExtraction.GraphPopulator;
import metricsExtraction.MetricsReader;

import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

public class CaptureOOMetrics {
	
	@Test
	public void shouldReturnAllMetricsForAnExistingNode() throws NumberFormatException, XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		List<String> metrics = new ArrayList<String>();
		metrics.add("SLOC");
		metrics.add("PuM");
		metrics.add("ProM");
		MetricsReader metricsReader = new MetricsReader();
		
		List<String> extractedMetrics = metricsReader.readMetrics("MockMetrics.xml", "marsExploration.Location", metrics);
		
		assertEquals(76,Double.parseDouble(extractedMetrics.get(0)),0.1);
		assertEquals(6,Double.parseDouble(extractedMetrics.get(1)),0.1);
		assertEquals(6,Double.parseDouble(extractedMetrics.get(2)),0.1);
	}
	
	@Test
	public void shouldReturnEmptyStringForANonExistantNode() throws NumberFormatException, XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		String nodeName = "ThePlanets.Jupiter";
		List<String> nodeMetrics = new ArrayList<String>();
		nodeMetrics.add("SLOC");
		MetricsReader metricsReader = new MetricsReader();
		assertTrue(metricsReader.readMetrics("MockMetrics.xml",nodeName,nodeMetrics).get(0).isEmpty());
	}
	
	@Test
	public void shouldReturnEmptyStringForANonExistantMetricOfAnExistantNode() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		List<String> nodeMetrics = new ArrayList<String>();
		nodeMetrics.add("YellowBaloons");
		MetricsReader metricsReader = new MetricsReader();
		assertTrue(metricsReader.readMetrics("MockMetrics.xml","marsExploration.Location",nodeMetrics).get(0).isEmpty());
	}
	
	@Test
	public void shouldCorrectlyExtractAndAssignMetricsToGraph() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("C:/Users/JSM/Desktop/JUnit-4.11.graphml");
		GraphWrapper graph = graphContext.getGraph();
		GraphPopulator graphPopulator = new GraphPopulator();
		
		graphPopulator.populate(graph,"C:/Users/JSM/Desktop/JUnit 4-11 OOMetrics.xml");
		
		//assertEquals(76,Double.parseDouble(graph.getNode("junit.extensions.ActiveTestSuite").getProperty("SLOC")),0.1);
		assertEquals(7,Double.parseDouble(graph.getNode("junit.extensions.ActiveTestSuite").getProperty("PuM")),0.1);
		assertEquals(0,Double.parseDouble(graph.getNode("junit.extensions.ActiveTestSuite").getProperty("ProM")),0.1);
	}
	
	@Test 
	public void shouldCorrectlyAssignNodeType() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("testData/TWRover_v1.graphml");
		GraphWrapper graph = graphContext.getGraph();
		GraphPopulator graphPopulator = new GraphPopulator();
		
		
		graphPopulator.populate(graph, "testData/TWRover_v1.xml");
	
		assertEquals(NodeType.PACKAGENODE.toString(),graph.getNode("marsExploration").getProperty("NodeType"));
		assertEquals(NodeType.CLASSNODE.toString(),graph.getNode("marsExploration.Plateau").getProperty("NodeType"));
		assertEquals(NodeType.CLASSNODE.toString(),graph.getNode("marsExploration.Rover").getProperty("NodeType"));
		assertEquals(NodeType.CLASSNODE.toString(),graph.getNode("marsExploration.Location").getProperty("NodeType"));
	}
}
