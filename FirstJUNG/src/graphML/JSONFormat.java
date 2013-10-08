package graphML;

import java.util.ArrayList;
import java.util.List;

import analytics.EdgeSummary;
import analytics.NodeSummary;

public class JSONFormat 
{
	
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
		int dummyInt = 0;
		EdgeSummary newEdgeSum = new EdgeSummary(newSource,newTarget,dummyInt,dummyInt);
		// Overwrite existing source & target
		edgeSum.updateSorTar(newEdgeSum);
	}
	
	public static void removeBadCharsInGMLids(NodeSummary nodeSum) 
	{
		// Create a dummy NodeSummary 
		String newGMLid = removeBadChars(nodeSum.getGMLid());
		int dummyInt = 0;
		String dummy = "dummy";
		NodeSummary newNodeSum = new NodeSummary(newGMLid,dummy,dummyInt,dummyInt);
		// Overwrite existing GMLid
		nodeSum.updateGMLid(newNodeSum);
	}
	
	private static String removeBadChars(String string)
	{
		String temp = string;
		temp = string.replaceAll("\\p{Punct}", "");
		return temp;
	}

	public static void createClassAndPackageNames(NodeSummary nodeSummary) 
	{	
		if(nodeSummary.getNodeType().equals("PACKAGENODE"))
			createPackageName(nodeSummary);
		else if(nodeSummary.getNodeType().equals("CLASSNODE"))
			createClassAndPackageName(nodeSummary);
	}

	private static void createClassAndPackageName(NodeSummary nodeSummary) 
	{
		String[] gmlID = nodeSummary.getGMLid().split("\\.");
		nodeSummary.setClassName(gmlID[gmlID.length-1]);
		
		String packageName = gmlID[0];
		for(int i = 1; i <= gmlID.length-2; i++) 
		{packageName = packageName.concat(gmlID[i]);}	 
		nodeSummary.setPackageName(packageName);
	}

	private static void createPackageName(NodeSummary nodeSummary) 
	{
		String[] gmlID = nodeSummary.getGMLid().split("\\.");
		String packageName = gmlID[0];
		for(int i = 1; i < gmlID.length; i++)
		{packageName = packageName.concat(gmlID[i]);}
		nodeSummary.setPackageName(packageName);
	}
}
