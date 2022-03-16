package data;

import java.time.Duration;
import java.time.Instant;
import java.util.Vector;

public class Race {
	
	private Vector<Pilot> pilots;
	private Map race_map;
	private int no_cars;
	private int[] car_loops;
	private Coordinates[] cars_co;
	private Coordinates[] cars_last_co;
	private Coordinates start_line;
	
	private Instant[] preTime;
	
	private int loops;
	
	public Race(Pilot[] pilots,Map map,int no_loops)
	{
		this.race_map = map;
		this.pilots =  new Vector<Pilot>(0);
		this.no_cars = pilots.length;
		this.car_loops = new int[this.no_cars];
		this.cars_co = new Coordinates[this.no_cars];
		this.cars_last_co = new Coordinates[this.no_cars];
		this.preTime = new Instant[this.no_cars];
		this.start_line = map.get_start_line();
		
		for(int i=0;i<this.no_cars;i++)
		{
			this.pilots.add(pilots[i]);
			this.car_loops[i] = 0;
			this.cars_co[i] = new Coordinates();
			this.cars_last_co[i] = new Coordinates();
			this.cars_co[i].x = start_line.x;
			this.cars_co[i].y = start_line.y-i;
			this.cars_last_co[i].x = start_line.x;
			this.cars_last_co[i].y = this.cars_co[i].y-1;
		}
		
		for(int i=0;i<this.no_cars;i++)
		{
			this.preTime[i] = Instant.now();
		}
		
		this.loops = no_loops;
		
	}
	
	public ScoreBoard begin_race()
	{
		ScoreBoard score = new ScoreBoard();
		while(!pilots.isEmpty())
		{
			for(int i=0;i<this.no_cars;i++)
			{
				Instant end = Instant.now();
				Duration crTime = Duration.between(preTime[i], end);
				long millis = crTime.toMillis();
				
				if(millis >= ((this.pilots.get(i).get_car_details().get_c_speed()==0)?0:(long)1000/this.pilots.get(i).get_car_details().get_c_speed()))
				{
					preTime[i] = end;
					float speed = this.race_map.map_data(this.cars_co[i].x, this.cars_co[i].y,1);
					
					int next = this.pilots.get(i).make_decision(speed, (int)this.race_map.map_data(this.cars_co[i].x, this.cars_co[i].y, 0));
					
					this.calculates_on_x(i, next);
					this.calculates_on_y(i, next);
		
					if(this.cars_co[i].x==start_line.x && this.cars_co[i].y==start_line.y)
					{
						car_loops[i]++;
					}
					
					
					//test
					System.out.println(this.pilots.get(i).get_car_details().get_c_speed() + " at x " + this.cars_co[i].x + " y " + this.cars_co[i].y);
					
					if(car_loops[i]==this.loops)
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
		for(int x=cars_co[i].x-1;x<=this.cars_co[i].x+1 && stop;x++)
		{
			if(((x!=this.cars_last_co[i].x || this.race_map.map_data(this.cars_co[i].x, this.cars_co[i].y, 0)==2) && (x!=this.cars_co[i].x)))
			{
				if(this.race_map.map_data(x, this.cars_co[i].y, 0)==next)
				{
					this.cars_last_co[i].x=this.cars_co[i].x;
					this.cars_last_co[i].y=this.cars_co[i].y;
				
					this.cars_co[i].x=x;
				
					stop = false;
				}
				else
				{
					if(this.race_map.map_data(x, this.cars_co[i].y, 0)==1)
					{
						this.cars_last_co[i].x=this.cars_co[i].x;
						//this.cars_last_co[i].y=this.cars_co[i].y;
					
						this.cars_co[i].x=x;
					
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
		for(int y=cars_co[i].y-1;y<=cars_co[i].y+1 && stop;y++)
		{
			if((y!=this.cars_last_co[i].y || this.race_map.map_data(this.cars_co[i].x, this.cars_co[i].y, 0)==2) && (y!=this.cars_co[i].y))
			{
				if(this.race_map.map_data(this.cars_co[i].x, y, 0)==next)
				{
					this.cars_last_co[i].x=this.cars_co[i].x;
					this.cars_last_co[i].y=this.cars_co[i].y;
					
					this.cars_co[i].y=y;
				
					stop = false;
				}
				else
				{
					if(this.race_map.map_data(this.cars_co[i].x, y, 0)==1)
					{
						//this.cars_last_co[i].x=this.cars_co[i].x;
						this.cars_last_co[i].y=this.cars_co[i].y;
						
						this.cars_co[i].y=y;
					
						stop = false;
					}
				}
			}
		}
	}

}
