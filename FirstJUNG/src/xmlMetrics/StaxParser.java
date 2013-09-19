package xmlMetrics;

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


public class StaxParser 
{
	//static final String SLOC 

	public List<String> readMetrics(String metricFile, String nodeName, List<String> nodeMetrics) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException 
	{
		List<String> ret = new ArrayList<String>();
		
		// Standard of reading an XML file
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setNamespaceAware(true);
	    DocumentBuilder builder;
	    Document doc = null;
	    XPathExpression expr = null;
	    builder = factory.newDocumentBuilder();
	    doc = builder.parse(metricFile);
	    
	    // Create a XPathFactory
	    XPathFactory xFactory = XPathFactory.newInstance();
	    
	    // Create a XPath object
	    XPath xpath = xFactory.newXPath();

	    // Extract metrics
	    Object result = null;
	    for(int i=0; i<nodeMetrics.size(); i++)
	    {
	    	// Compile the XPath expression
	    	String xpathExpr = "//class[name='" + nodeName + "']/measurement[short-name='" + nodeMetrics.get(i) + "']/value/text()";
		    expr = xpath.compile(xpathExpr);
		    // Run the query
		    result = expr.evaluate(doc, XPathConstants.STRING);
		    ret.add((String)result);
	    }
	    
			
		return ret;
	}
}
