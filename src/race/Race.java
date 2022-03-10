package race;

import map.Map;
import map.Coordinates;
import car.Car;
import score.ScoreBoard;
import java.time.Duration;
import java.time.Instant;
import java.util.Vector;

public class Race {
	
	private Map race_map;
	private Vector<Car> cars;
	private int no_cars;
	private int[] car_loops;
	private Coordinates[] cars_co;
	private Coordinates[] cars_last_co;
	private Coordinates start_line;
	
	private Instant[] preTime;
	
	private int loops;
	
	public Race(int no_cars,int map_size,int no_loops)
	{
		this.race_map = new Map();
		this.race_map.init_map(map_size);
		this.start_line = race_map.generate_simple_path();
		this.cars =  new Vector<Car>(0);
		this.no_cars = no_cars;
		this.car_loops = new int[this.no_cars];
		this.cars_co = new Coordinates[this.no_cars];
		this.cars_last_co = new Coordinates[this.no_cars];
		this.preTime = new Instant[this.no_cars];
		
		
		for(int i=0;i<this.no_cars;i++)
		{
			this.cars.add(new Car());
			this.car_loops[i] = 0;
			this.cars_co[i] = new Coordinates();
			this.cars_last_co[i] = new Coordinates();
			this.cars_co[i].x = start_line.x;
			this.cars_co[i].y = start_line.y-i;
			this.cars_last_co[i].x = start_line.x;
			this.cars_last_co[i].y = this.cars_co[i].y-1;
			this.preTime[i] = Instant.now();
		}
		
		this.loops = no_loops;
		
	}
	
	public ScoreBoard begin_race()
	{
		ScoreBoard score = new ScoreBoard();
		boolean stop = true;
		while(!cars.isEmpty())
		{
			for(int i=0;i<this.no_cars;i++)
			{
				Instant end = Instant.now();
				Duration crTime = Duration.between(preTime[i], end);
				long millis = crTime.toMillis();
				
				if(millis >= ((this.cars.get(i).get_c_speed()==0)?0:(long)1000/this.cars.get(i).get_c_speed()))
				{
					preTime[i] = end;
					float speed = this.race_map.map_data(this.cars_co[i].x, this.cars_co[i].y,1);
					
					int next = this.cars.get(i).action(speed)?1:2;
					
					for(int x=cars_co[i].x-1;x<=this.cars_co[i].x+1 && stop;x++)
					{
						if(this.race_map.map_data(x, this.cars_co[i].y, 0)==next && (x!=this.cars_last_co[i].x
								|| this.cars_co[i].y!=this.cars_last_co[i].y) 
								&& (x!=this.cars_co[i].x || this.cars_co[i].y!=this.cars_co[i].y))
						{
							this.cars_last_co[i].x=this.cars_co[i].x;
							this.cars_last_co[i].y=this.cars_co[i].y;
						
							this.cars_co[i].x=x;
							
							stop = false;
						}
					}
					
					for(int y=cars_co[i].y-1;y<=cars_co[i].y+1 && stop;y++)
					{
						if(this.race_map.map_data(this.cars_co[i].x, y, 0)==next && (this.cars_co[i].x!=this.cars_last_co[i].x
								|| y!=this.cars_last_co[i].y) 
								&& (this.cars_co[i].x!=this.cars_co[i].x || y!=this.cars_co[i].y))
						{
							this.cars_last_co[i].x=this.cars_co[i].x;
							this.cars_last_co[i].y=this.cars_co[i].y;
						
							this.cars_co[i].y=y;
							
							stop = false;
						}
					}
					
					stop = true;
					
					if(this.cars_co[i].x==start_line.x && this.cars_co[i].y==start_line.y)
					{
						car_loops[i]++;
					}
					
					
					//test
					System.out.println(this.cars.get(i).get_c_speed() + " at x " + this.cars_co[i].x + " y " + this.cars_co[i].y);
					
					if(car_loops[i]==this.loops)
					{
						score.PlaceCar(this.cars.get(i));
						this.cars.removeElementAt(i);
						this.no_cars--;
					}
				}
			}
		}
		return score;
	}

}
