package data;

import java.time.Duration;
import java.time.Instant;
import java.util.Vector;
import logging.*;
import map.Coordinates;
import map.Map;
import gui.Window;

public class Race {
	
	
	private int max_p = 8;
	private int c_p = 0;
	
	private Vector<Pilot> pilots;
	private Map race_map;
	private int no_pilots;
	private Vector<Integer> pilot_loops;
	private Vector<Coordinates> pilot_co;
	private Vector<Coordinates> pilot_last_co;
	private Coordinates start_line;
	
	private Vector<Instant> preTime;
	
	private int loops;
	
	private ScoreBoard Scoreboard = null;
	
	private Window main_window;
	
	public Race(Map map,int no_loops)
	{
		this.Scoreboard = new ScoreBoard();
		this.preTime = new Vector<Instant>(0);
		this.race_map = map;
		this.pilots =  new Vector<Pilot>(0);
		this.pilot_loops = new Vector<Integer>(0);
		this.pilot_co = new Vector<Coordinates>(0);		
		this.pilot_last_co = new Vector<Coordinates>(0);
		this.start_line = map.get_start_line();
		this.loops = no_loops;
		
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
			this.pilot_co.add(new Coordinates(this.start_line.x,this.start_line.y - this.c_p - 1));
			this.pilot_last_co.add(new Coordinates(this.start_line.x,this.start_line.y - this.c_p - 2));
			this.pilot_loops.add(0);
			this.preTime.add(null);
			c_p++;
			this.no_pilots = this.c_p;
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
		for(int i=0;i<this.no_pilots;i++)
		{
			this.preTime.set(i, Instant.now());
			this.main_window.add_car(this.pilot_co.get(i).x, this.pilot_co.get(i).y);
		}
		
		Logger.get_instance().write(Level.DEBUG, "Start line " + this.start_line.x + " " + this.start_line.y);
		
		while(!pilots.isEmpty())
		{
			for(int i=0;i<this.no_pilots;i++)
			{
				Instant end = Instant.now();
				Duration crTime = Duration.between(preTime.get(i), end);
				long millis = crTime.toMillis();
				
				if(millis >= ((this.pilots.get(i).get_car_details().get_c_speed()==0)?0:(long)1000/this.pilots.get(i).get_car_details().get_c_speed()))
				{
					preTime.set(i, end);
					
					
					float speed = this.race_map.map_data(this.pilot_co.get(i).x, this.pilot_co.get(i).y,1);
					int tile = (int)this.race_map.map_data(this.pilot_co.get(i).x, this.pilot_co.get(i).y, 0);
					
					int next = this.pilots.get(i).make_decision(speed, tile);
					
					if(this.calculates_on_x(i, next) == 0)
					{
						if(this.calculates_on_y(i, next) == 0)
						{
							
							this.pilots.remove(i);
							this.pilot_last_co.removeElementAt(i);
							this.pilot_co.removeElementAt(i);
							this.pilot_loops.removeElementAt(i);
							this.main_window.dispose(i);
							this.no_pilots--;
							continue;
						}
					}
		
					main_window.set_co(this.pilot_co.get(i).x, this.pilot_co.get(i).y,i);
					
					if(this.pilot_co.get(i).x==start_line.x && this.pilot_co.get(i).y==start_line.y)
					{
						pilot_loops.set(i,pilot_loops.get(i)+1);
					}
					
					
					//test
					Logger.get_instance().write(Level.DEBUG,this.pilots.get(i).get_name() + " with " + this.pilots.get(i).get_car_details().get_c_speed() + " at x " + this.pilot_co.get(i).x + " y " + this.pilot_co.get(i).y);
					
					if(pilot_loops.get(i)>this.loops)
					{
						this.Scoreboard.place_participant(this.pilots.get(i));
						this.pilots.removeElementAt(i);
						this.pilot_last_co.removeElementAt(i);
						this.pilot_co.removeElementAt(i);
						this.pilot_loops.removeElementAt(i);
						this.no_pilots--;
						this.main_window.dispose(i);
					}
				}
			}
		}
	}
	
	private int calculates_on_x(int i,int next)
	{
		int ret = 0;
		boolean stop = true;
		//calculates position on x axis 
		for(int x=pilot_co.get(i).x-1;x<=this.pilot_co.get(i).x+1 && stop;x++)
		{
			if(((x!=this.pilot_last_co.get(i).x || this.race_map.map_data(this.pilot_co.get(i).x, this.pilot_co.get(i).y, 0)==2) && (x!=this.pilot_co.get(i).x)))
			{
				if(this.race_map.map_data(x, this.pilot_co.get(i).y, 0)==next)
				{
					this.pilot_last_co.get(i).x=this.pilot_co.get(i).x;
					this.pilot_last_co.get(i).y=this.pilot_co.get(i).y;
				
					this.pilot_co.get(i).x=x;
					ret = 1;
					stop = false;
				}
				else
				{
					if(this.race_map.map_data(x, this.pilot_co.get(i).y, 0)==1)
					{
						this.pilot_last_co.get(i).x=this.pilot_co.get(i).x;
						//this.cars_last_co[i].y=this.cars_co[i].y;
					
						this.pilot_co.get(i).x=x;
					
						ret = 1;
						stop = false;
					}
				}
			}
		}
		return ret;
	}
	
	private int calculates_on_y(int i, int next)
	{
		int ret = 0; 
		boolean stop = true;
		//calculates position on y axis
		for(int y=pilot_co.get(i).y-1;y<=pilot_co.get(i).y+1 && stop;y++)
		{
			if((y!=this.pilot_last_co.get(i).y || this.race_map.map_data(this.pilot_co.get(i).x, this.pilot_co.get(i).y, 0)==2) && (y!=this.pilot_co.get(i).y))
			{
				if(this.race_map.map_data(this.pilot_co.get(i).x, y, 0)==next)
				{
					this.pilot_last_co.get(i).x=this.pilot_co.get(i).x;
					this.pilot_last_co.get(i).y=this.pilot_co.get(i).y;
					
					this.pilot_co.get(i).y=y;
				
					ret = 1;
					stop = false;
				}
				else
				{
					if(this.race_map.map_data(this.pilot_co.get(i).x, y, 0)==1)
					{
						//this.cars_last_co[i].x=this.cars_co[i].x;
						this.pilot_last_co.get(i).y=this.pilot_co.get(i).y;
						
						this.pilot_co.get(i).y=y;
					
						ret = 1;
						stop = false;
					}
				}
			}
		}
		return ret;
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