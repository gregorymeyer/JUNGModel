/**
 * MetricsReader class
 * Class to extract OO metrics for a specific node from it's metadata file.
 * 
 * @author Greg Meyer & Etai Miller
 * @see GraphPopulator
 * @version 0.1
 */

package metricsExtraction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class MetricsReader 
{
	/**
	 * The function extracts a list of metrics for a specific node from a given XML file
	 * 
	 * @param metricFile String URI of the XML file containing the node's metadata 
	 * @param nodeName String name of the node for which metadata is extracted
	 * @param nodeMetrics names of metrics to be extracted
	 * @return extracted node metrics
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public List<String> readMetrics(String metricFile, String nodeName, List<String> nodeMetrics) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException 
	{
		List<String> ret = new ArrayList<String>();
		
		// Standard of reading an XML file
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setNamespaceAware(true);
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    //Document doc = null;
	    Document doc = builder.parse(metricFile); 
	    XPathExpression expr = null;
	    //builder = factory.newDocumentBuilder();
	    //doc = builder.parse(metricFile);
	    
	    // Create a XPathFactory
	    XPathFactory xFactory = XPathFactory.newInstance();
	    
	    // Create a XPath object
	    XPath xpath = xFactory.newXPath();

	    // Extract metrics
	    Object result = null;
	    for(int i=0; i<nodeMetrics.size(); i++)
	    {
	    	if(nodeMetrics.get(i).equals("SLOC"))
	    		ret.add(findSLOC(nodeName,xpath,doc));
	    	else
	    	{
	    		// Compile the XPath expression
		    	String xpathExpr = "//class[name='" + nodeName + "']/measurement[short-name='" + nodeMetrics.get(i) + "']/value/text()";
			    expr = xpath.compile(xpathExpr);
			    // Run the query
			    result = expr.evaluate(doc, XPathConstants.STRING);
			    ret.add((String)result);
	    	}
	    }
	    
		return ret;
	}

	private String findSLOC(String nodeName, XPath xpath, Document doc) throws XPathExpressionException 
	{
		String xpathExpr = "//class[name='" + nodeName + "']//method/measurement[short-name='SLOC']/value/text()";
		XPathExpression expr = xpath.compile(xpathExpr);
		Object result = expr.evaluate(doc,XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		return findSum(nodes);
	}

	private String findSum(NodeList nodes) 
	{
		Double sum = 0.0;
		for(int i=0; i<nodes.getLength(); i++)
		{
			sum += Double.parseDouble(nodes.item(i).getNodeValue());
		}
		
		if(!sum.toString().equals("0.0"))
			return sum.toString();
		else return "";
	}
}
















