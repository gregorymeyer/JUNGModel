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
		
		assertEquals(new Integer(1) , alienSummary.getDeletedAt());
		//assertEquals(2, treeLifeSummary.getDeletedAt());
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
		assertNull(locationSummary.getDeletedAt());
		
	}
	
	
	
	
	
	

}
