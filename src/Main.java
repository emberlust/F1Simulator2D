import data.DataHandler;
import data.Map;
import data.Pilot;
import data.Race;
import data.ScoreBoard;
import logging.Level;
import logging.Logger;

public class Main {

	public static void main(String[] args) {
		
		ScoreBoard score = null;
		
		Logger logger = Logger.get_instance();
		
		int a = 5;
		
		try {
			System.out.println(a/0);
		}
		catch(Exception e)
		{
			logger.write(Level.WARNING, e);
		}
		
		Map race_map = new Map();
		race_map.init_map(20);
		race_map.generate_simple_path();
		
		Pilot[] pilots = new Pilot[1];
		
		pilots[0] = new Pilot();
		
		Race f1 = new Race(pilots,race_map,1);
		//score = f1.begin_race();
		
		score = DataHandler.pull_data();
		//DataHandler.push_data(score);
	}

}
