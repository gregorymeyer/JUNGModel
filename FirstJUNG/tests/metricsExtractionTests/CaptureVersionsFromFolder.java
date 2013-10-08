package metricsExtractionTests;

import static org.junit.Assert.*;
import metricsExtraction.VersionHandler;







import org.junit.Test;

import analytics.NodeSummary;

public class CaptureVersionsFromFolder {
	
	@Test
	public void canVersionHandlerCaptureAllGraphsAndMetricsFromFolderIntoAContainer() throws Exception{
		VersionHandler versionHandler = new VersionHandler();
		assertTrue(versionHandler.createGraphsFromFolder("TestData/Rover"));
	}
	
	@Test
	public void canVersionHandlerCaptureAllNodeChangeListsFromAllGraphComparisons() throws Exception{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		int comparisonSize = 3;
		assertEquals(comparisonSize, versionHandler.getNodeChangeList().size());
	}
	
	@Test
	public void shouldBeAbleToReturnAListOfEdgeSummaries() throws Exception
	{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateEdgeSummaryList();
		
		assertFalse(versionHandler.getEdgeSummaryList().isEmpty());
	}
	
	@Test
	public void shouldBeAbleToOutputNodeSummaryAndEdgeSummaryListsToJSON() throws Exception
	{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/JUnit");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateNodeSummaryList();
		versionHandler.createAndPopulateEdgeSummaryList();
		
		assertTrue(versionHandler.convertToJson());
	}
	
	@Test
	public void shouldBeAbleToAccuratelyIdentifyAPackageNode() throws Exception
	{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/JUnit");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateNodeSummaryList();
		
		NodeSummary nodeSummary = versionHandler.getNodeSummary("junit.awtui");
		
		assertEquals("PACKAGENODE",nodeSummary.getNodeType());
	}
	
	@Test
	public void shouldBeAbleToAccuratelyIdentifyAClassNode() throws Exception
	{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/JUnit");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateNodeSummaryList();
		versionHandler.createAndPopulateEdgeSummaryList();
		
		NodeSummary nodeSummary = versionHandler.getNodeSummary("junit.extensions.ActiveTestSuite");
		
		assertEquals("CLASSNODE",nodeSummary.getNodeType());
		assertTrue(versionHandler.convertToJson());
	}

}
