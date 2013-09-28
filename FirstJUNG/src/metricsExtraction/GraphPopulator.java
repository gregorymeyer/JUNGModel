/**
 * GraphPopulator class
 * The class populates a graph object with metadata extracted from the associated xml
 * file
 * 
 * @author Greg Meyer & Etai Miller
 * @see MetricsReader
 * @version 0.1
 */

package metricsExtraction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import graphML.GraphContext;
import graphML.GraphManager;
import graphML.GraphWrapper;
import graphML.NodeType;
import graphML.Vertex;

public class GraphPopulator 
{
	final String loc = "SLOC";
	final String public_methods = "PuM";
	final String protected_methods = "ProM"; // Dependency Finder analyses private methods as protected
	MetricsReader metricsReader;
	
	/**
	 * The function to populate a graph's nodes with metadata 
	 * 
	 * @param graph GraphWrapper to be populated
	 * @param metricsFile String URI of XML file containing node metadata
	 */
	public void populate(GraphWrapper graph, String metricsFile)
	{	
		// Get node names by GMLid
		List<Vertex> nodes = graph.getNodes();
		List<String> nodeNames =  new ArrayList<String>();
		for(int i=0; i<nodes.size(); i++)
		{
			nodeNames.add(nodes.get(i).getProperty("GMLid"));
		}
		
		// Get metrics for each node
		List<String> metrics = new ArrayList<String>();
		metrics.add(loc);
		metrics.add(public_methods);
		metrics.add(protected_methods);
		
		Map<String,List<String>> nodeMetrics = new HashMap<String,List<String>>(); //better option to a HashMap?
		MetricsReader metricsReader =  new MetricsReader();
		for(int i=0; i<nodeNames.size(); i++)
		{
			try 
			{
				nodeMetrics.put(nodeNames.get(i),metricsReader.readMetrics(metricsFile, nodeNames.get(i), metrics));
			} 
			catch (XPathExpressionException | ParserConfigurationException
					| SAXException | IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		// Assign metrics to each node
		for(int i=0; i<nodes.size(); i++)
		{
			if(nodes.get(i).getProperty("GMLid").equals(nodeNames.get(i)))
			{
				// Get Node's metrics
				List<String> mets = nodeMetrics.get(nodeNames.get(i));
				
				for(int j=0; j<metrics.size(); j++)
				{
					nodes.get(i).addData(metrics.get(j),mets.get(j));
					//System.out.println("Added " + metrics.get(j) + "(" 
							//+ mets.get(j) + ") to " + nodes.get(i).getProperty("GMLid"));
				}
				
			}
			
			// Assign the NodeType
			nodes.get(i).addData("NodeType", findNodeType(nodes.get(i),metrics).toString());
		}
		
		//Check to see if string is empty before adding key,value
	}

	private NodeType findNodeType(Vertex node,List<String> metrics) 
	{	
		if(node.getProperty(metrics.get(0)).isEmpty() 
				&& node.getProperty(metrics.get(1)).isEmpty() 
				&& node.getProperty(metrics.get(2)).isEmpty())
			return NodeType.PACKAGENODE;
		else return NodeType.CLASSNODE;
	}

}
