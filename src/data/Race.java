package data;

import java.util.Vector;
import logging.*;
import map.Map;
import gui.Window;

public class Race {
	
	
	private int max_p = 8;
	private int c_p = 0;
	
	private Vector<Pilot> pilots;
	private Map race_map;
	private int no_pilots;

	private ScoreBoard Scoreboard = null;
	
	private Window main_window;
	
	public Race(Map map,int no_loops)
	{
		this.Scoreboard = new ScoreBoard();
		this.race_map = map;
		this.pilots =  new Vector<Pilot>(0);
	}
	
	public void set_window(Window window)
	{
		this.main_window = window;
	}
	
	//returns true when full, otherwise false
	public boolean place_participant(Pilot pilot)
	{
		boolean is_full = false;
		
		if(this.c_p <= this.max_p)
		{
			this.pilots.add(pilot);
			c_p++;
			this.no_pilots = this.c_p;
			pilot.set_race(this);
			pilot.go_on_position(race_map);
		}
		else
		{
			is_full = true;
		}
		
		return is_full;
	}
	
	//starts the race
	public void start_race()
	{
		
		for(int i = 0;i<this.no_pilots;i++)
		{
			this.main_window.add_car(pilots.get(i).get_p().x, pilots.get(i).get_p().y);
		}
		
		while(true)
		{
			for(int i = 0;i<this.no_pilots;i++)
			{
				pilots.get(i).make_decision(race_map);
				this.main_window.set_co(pilots.get(i).get_p().x, pilots.get(i).get_p().y, i);
			}
		}
		
	}
	
	public Vector<Pilot> get_pilots()
	{
		return this.pilots;
	}
	
	public Map get_map()
	{
		return this.race_map;
	}
	
	public ScoreBoard get_score()
	{
		return this.Scoreboard;
	}

}