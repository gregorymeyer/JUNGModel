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

	public List<String> readMetrics(String metrics) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException 
	{
		List<String> ret = new ArrayList<String>();
		ret.add("8");
		
		// Standard of reading an XML file
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setNamespaceAware(true);
	    DocumentBuilder builder;
	    Document doc = null;
	    XPathExpression expr = null;
	    builder = factory.newDocumentBuilder();
	    doc = builder.parse(metrics);
	    
	    // Create a XPathFactory
	    XPathFactory xFactory = XPathFactory.newInstance();
	    
	    // Create a XPath object
	    XPath xpath = xFactory.newXPath();

	    // Compile the XPath expression
	    expr = xpath.compile("//measurement[short-name='M']/value/text()");
	    // Run the query and get a nodeset
	    Object result = expr.evaluate(doc, XPathConstants.NODESET);
	    
	    // Cast the result to a DOM NodeList
	    NodeList nodes = (NodeList) result;
	    for (int i=0; i<nodes.getLength();i++)
	    {
	      System.out.println(nodes.item(i).getNodeValue());
	    }
			
		return ret;
	}
}
