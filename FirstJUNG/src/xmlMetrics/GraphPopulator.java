package xmlMetrics;

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
import graphML.Vertex;

public class GraphPopulator 
{
	final String character_count = "CNCC";
	final String word_count = "CNWC";
	final String loc = "SLOC";
	final String methods = "M";
	final String public_methods = "PuM";
	StaxParser staxParser;
	
	public void populate(GraphWrapper graph)
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
		metrics.add(character_count);
		metrics.add(word_count);
		metrics.add(loc);
		metrics.add(methods);
		metrics.add(public_methods);
		
		Map<String,List<String>> nodeMetrics = new HashMap<String,List<String>>(); //better option to a HashMap?
		StaxParser staxParser =  new StaxParser();
		for(int i=0; i<nodeNames.size(); i++)
		{
			try 
			{
				nodeMetrics.put(nodeNames.get(i),staxParser.readMetrics("MockMetrics.xml", nodeNames.get(i), metrics));
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
				}
				
			}
		}
		
		//Check to see if string is empty before adding key,value
		
	}

}
