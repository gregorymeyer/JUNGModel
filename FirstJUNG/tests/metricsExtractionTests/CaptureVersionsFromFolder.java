package metricsExtractionTests;

import static org.junit.Assert.*;
import metricsExtraction.VersionHandler;

import org.junit.Test;

public class CaptureVersionsFromFolder {
	
	@Test
	public void canVersionHandlerCaptureAllGraphsAndMetricsFromFolderIntoAContainer() throws Exception{
		VersionHandler versionHandler = new VersionHandler();
		assertTrue(versionHandler.createGraphsFromFolder("TestData/JUnit"));
	}

}
