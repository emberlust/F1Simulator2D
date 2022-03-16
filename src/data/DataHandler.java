package data;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DataHandler {
	
	
	public DataHandler()
	{
		
	}
	
	public NodeList pull_data(String tag, Document document)
	{	
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		NodeList doc_data = null;
		doc_data = root.getElementsByTagName(tag);

		return doc_data;
		
	}
	
	public void push_data(String text, int number, String tag_parent, String tag, Document document)
	{
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		Element parent = (Element) root.getElementsByTagName(tag_parent).item(0);
		
		Element new_element = document.createElement(tag);
		
		Element data_1 = document.createElement("name");
		data_1.setTextContent(text);
		
		Element data_2 = document.createElement("points");
		data_2.setTextContent(new String(Integer.toString(number)));
		
		new_element.appendChild(data_1);
		new_element.appendChild(data_2);
		parent.appendChild(new_element);
		
		DOMSource source = new DOMSource(document);
		TransformerFactory transformer_factory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformer_factory.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		
		StreamResult result = new StreamResult("scores.xml");
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update_data(String text, int number, String tag, String data_identifier, Document document)
	{	
		document.getDocumentElement().normalize();
		
		Element root = document.getDocumentElement();
		Element parent = (Element) root.getElementsByTagName(tag).item(0);
		NodeList data = parent.getElementsByTagName(data_identifier);
		
		for(int i = 0; i<data.getLength();i++)
		{
			Element item = (Element) data.item(i);
			
			String str = item.getElementsByTagName("name").item(0).getTextContent();
			
			if(str.equals(text))
			{
				item.getElementsByTagName("points").item(0).setTextContent(Integer.toString(Integer.parseInt(item.getElementsByTagName("points").item(0).getTextContent())+number));
			}
		
		}
		
		DOMSource source = new DOMSource(document);
		TransformerFactory transformer_factory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformer_factory.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		
		StreamResult result = new StreamResult("scores.xml");
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
