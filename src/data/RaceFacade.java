package data;

import java.util.Vector;

import gui.GameWindow;
import map.Map;
import map.MapBuilder;

public class RaceFacade {

	private Race race;
	private Map map;
	private int nof_loops;
	private Vector<Pilot> pilots;
	
	private boolean start = false;
	
	public RaceFacade()
	{
		
	}
	
	public void set_start(boolean o_f)
	{
		this.start = o_f;
	}
	
	public boolean get_start()
	{
		return this.start;
	}
	
	public void set_map(int size,int loops)
	{
		this.nof_loops = loops;
		map = new MapBuilder()
				.set_size(size)
				.init(true).generate(true)
				.build_map();
	}
	
	public void set_pilots(Vector<Pilot> pilots)
	{
		this.pilots = pilots;
		
	}
	
	public void prepare_race()
	{
		this.race = new Race();
		this.race.set_map(map);
		this.race.set_loops(nof_loops);
		for(Pilot pilot : pilots)
		{
			race.place_participant(pilot);
		}
		
	}
	
	public void simulate_race()
	{
		System.out.println("I am about to start");
		this.race.start_race();
		DataHandler.push_data(this.race.get_score());
	}
	
	public Map get_map()
	{
		return this.map;
	}
	
	public Race get_race()
	{
		return this.race;
	}
}
