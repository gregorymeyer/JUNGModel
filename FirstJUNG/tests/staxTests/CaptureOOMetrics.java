package staxTests;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import graphML.*;

import org.junit.Test;
import org.xml.sax.SAXException;

import xmlMetrics.StaxParser;

public class CaptureOOMetrics {
	
	@Test
	public void staxParserCorrectlyExtractsSLOCMetric() throws NumberFormatException, XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		StaxParser staxParser = new StaxParser();
		assertEquals(Integer.parseInt(staxParser.readMetrics("MockMetrics.xml").get(0)),8);
	}
	

}
