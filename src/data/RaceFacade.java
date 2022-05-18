package data;

import car.Car;
import car.CarFactory;
import gui.Window;
import map.Map;
import map.MapBuilder;

public class RaceFacade {

	private Race race;
	private Map map;
	private int nof_loops;
	private Pilot[] pilots;
	
	public RaceFacade()
	{
		
	}
	
	public void choose_pilots()
	{
		CarFactory cf = new CarFactory();
		
		this.pilots = new Pilot[6];
		pilots[0] = new Pilot("gigel",new Car(1,10,0,999,1),"Mercedes");
		pilots[1] = new Pilot("ionica",new Car(2,5,0,999,1),"Redbull");
		pilots[2] = new Pilot("gheorge",new Car(3,6,0,999,1),"TurboP");
		pilots[3] = new Pilot("gigel",new Car(1,11,0,999,1),"Mercedes");
		pilots[4] = new Pilot("ionica",new Car(2,7,0,999,1),"Redbull");
		pilots[5] = new Pilot("gheorge",new Car(3,12,0,999,1),"TurboP");
		//pilots[3] = new Pilot("costica", cf.get_car(150),"Mercedes");
	}
	
	public void chose_map_and_loops()
	{
		int size = 20;
		map = new MapBuilder()
				.set_size(size)
				.init(true)
				.generate(true)
				.build_map();
		this.nof_loops = 1;
	}
	
	public void prepare_race()
	{	
		race = new Race(this.map, nof_loops);
		
		for(Pilot pilot : pilots)
		{
			race.place_participant(pilot);
		}
		
		Window window = new Window(this.race);
		
	}
	
	public ScoreBoard simulate_race()
	{
		this.race.start_race();
		return this.race.get_score();
	}
	
}
