package graphML;

import java.util.ArrayList;
import java.util.List;

public class JSONFormat 
{
	private static List<String> badCharacters =  new ArrayList<String>();
	
	public static void removeGMLidBadChars(GraphWrapper graph) 
	{	
		populateBadChacaters();
		List<Vertex> nodes = graph.getNodes();
		// Remove bad characters from all nodes
		for(Vertex node : nodes)
		{
			String temp  = node.getProperty("GMLid");
			for(String badChar : badCharacters)
			{
				if(temp.contains(badChar))
				{
					String newGMLid = temp.replace(badChar, "");
					// Overwrite the existing GMLid value
					node.addData("GMLid", newGMLid);
				}
			}
		}
	}

	private static void populateBadChacaters() 
	{
		badCharacters.add(".");
		badCharacters.add("$");
	}
	

}
