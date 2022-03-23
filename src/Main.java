import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import data.DataHandler;
import data.Map;
import data.Pilot;
import data.Race;
import data.ScoreBoard;

public class Main {

	public static void main(String[] args) {
		System.out.println("Test");
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Document document = null;
		try {
			document = builder.parse("scores.xml");
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.getDocumentElement().normalize();
		ScoreBoard score = null;
		
		Map race_map = new Map();
		race_map.init_map(20);
		race_map.generate_simple_path();
		
		ScoreBoard read_data = DataHandler.pull_data(document);
		read_data.show_score();
		DataHandler.push_data(read_data, document);
		
		//data.update_data("Pilot1",12,"pilots","pilot",document);
		
		//race_map.show_map_speeds();
		
		Pilot[] pilots = new Pilot[1];
		
		pilots[0] = new Pilot();
		
		Race f1 = new Race(pilots,race_map,1);
		//score = f1.begin_race();
		
		//score.show_score();
		
		System.out.println("End test");
	}

}
