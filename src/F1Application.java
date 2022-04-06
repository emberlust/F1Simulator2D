import data.DataHandler;
import data.Map;
import data.Pilot;
import data.Race;
import data.ScoreBoard;
import data.MapFactory;

public class F1Application {

	public static void main(String[] args) {
		
		ScoreBoard score = null;
		
		MapFactory map_f = new MapFactory();
		
		Map race_map = map_f.get_large_map();
		
		Pilot[] pilots = new Pilot[2];
		
		pilots[0] = new Pilot();
		pilots[1] = new Pilot();
		
		Race f1 = new Race(race_map,1);
		f1.place_participant(pilots[0]);
		f1.place_participant(pilots[1]);
		score = f1.start_race();
		
		//score = DataHandler.pull_data();
		//DataHandler.push_data(score);
	}

}
