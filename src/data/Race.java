package data;

import java.time.Duration;
import java.time.Instant;
import java.util.Vector;
import logging.*;

public class Race {
	
	
	private int max_p = 8;
	private int c_p = 0;
	
	private Vector<Pilot> pilots;
	private Map race_map;
	private int no_cars;
	private Vector<Integer> car_loops;
	private Vector<Coordinates> cars_co;
	private Vector<Coordinates> cars_last_co;
	private Coordinates start_line;
	
	private Vector<Instant> preTime;
	
	private int loops;
	
	public Race(Map map,int no_loops)
	{
		this.preTime = new Vector<Instant>(0);
		this.race_map = map;
		this.pilots =  new Vector<Pilot>(0);
		this.car_loops = new Vector<Integer>(0);
		this.cars_co = new Vector<Coordinates>(0);		
		this.cars_last_co = new Vector<Coordinates>(0);
		this.start_line = map.get_start_line();
		this.loops = no_loops;
		
	}
	
	//returns true when full, otherwise false
	public boolean place_participant(Pilot pilot)
	{
		boolean is_full = false;
		
		if(this.c_p <= this.max_p)
		{
			this.pilots.add(pilot);
			this.cars_co.add(new Coordinates(this.start_line.x,this.start_line.y));
			this.cars_last_co.add(new Coordinates(this.start_line.x,this.start_line.y - 1));
			this.car_loops.add(0);
			this.preTime.add(null);
			c_p++;
			this.no_cars = this.c_p;
		}
		else
		{
			is_full = true;
		}
		
		return is_full;
	}
	
	//starts the race
	public ScoreBoard start_race()
	{
		for(int i=0;i<this.no_cars;i++)
		{
			this.preTime.set(i, Instant.now());
		}
		ScoreBoard score = new ScoreBoard();
		while(!pilots.isEmpty())
		{
			for(int i=0;i<this.no_cars;i++)
			{
				Instant end = Instant.now();
				Duration crTime = Duration.between(preTime.get(i), end);
				long millis = crTime.toMillis();
				
				if(millis >= ((this.pilots.get(i).get_car_details().get_c_speed()==0)?0:(long)1000/this.pilots.get(i).get_car_details().get_c_speed()))
				{
					preTime.set(i, end);
					float speed = this.race_map.map_data(this.cars_co.get(i).x, this.cars_co.get(i).y,1);
					
					int next = this.pilots.get(i).make_decision(speed, (int)this.race_map.map_data(this.cars_co.get(i).x, this.cars_co.get(i).y, 0));
					
					this.calculates_on_x(i, next);
					this.calculates_on_y(i, next);
		
					if(this.cars_co.get(i).x==start_line.x && this.cars_co.get(i).y==start_line.y)
					{
						car_loops.set(i,car_loops.get(i)+1);
					}
					
					
					//test
					Logger.get_instance().write(Level.DEBUG, this.pilots.get(i).get_car_details().get_c_speed() + " at x " + this.cars_co.get(i).x + " y " + this.cars_co.get(i).y);
					
					if(car_loops.get(i)==this.loops)
					{
						score.place_participant(this.pilots.get(i));
						this.pilots.removeElementAt(i);
						this.no_cars--;
					}
				}
			}
		}
		return score;
	}
	
	private void calculates_on_x(int i,int next)
	{
		boolean stop = true;
		//calculates position on x axis 
		for(int x=cars_co.get(i).x-1;x<=this.cars_co.get(i).x+1 && stop;x++)
		{
			if(((x!=this.cars_last_co.get(i).x || this.race_map.map_data(this.cars_co.get(i).x, this.cars_co.get(i).y, 0)==2) && (x!=this.cars_co.get(i).x)))
			{
				if(this.race_map.map_data(x, this.cars_co.get(i).y, 0)==next)
				{
					this.cars_last_co.get(i).x=this.cars_co.get(i).x;
					this.cars_last_co.get(i).y=this.cars_co.get(i).y;
				
					this.cars_co.get(i).x=x;
				
					stop = false;
				}
				else
				{
					if(this.race_map.map_data(x, this.cars_co.get(i).y, 0)==1)
					{
						this.cars_last_co.get(i).x=this.cars_co.get(i).x;
						//this.cars_last_co[i].y=this.cars_co[i].y;
					
						this.cars_co.get(i).x=x;
					
						stop = false;
					}
				}
			}
		}
	}
	
	private void calculates_on_y(int i, int next)
	{
		boolean stop = true;
		//calculates position on y axis
		for(int y=cars_co.get(i).y-1;y<=cars_co.get(i).y+1 && stop;y++)
		{
			if((y!=this.cars_last_co.get(i).y || this.race_map.map_data(this.cars_co.get(i).x, this.cars_co.get(i).y, 0)==2) && (y!=this.cars_co.get(i).y))
			{
				if(this.race_map.map_data(this.cars_co.get(i).x, y, 0)==next)
				{
					this.cars_last_co.get(i).x=this.cars_co.get(i).x;
					this.cars_last_co.get(i).y=this.cars_co.get(i).y;
					
					this.cars_co.get(i).y=y;
				
					stop = false;
				}
				else
				{
					if(this.race_map.map_data(this.cars_co.get(i).x, y, 0)==1)
					{
						//this.cars_last_co[i].x=this.cars_co[i].x;
						this.cars_last_co.get(i).y=this.cars_co.get(i).y;
						
						this.cars_co.get(i).y=y;
					
						stop = false;
					}
				}
			}
		}
	}

}
