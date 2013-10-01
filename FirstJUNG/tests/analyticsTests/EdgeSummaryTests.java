package analyticsTests;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import analytics.EdgeSummary;
import metricsExtraction.VersionHandler;

public class EdgeSummaryTests {
	
	@Test
	public void shouldKnowNumberOfTimesThatAnEdgesSourceChanges() throws Exception{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateEdgeSummaryList();
		
		EdgeSummary plateauToRoverSummary = versionHandler.getEdgeSummary("marsExploration.Plateau", "marsExploration.Rover");
		assertEquals(new Integer(2), plateauToRoverSummary.getSourceChangeCount());
		
	}
	
	@Test
	public void shouldKnowNumberOfTimesThatAnEdgesTargetChanges() throws Exception{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateEdgeSummaryList();
		
		EdgeSummary plateauToRoverSummary = versionHandler.getEdgeSummary("marsExploration.Plateau", "marsExploration.Rover");
		assertEquals(new Integer(2), plateauToRoverSummary.getTargetChangeCount());
		
	}
	
	@Test
	public void shouldKnowVersionOfFirstAppearance() throws Exception{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateEdgeSummaryList();
		
		EdgeSummary alienToLocationSummary = versionHandler.getEdgeSummary("marsExploration.Alien", "marsExploration.Location");
		EdgeSummary roverToLocationSummary = versionHandler.getEdgeSummary("marsExploration.Rover", "marsExploration.Location");
		assertEquals(1, alienToLocationSummary.getFirstAppearance());
		assertEquals(0, roverToLocationSummary.getFirstAppearance());

		
	}
	
	@Test
	public void shouldKnowToReturnNullForLastAppearanceIfEdgeExistsUntilFinalVersion() throws Exception{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateEdgeSummaryList();
		
		EdgeSummary locationToPlateauSummary = versionHandler.getEdgeSummary("marsExploration.Location", "marsExploration.Plateau");
		EdgeSummary alienToLocationSummary = versionHandler.getEdgeSummary("marsExploration.Alien", "marsExploration.Location");
		assertNull(locationToPlateauSummary.getLastAppearance());
		assertNotNull(alienToLocationSummary.getLastAppearance());
	}
	
	@Test
	public void shouldKnowVersionOfLastAppearance() throws Exception{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateEdgeSummaryList();
		
		EdgeSummary alienToLocationSummary = versionHandler.getEdgeSummary("marsExploration.Alien", "marsExploration.Location");
		EdgeSummary locationToPlateauSummary = versionHandler.getEdgeSummary("marsExploration.Location", "marsExploration.Plateau");
		assertNull(locationToPlateauSummary.getLastAppearance());
		assertEquals(new Integer(1), alienToLocationSummary.getLastAppearance());
	}
	
}
