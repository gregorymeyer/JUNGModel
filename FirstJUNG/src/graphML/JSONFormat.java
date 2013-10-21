package graphML;

import java.util.ArrayList;
import java.util.List;

import analytics.EdgeSummary;
import analytics.NodeSummary;

public class JSONFormat 
{
	private static List<String> TLD = new ArrayList<>();

	/**
	 * Removes all punctuation from the GMLids of graph nodes
	 * 
	 * @param graph whose GMLid is to be removed of punctuation
	 */
	public static void formatGraph(GraphWrapper graph) 
	{	
		List<Vertex> nodes = graph.getNodes();
		// Remove bad characters from all nodes
		for(Vertex node : nodes)
		{
			// Overwrite the existing GMLid
			node.addData("GMLid", removeBadChars(node.getProperty("GMLid")));
		}
	}

	/**
	 * Removes all punctuation as well as top-level domain names from an edge's source and target
	 * GMLid
	 * 
	 * @param edgeSum whose GMLid is to be rid of punctuation and TLD name
	 */
	public static void formatEdgeSummary(EdgeSummary edgeSum) 
	{
		// Remove all bad characters in source & target GMLids
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

	/**
	 * Removes all punctuation as well as top-level domain names from node GMLids. Also
	 * populates the 'className' and 'packageName' of the NodeSummary.
	 * 
	 * @param nodeSum whose GMLid is to be cleaned and whose class and  package names are
	 * to be populated.
	 */
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
		updateNodeSumGMLid(nodeSum,removeBadChars(newGMLid));
	}
	
	private static void updateNodeSumGMLid(NodeSummary nodeSum,String newGMLid) 
	{
		int dummyInt = 0;
		String dummy = "dummy";
		NodeSummary newNodeSum = new NodeSummary(newGMLid,dummy,dummyInt,dummyInt);
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

	/**
	 * Removes all punctuation of a NodeSummary's GMLid except for periods.
	 * 
	 * @param nodeSum whose GMLid is to be rid of punctuation
	 */
	public static void removeOnlyGMLIDBadChars(NodeSummary nodeSum) 
	{
		String[] temp = nodeSum.getGMLid().split("\\.");
		for(int i = 0; i<temp.length; i++)
		{
			temp[i] = temp[i].replace(temp[i], removeAllButPeriod(temp[i]));
		}
		updateNodeSumGMLid(nodeSum,removeTLD(temp));
	}

	private static String removeAllButPeriod(String string) 
	{
		String ret = string.replaceAll("[$%#@]", ""); 
		return ret;
	}
}
