package data;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

import car.Car;
import map.*;

public class Pilot {
	
	
	private String name;
	private Car car;
	private String team;
	
	public Pilot()
	{
		this.car = new Car();
		this.name = "Pilot" + this.car.get_id();
	}
	
	public Pilot(String name, Car car, String team)
	{
		this.name = name;
		if(car != null)
		{
			this.car = car;
		}
		this.team = team;
	}
	
	public String get_name()
	{
		return this.name;
	}
	
	public Car get_car_details()
	{
		return this.car;
	}
	
	public String get_team()
	{
		return this.team;
	}
	
	//testing movement by pilot
	
	
	private Coordinates c_co;
	private Coordinates l_co;
	private Instant t_now;
	
	private Instant cdr;
	
	private Random random_event = null;
	
	private int loops;
	private int c_loops;
	
	private Map map;
	
	public void pass_map(Map map)
	{
		this.map = map;
	}
	
	public void go_on_position(int loops)
	{	
		
		this.loops = loops;
		this.c_loops = 0;
		
		this.c_co = new Coordinates();
		this.l_co = new Coordinates();
		
		this.c_co.x = map.get_first_grid().x;
		this.c_co.y = map.get_first_grid().y;
		
		this.l_co.x = map.get_start_line().x;
		this.l_co.y = map.get_start_line().y;
		
		while(map.is_oc(l_co) == true)
		{
			this.make_decision();
			this.calculate_on_x(map);
			this.calculate_on_y(map);
		}
		
		Coordinates.swap(l_co, c_co);
		
		this.t_now = Instant.now();
	}
	
	public Coordinates get_p()
	{
		return this.c_co;
	}
	
	public Coordinates get_lp()
	{
		return this.l_co;
	}
	
	public boolean make_decision()
	{
		if(this.random_event == null)
		{
			this.random_event = new Random();
		}
		
		Instant end = Instant.now();
		Duration crTime = Duration.between(t_now, end);
		long millis = crTime.toMillis();
		
		if(millis >=((this.car.get_c_speed()==0)?0:(long)1000/this.car.get_c_speed()))
		{
			this.car_action(map.map_data(this.c_co.x, this.c_co.y, 1));
			this.calculate_on_x(map);
			this.calculate_on_y(map);
			
			if(Coordinates.is_equal(c_co, map.get_start_line()))
			{
				this.c_loops++;
			}
			
			if(this.c_loops == loops)
			{
				return true;
			}
			
			this.t_now = Instant.now();
		}
		
		return false;
		
	}
	
	private void car_action(float speed)
	{
		
		if(this.car.get_c_speed()<speed)
		{
			this.car.accelerate(speed);
		}
		else
		{
			this.car.brake(speed);
		}
	}
	
	
	//check corner state || check if another overcome takes place  
	
	private void calculate_on_x(Map map)
	{		
		boolean overcome = false;
		for(int x = this.c_co.x - 1; x <= this.c_co.x + 1; x++)
		{
			if(!Coordinates.is_equal(l_co, new Coordinates(x,this.c_co.y)) && !Coordinates.is_equal(c_co, new Coordinates(x,this.c_co.y)) && map.map_data(x, this.c_co.y, 0) == 1)
			{
				if(map.is_oc(new Coordinates(x,c_co.y)) == true)
				{
					if(this.random_event.nextInt(1000) > 500)
					{
						overcome = true;
					}
				}
				System.out.println("I move");
				
				map.set_oc(c_co, false);
					
				this.l_co.x = this.c_co.x;
				this.l_co.y = this.c_co.y;
				this.c_co.x = x;
					
				map.set_oc(c_co, true);
				
				if(overcome)
				{
					this.calculate_on_x(map);
					this.calculate_on_y(map);
					map.set_oc(l_co, true);
				}
			
				break;
				
			}
		}
	}
	
	private void calculate_on_y(Map map)
	{		
		
		boolean overcome = false;
		
		for(int y = this.c_co.y - 1; y <= this.c_co.y + 1; y++)
		{
			if(!Coordinates.is_equal(l_co, new Coordinates(this.c_co.x,y)) && !Coordinates.is_equal(c_co, new Coordinates(this.c_co.x,y)) && map.map_data(this.c_co.x, y, 0) == 1)
			{
				if(map.is_oc(new Coordinates(c_co.x,y)) == true)
				{
					if(this.random_event.nextInt(1000) > 500)
					{
						overcome = true;
					}	
				}
				
				System.out.println("I move");
				
				map.set_oc(c_co, false);
					
				this.l_co.x = this.c_co.x;
				this.l_co.y = this.c_co.y;
				this.c_co.y = y;
					
				map.set_oc(c_co, true);
				
				if(overcome)
				{
					this.calculate_on_x(map);
					this.calculate_on_y(map);
					map.set_oc(l_co, true);
				}
					
				break;
			}
		}
	}
	
}
