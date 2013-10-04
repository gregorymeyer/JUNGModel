package graphMLtests;



import static org.junit.Assert.*;

import java.util.List;

import graphML.GraphContext;
import graphML.GraphManager;
import graphML.GraphWrapper;
import graphML.JSONFormat;
import graphML.Vertex;

import org.junit.Test;

public class JSONFormatTests 
{
	@Test
	public void shouldRemoveAllBadChacartersFromNodeGMLIdsInAGraphWrapper() throws Exception
	{
		GraphManager graphManager = new GraphManager();
		GraphContext graphContext = graphManager.captureGraphMLFile("testData/Rover/0.graphml");
		GraphWrapper graph = graphContext.getGraph();
		
		JSONFormat.removeGMLidBadChars(graph);
		
		assertFalse(graph.getNode("marsExplorationRover").getProperty("GMLid").contains("."));
		assertFalse(graph.getNode("marsExplorationPlateau").getProperty("GMLid").contains("$"));
	}

}
