package analyticsTests;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import analytics.NodeSummary;
import metricsExtraction.VersionHandler;

public class NodeSummaryTests {
	
	@Test
	@Ignore
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
	@Ignore
	public void canCorrectlyIdentifyVersionOfLastAppearanceForNode() throws Exception{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateNodeSummaryList();
		
		NodeSummary alienSummary = versionHandler.getNodeSummary("marsExploration.Alien");
		NodeSummary treeLifeSummary = versionHandler.getNodeSummary("jupiterExploration.TreeLife");
		
		assertEquals(new Integer(1), alienSummary.getLastAppearance());
		assertEquals(new Integer(2), treeLifeSummary.getLastAppearance());
	}
	
	@Test
	@Ignore
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
	@Ignore
	public void shouldKnowTheVersionsInWhichTheNodeChanges() throws Exception
	{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateNodeSummaryList();
		
		NodeSummary locationSummary = versionHandler.getNodeSummary("marsExploration.Location");
		NodeSummary alienSummary = versionHandler.getNodeSummary("marsExploration.Alien");
		NodeSummary treeLifeSummary = versionHandler.getNodeSummary("jupiterExploration.TreeLife");
		
		assertTrue(locationSummary.getChangeVersionsList().contains(2));
		assertTrue(alienSummary.getChangeVersionsList().contains(1));
		assertFalse(treeLifeSummary.getChangeVersionsList().contains(0));
	}
	
	@Test
	@Ignore
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
	
	@Test
	@Ignore
	public void shouldBeAbleToReturnAListOfProbabilitiesForEachGraphVersion() throws Exception
	{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateNodeSummaryList();
		
		//NodeSummary locationSummary = versionHandler.getNodeSummary("marsExploration.Location");
		NodeSummary alienSummary = versionHandler.getNodeSummary("marsExploration.Alien");
		
		assertTrue(alienSummary.getVersionProbabilities().contains(0.0));
		assertEquals(4,alienSummary.getVersionProbabilities().size());
	}
	
	@Test
	@Ignore
	public void shouldBeAbleToReturnAListOfCorrectProbabilitiesForEachGraphVersion() throws Exception
	{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateNodeSummaryList();
		
		NodeSummary locationSummary = versionHandler.getNodeSummary("marsExploration.Location");
		NodeSummary alienSummary = versionHandler.getNodeSummary("marsExploration.Alien");
		
		assertTrue(alienSummary.getVersionProbabilities().contains(0.0));
		assertEquals(1.0, alienSummary.getVersionProbabilities().get(1), 0.1);
		assertEquals(0.3, locationSummary.getVersionProbabilities().get(2), 0.1);
	}
	
	@Test
	public void shouldExtractSLOCDeltaForEachVersionThatTheNodeChanged() throws Exception
	{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateNodeSummaryList();
		versionHandler.createAndPopulateEdgeSummaryList();
		versionHandler.createPackageStructure();
		versionHandler.convertToJson();
		
		NodeSummary nodeSum = versionHandler.getNodeSummary("junitframeworkTestFailure");
		
		assertFalse(nodeSum.getDeltaSLOCList().isEmpty());
	}
}
