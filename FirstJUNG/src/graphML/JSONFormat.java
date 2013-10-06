package graphML;

import java.util.ArrayList;
import java.util.List;

import analytics.EdgeSummary;
import analytics.NodeSummary;

public class JSONFormat 
{
	private static List<String> badCharacters =  new ArrayList<String>();
	//private static Character[] badCharacters =  new Character[10];
	
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

	public static void removeBadCharsInSorTar(EdgeSummary edgeSum) 
	{
		String newSource = removeBadChars(edgeSum.getSourceGMLid());
		String newTarget = removeBadChars(edgeSum.getTargetGMLid());
		EdgeSummary newEdgeSum = new EdgeSummary(newSource,newTarget,edgeSum.getFirstAppearance());
		// Overwrite existing source & target
		edgeSum.updateSorTar(newEdgeSum);
	}
	
	public static void removeBadCharsInGMLids(NodeSummary nodeSum) 
	{
		String newGMLid = removeBadChars(nodeSum.getGMLid());
		int dummyInt = 0;
		NodeSummary newNodeSum = new NodeSummary(newGMLid,dummyInt,dummyInt);
		// Overwrite existing GMLid
		nodeSum.updateGMLid(newNodeSum);
	}
	private static String removeBadChars(String string)
	{
		populateBadChacaters();
		String newString = string;
		newString = string.replaceAll("\\p{Punct}", "");
		return newString;
	}
	
	private static void populateBadChacaters() 
	{
		badCharacters.add(".");
		badCharacters.add("$");
	}

}
