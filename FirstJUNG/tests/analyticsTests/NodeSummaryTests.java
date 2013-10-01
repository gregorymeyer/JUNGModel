package analyticsTests;

import static org.junit.Assert.*;

import org.junit.Test;

import analytics.NodeSummary;
import metricsExtraction.VersionHandler;

public class NodeSummaryTests {
	
	@Test
	public void canCorrectlyIdentifyVersionOfFirstAppearanceForNode() throws Exception{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateNodeSummaryList();
		NodeSummary alienSummary = versionHandler.getNodeSummary("marsExploration.Alien");
		NodeSummary locationSummary = versionHandler.getNodeSummary("marsExploration.Location");
		NodeSummary treeLifeSummary = versionHandler.getNodeSummary("jupiterExploration.TreeLife");
		
		assertEquals( 1, alienSummary.getFirstAppearance());
		assertEquals( 0, locationSummary.getFirstAppearance());
		assertEquals( 2, treeLifeSummary.getFirstAppearance());
	}
	
	@Test
	public void canCorrectlyIdentifyVersionOfDeletionForNode() throws Exception{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateNodeSummaryList();
		
		NodeSummary alienSummary = versionHandler.getNodeSummary("marsExploration.Alien");
		NodeSummary treeLifeSummary = versionHandler.getNodeSummary("jupiterExploration.TreeLife");
		assertEquals(new Integer(1) , alienSummary.getLastAppearance());
		assertEquals(new Integer(2), treeLifeSummary.getLastAppearance());
		assertEquals(new Integer(1) , alienSummary.getLastAppearance());
		assertEquals(new Integer(2), treeLifeSummary.getLastAppearance());
	}
	
	@Test
	public void canCorrectlyIdentifyNumberOfTimesNodeHasChanged() throws Exception{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
	}
	
	@Test
	public void returnsNullIfNodeIsNeverDeleted() throws Exception{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateNodeSummaryList();
		
		NodeSummary locationSummary = versionHandler.getNodeSummary("marsExploration.Location");
		assertNull(locationSummary.getLastAppearance());
		NodeSummary roverSummary = versionHandler.getNodeSummary("marsExploration.Rover");
		NodeSummary plateauSummary = versionHandler.getNodeSummary("marsExploration.Plateau");
		
		assertNull(locationSummary.getLastAppearance());
		assertNull(roverSummary.getLastAppearance());
		assertNull(plateauSummary.getLastAppearance());
	}
	
	@Test
	public void shouldFindCorrectNumberOfTimesANodeChanges() throws Exception
	{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateNodeSummaryList();
		
		NodeSummary locationSummary = versionHandler.getNodeSummary("marsExploration.Location");
		NodeSummary alienSummary = versionHandler.getNodeSummary("marsExploration.Alien");
		NodeSummary treeLifeSummary = versionHandler.getNodeSummary("jupiterExploration.TreeLife");
		
		assertEquals(new Integer(2), locationSummary.getChangeCount());
		assertEquals(new Integer(2), alienSummary.getChangeCount());
		assertEquals(new Integer(2), treeLifeSummary.getChangeCount());
	}
}
