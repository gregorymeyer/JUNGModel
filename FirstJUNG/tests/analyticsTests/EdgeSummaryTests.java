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
		assertEquals(new Integer(3), plateauToRoverSummary.getSourceChangeCount());
	}
	
	@Test
	public void shouldKnowNumberOfTimesThatAnEdgesTargetChanges() throws Exception{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateEdgeSummaryList();
		
		EdgeSummary plateauToRoverSummary = versionHandler.getEdgeSummary("marsExploration.Plateau", "marsExploration.Rover");
		assertEquals(new Integer(3), plateauToRoverSummary.getSourceAndTargetChangeCount());
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
	
	@Test
	public void shouldKnowNumberOfTimesThatASourceAndTargetBothChange() throws Exception{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateEdgeSummaryList();
		
		EdgeSummary alienToLocationSummary = versionHandler.getEdgeSummary("marsExploration.Alien", "marsExploration.Location");
		EdgeSummary locationToPlateauSummary = versionHandler.getEdgeSummary("marsExploration.Location", "marsExploration.Plateau");
		assertEquals(new Integer (2),locationToPlateauSummary.getSourceAndTargetChangeCount());
		assertEquals(new Integer(1), alienToLocationSummary.getSourceAndTargetChangeCount());
	}
	
	@Test
	public void shouldKnowTheProbabilityThatTargetWillChangeIfSourceChanges() throws Exception{
		VersionHandler versionHandler = new VersionHandler();
		versionHandler.createGraphsFromFolder("TestData/Rover");
		versionHandler.createNodeChangeList();
		versionHandler.createAndPopulateEdgeSummaryList();
		
		EdgeSummary alienToLocationSummary = versionHandler.getEdgeSummary("marsExploration.Alien", "marsExploration.Location");
		EdgeSummary locationToPlateauSummary = versionHandler.getEdgeSummary("marsExploration.Location", "marsExploration.Plateau");
		EdgeSummary roverToLocationSummary = versionHandler.getEdgeSummary("marsExploration.Rover", "marsExploration.Location");
		assertEquals(new Double(1.0),locationToPlateauSummary.getChangePropagationProb());
		assertEquals(new Double(2.0/3.0), roverToLocationSummary.getChangePropagationProb());
		assertEquals(new Double(0.5), alienToLocationSummary.getChangePropagationProb());
		
	}
	
}
