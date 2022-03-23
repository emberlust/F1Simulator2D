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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DataHandler {
	
	
	public DataHandler()
	{
		
	}
	
	public static ScoreBoard pull_data(Document document)
	{	
		Element root = document.getDocumentElement();
		NodeList doc_data = null;
		doc_data = root.getElementsByTagName("pilot");
		
		ScoreBoard score = new ScoreBoard();
		for(int i = 0; i<doc_data.getLength(); i++)
		{
			Node node = doc_data.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element) node;
			    score.place_participant(new Pilot(element.getElementsByTagName("name").item(0).getTextContent(),null),Integer.parseInt(element.getElementsByTagName("points").item(0).getTextContent()));
			    	
			}
		}
		
		score.sort();
		
		return score;
		
	}
	
	public static void push_data(ScoreBoard score, Document document)
	{
		
		for(int i = 0; i<score.get_no_participants();i++)
		{
			boolean found = false;
			Element root = document.getDocumentElement();
			Element parent = (Element) root.getElementsByTagName("pilots").item(0);
			NodeList data = parent.getElementsByTagName("pilot"); 
			
			for(int j = 0; j<data.getLength();j++)
			{
				Element item = (Element) data.item(j);
				
				String str = item.getElementsByTagName("name").item(0).getTextContent();
				
				if(str.equals(score.get_participant(i).get_name()))
				{
					DataHandler.update_data(score.get_participant(i).get_name(), score.get_score(i) + Integer.parseInt(item.getElementsByTagName("points").item(0).getTextContent()) , document);
					found = true;
				}
			}
			
			if(found)
			{
				continue;
			}
			
			Element new_element = document.createElement("pilot");
			
			Element data_1 = document.createElement("name");
			data_1.setTextContent(score.get_participant(i).get_name());
			
			Element data_2 = document.createElement("points");
			data_2.setTextContent(new String(Integer.toString(score.get_score(i))));
			
			new_element.appendChild(data_1);
			new_element.appendChild(data_2);
			parent.appendChild(new_element);
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
	
	private static void update_data(String text, int number, Document document)
	{
		
		Element root = document.getDocumentElement();
		Element parent = (Element) root.getElementsByTagName("pilots").item(0);
		NodeList data = parent.getElementsByTagName("pilot");
		
		for(int i = 0; i<data.getLength();i++)
		{
			Element item = (Element) data.item(i);
			
			String str = item.getElementsByTagName("name").item(0).getTextContent();
			
			if(str.equals(text))
			{
				item.getElementsByTagName("points").item(0).setTextContent(Integer.toString(number));
			}
		
		}
	}
}
