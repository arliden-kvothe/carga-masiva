package mx.com.paralife;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Unit test for simple App.
 */
public class AppTest {

	/**
	 * Rigourous Test :-)
	 */
	@Test
	public void testApp() {
		try {
			String fault = FileUtils.readFileToString(new File("fault.txt"));
			String[] lines = fault.split("\n");
			System.out.println(lines.length);
			StringBuilder str = new StringBuilder(lines[5]);
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = documentFactory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(str.toString())));
			System.out.println(docToString(doc));
			String faultcode = "faultcode";
			String faultstring = "faultstring";
			NodeList nodes = doc.getElementsByTagName(faultcode);
			assertEquals("Retrieved strings don't match ", "S:Server", nodes.item(0).getTextContent());
			nodes = doc.getElementsByTagName(faultstring);
			assertEquals("Retrieved strings don't match ", "The requested market(LB_IQ) is not a Future or it doesn't have the proper settings, please check with Administrator(s).", nodes.item(0).getTextContent());
		} catch (IOException | ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*System.out.println(str.toString().in);
		
		try {
			DocumentBuilder builder = documentFactory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(str.toString())));
			String faultcode = "faultcode";
			String faultstring = "faultstring";
			NodeList nodes = doc.getElementsByTagName(faultcode);
			assertEquals("Retrieved strings don't match ", "S:Server", nodes.item(0).getTextContent());
			nodes = doc.getElementsByTagName(faultstring);
			assertEquals("Retrieved strings don't match ", "The requested market(LB_IQ) is not a Future or it doesn't have the proper settings, please check with Administrator(s).", nodes.item(0).getTextContent());
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
	
	public static String docToString(Document doc) {
	    try {
	        StringWriter sw = new StringWriter();
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

	        transformer.transform(new DOMSource(doc), new StreamResult(sw));
	        return sw.toString();
	    } catch (Exception ex) {
	        throw new RuntimeException("Error converting to String", ex);
	    }
	}
}
