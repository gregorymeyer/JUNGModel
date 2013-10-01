package metricsExtractionTests;

import static org.junit.Assert.*;
import metricsExtraction.VersionHandler;

import metricsExtraction.VersionHandler;

import org.junit.Test;

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
		assertEquals( comparisonSize, versionHandler.getNodeChangeList().size());
	}

}
