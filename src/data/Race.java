package data;

import java.util.Vector;
import logging.*;
import map.Coordinates;
import map.Map;
import gui.GameWindow;

public class Race {
	
	
	private int max_p = 8;
	private int c_p = 0;
	
	private Vector<Pilot> pilots;
	private Map race_map;
	private int no_pilots;

	private ScoreBoard Scoreboard = null;
	
	private GameWindow main_window;
	
	private int loops;
	
	public Race()
	{
		this.Scoreboard = new ScoreBoard();
		this.pilots =  new Vector<Pilot>(0);
	}
	
	public void set_map(Map map)
	{
		this.race_map = map;
	}
	
	public void set_loops(int lp)
	{
		this.loops = lp;
	}
	
	public void set_window(GameWindow window)
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
			pilot.pass_map(race_map);
			pilot.go_on_position(this.loops);
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
		this.Scoreboard = new ScoreBoard();
		
		for(int i = 0;i<this.c_p;i++)
		{
			this.main_window.add_car(pilots.get(i).get_p().x, pilots.get(i).get_p().y);
		}
		while(this.pilots.isEmpty() == false)
		{
			for(int i = 0;i<this.c_p;i++)
			{
				int rsp = pilots.get(i).make_decision();
				if( rsp == 1)
				{
					this.Scoreboard.place_participant(pilots.get(i), this.c_p);
					this.main_window.dispose(i);
					this.race_map.set_oc(pilots.get(i).get_p(), false);
					pilots.remove(i);
					this.c_p--;
					break;
				}
				
				if( rsp == 2)
				{
					this.Scoreboard.place_participant(pilots.get(i), 0);
					this.main_window.dispose(i);
					this.race_map.set_oc(pilots.get(i).get_p(), false);
					pilots.remove(i);
					this.c_p--;
					break;
				}
				
				if(rsp == 3)
				{
					Coordinates co = new Coordinates(pilots.get(i).get_p().x,pilots.get(i).get_p().y);
					
					boolean found = false;
					
					do
					{
						for(int t = 0;t<this.c_p;t++)
						{
							if(Coordinates.is_equal(co, pilots.get(t).get_p()))
							{
								this.Scoreboard.place_participant(pilots.get(t), 0);
								this.main_window.dispose(t);
								this.race_map.set_oc(pilots.get(t).get_p(), false);
								pilots.remove(t);
								this.c_p--;
								found = true;
								break;
							}
						}
					}while(found);
					break;
				}
				
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
	
	public boolean is_empty()
	{
		if(this.c_p == 0)
		{
			return true;
		}
		
		return false;
	}
}