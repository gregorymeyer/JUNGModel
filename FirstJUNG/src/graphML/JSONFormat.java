package graphML;

import java.util.ArrayList;
import java.util.List;

import analytics.EdgeSummary;
import analytics.NodeSummary;

public class JSONFormat 
{
	private static List<String> TLD = new ArrayList<>();

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
		String newSource = removeBadChars(removeTLD(edgeSum.getSourceGMLid().split("\\.")));
		String newTarget = removeBadChars(removeTLD(edgeSum.getTargetGMLid().split("\\.")));
		int dummyInt = 0;
		EdgeSummary newEdgeSum = new EdgeSummary(newSource,newTarget,dummyInt,dummyInt);
		// Overwrite existing source & target
		edgeSum.updateSorTar(newEdgeSum);
	}
	
	private static String removeTLD(String[] strArray) 
	{
		createTLDList();
		//String[] temp = gmlID.split("\\.");
		String ret = "";
		for(String string : strArray)
		{
			if(!TLD.contains(string))
				ret = ret + string + ".";
		}
		return ret;
	}

	public static void formatNodeSummary(NodeSummary nodeSum) 
	{
		// Split the GMLid
		String[] gmlID = nodeSum.getGMLid().split("\\.");
		// Remove TLD
		String newGMLid = removeTLD(gmlID);
		// Set class and package names
		if(nodeSum.getNodeType().equals("CLASSNODE"))
		{
			nodeSum.setClassName(getClassName(newGMLid));
			nodeSum.setPackageName(getPackageNameForClass(newGMLid));
		}
		else if(nodeSum.getNodeType().equals("PACKAGENODE"))
		{
			nodeSum.setPackageName(getPackageNameForPackage(newGMLid));
		}
		// Update the GMLid of the NodeSummary
		updateNodeSumGMLid(nodeSum,newGMLid);
	}
	
	private static void updateNodeSumGMLid(NodeSummary nodeSum,String newGMLid) 
	{
		int dummyInt = 0;
		String dummy = "dummy";
		NodeSummary newNodeSum = new NodeSummary(removeBadChars(newGMLid)
												,dummy,dummyInt,dummyInt);
		// Overwrite existing GMLid
		nodeSum.updateGMLid(newNodeSum);
	}

	private static String getPackageNameForPackage(String newGMLid) 
	{
		String[] temp = newGMLid.split("\\.");
		String ret = temp[0];
		for(int i = 1; i<temp.length; i++)
		{
			ret = ret.concat(temp[i]);
		}
		return removeBadChars(ret);
	}

	private static String getPackageNameForClass(String newGMLid) 
	{
		String[] temp = newGMLid.split("\\.");
		String ret = temp[0];
		for(int i = 1; i<temp.length-1; i++)
		{
			ret = ret.concat(temp[i]);
		}
		return removeBadChars(ret);
	}

	private static String getClassName(String newGMLid) 
	{
		String[] temp = newGMLid.split("\\.");
		String ret = removeBadChars(temp[temp.length-1]);
		return ret;
	}

	private static String removeBadChars(String string)
	{
		String temp = string;
		temp = string.replaceAll("\\p{Punct}", "");
		return temp;
	}

	private static void createTLDList() 
	{
		// Top Level Domain names to be removed from package names 
		TLD.add("com");
		TLD.add("org");
	}
}
