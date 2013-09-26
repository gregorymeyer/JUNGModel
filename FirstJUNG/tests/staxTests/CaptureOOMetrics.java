package staxTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import graphML.*;

import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import xmlMetrics.GraphPopulator;
import xmlMetrics.StaxParser;

public class CaptureOOMetrics {
	
	@Test
	public void shouldReturnAllMetricsForANodeNamedByGMLid() throws NumberFormatException, XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		String nodeName = "AntTests.ClassA";
		List<String> nodeMetrics = new ArrayList<String>();
		nodeMetrics.add("CNCC");
		nodeMetrics.add("CNWC");
		nodeMetrics.add("SLOC");
		nodeMetrics.add("M");
		nodeMetrics.add("PuM");
		StaxParser staxParser = new StaxParser();
		assertEquals(6.0,Double.parseDouble(staxParser.readMetrics("MockMetrics.xml",nodeName,nodeMetrics).get(0)),0.1);
		assertEquals(2.0,Double.parseDouble(staxParser.readMetrics("MockMetrics.xml",nodeName,nodeMetrics).get(1)),0.1);
		assertEquals(8.0,Double.parseDouble(staxParser.readMetrics("MockMetrics.xml",nodeName,nodeMetrics).get(2)),0.1);
		assertEquals(2.0,Double.parseDouble(staxParser.readMetrics("MockMetrics.xml",nodeName,nodeMetrics).get(3)),0.1);
		assertEquals(2.0,Double.parseDouble(staxParser.readMetrics("MockMetrics.xml",nodeName,nodeMetrics).get(4)),0.1);
	}
	
	@Test
	public void shouldReturnEmptyStringForANonExistantNode() throws NumberFormatException, XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		String nodeName = "ThePlanets.Jupiter";
		List<String> nodeMetrics = new ArrayList<String>();
		nodeMetrics.add("SLOC");
		StaxParser staxParser = new StaxParser();
		assertTrue(staxParser.readMetrics("MockMetrics.xml",nodeName,nodeMetrics).get(0).isEmpty());
	}
	
	@Test
	public void shouldReturnEmptyStringForANonExistantMetricOfAnExistantNode() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		List<String> nodeMetrics = new ArrayList<String>();
		nodeMetrics.add("YellowBaloons");
		StaxParser staxParser = new StaxParser();
		assertTrue(staxParser.readMetrics("MockMetrics.xml","GregTest.Greg",nodeMetrics).get(0).isEmpty());
	}
	
	@Test
	public void shouldCorrectlyExtractAndAssignMetricsToGraph() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("SortedGraphML.graphml");
		GraphWrapper graph = graphContext.getGraph();
		GraphPopulator graphPopulator = new GraphPopulator();
		
		graphPopulator.populate(graph);
		
		graph.convertToJson();
		
		assertEquals(6.0,Double.parseDouble(graph.getNode("AntTests.ClassA").getProperty("CNCC")),0.1);
		assertEquals(2.0,Double.parseDouble(graph.getNode("AntTests.ClassA").getProperty("CNWC")),0.1);
		assertEquals(8.0,Double.parseDouble(graph.getNode("AntTests.ClassA").getProperty("SLOC")),0.1);
		assertEquals(2.0,Double.parseDouble(graph.getNode("AntTests.ClassA").getProperty("M")),0.1);
		assertEquals(2.0,Double.parseDouble(graph.getNode("AntTests.ClassA").getProperty("PuM")),0.1);
	}

}
