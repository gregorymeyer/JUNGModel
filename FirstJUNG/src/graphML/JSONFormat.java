package graphML;

import java.util.ArrayList;
import java.util.List;

import analytics.EdgeSummary;

public class JSONFormat 
{
	private static List<String> badCharacters =  new ArrayList<String>();
	
	public static void removeGMLidBadChars(GraphWrapper graph) 
	{	
		List<Vertex> nodes = graph.getNodes();
		// Remove bad characters from all nodes
		for(Vertex node : nodes)
		{
			// Overwrite the existing GMLid
			node.addData("GMLid", removeBadChars(node.getProperty("GMLid")));
		}
	}
	
	private static String removeBadChars(String string)
	{
		populateBadChacaters();
		String newString = string;
		for(String badChar : badCharacters)
		{
			if(string.contains(badChar))
			{
				newString = string.replace(badChar, "");
			}
		}
		return newString;
	}

	public static void removeBadCharsInSorTar(EdgeSummary edgeSum) 
	{
		String newSource = removeBadChars(edgeSum.getSourceGMLid());
		String newTarget = removeBadChars(edgeSum.getTargetGMLid());
		EdgeSummary newEdgeSum = new EdgeSummary(newSource,newTarget,edgeSum.getFirstAppearance());
		// Overwrite existing source & target
		edgeSum.updateSorTar(newEdgeSum);
	}
	
	private static void populateBadChacaters() 
	{
		badCharacters.add(".");
		badCharacters.add("$");
	}

}
