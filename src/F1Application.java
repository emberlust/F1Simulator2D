import car.CarFactory;
import data.DataHandler;
import data.Pilot;
import data.Race;
import data.ScoreBoard;
import map.Map;
import map.MapBuilder;
import gui.Window;
import car.Car;

public class F1Application {

	public static void main(String[] args) {
		
		ScoreBoard score = null;
		
		
		CarFactory cf = new CarFactory();
		
		Pilot[] pilots = new Pilot[4];
		
		pilots[0] = new Pilot("gigel",new Car(1,10,0,999,1),"Mercedes");
		pilots[1] = new Pilot("ionica",new Car(2,5,0,999,1),"Redbull");
		pilots[2] = new Pilot("gheorge",new Car(3,6,0,999,1),"TurboP");
		pilots[3] = new Pilot("costica", cf.get_car(150),"Mercedes");
		
		Map race_map = new MapBuilder()
				.set_size(20)
				.init(true)
				.generate(true)
				.build_map();
		
		Race f1 = new Race(race_map,1);
		f1.place_participant(pilots[0]);
		f1.place_participant(pilots[1]);
		f1.place_participant(pilots[2]);
		f1.place_participant(pilots[3]);
		
	   Window window = new Window(f1);
		
	    

		//f1.start_race();
		
		score = f1.get_score();
		
		
		//score = DataHandler.pull_data();
		//DataHandler.push_data(score);
	}

}
