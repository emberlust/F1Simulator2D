package data;

import java.time.Duration;
import java.time.Instant;

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
	
	public int make_decision(float speed, int road_tipe)
	{
		if(road_tipe == (float)2)
		{
			this.car.in_box();
		}
		
		if(this.car.get_c_speed()<speed)
		{
			this.car.accelerate(speed);	
		}
		else
		{
			this.car.brake(speed);
		}
		
		
		if(this.car.feedback())
		{
			return 2;
		}
		else
		{
			return 1;
		}
		
	}
	
	//testing movement by pilot
	
	
	private Coordinates c_co;
	private Coordinates l_co;
	private Instant t_now;
	private boolean moved_x = true;
	private boolean moved_y = true;

	public void go_on_position(Map map)
	{	
		Coordinates co = new Coordinates();
		co.x = map.get_first_grid().x;
		co.y = map.get_first_grid().y;
		
		Coordinates co2 = new Coordinates();
		co2.x = map.get_start_line().x;
		co2.y = map.get_start_line().y;
		
		while(true)
		{
			if(!map.is_oc(co))
			{
				map.set_oc(co, true);
				break;
			}
			for(int x = co.x-1;x<=co.x+1;x++)
			{
				if(x!=co2.x && x!=co.x && x!=map.get_start_line().x && map.map_data(x, co.y, 0) == 1)
				{
					co2.x=co.x;
					co.x=x;
				}
			}
			
			for(int y = co.y-1;y<=co.y+1;y++)
			{
				if(y!= co2.y && y!=co.y && y!=map.get_start_line().y &&map.map_data(co.x, y, 0) == 1)
				{
					co2.y = co.y;
					co.y = y;
				}
			}
		}
		
		this.c_co = new Coordinates();
		this.c_co.x = co.x;
		this.c_co.y = co.y;
		
		for(int x = co.x-1;x<=co.x+1;x++)
		{
			if(x!=co2.x && x!=co.x && x!=map.get_start_line().x && map.map_data(x, co.y, 0) == 1)
			{
				co2.x=co.x;
				co.x=x;
			}
		}
		
		for(int y = co.y-1;y<=co.y+1;y++)
		{
			if(y!= co2.y && y!=co.y && y!=map.get_start_line().y &&map.map_data(co.x, y, 0) == 1)
			{
				co2.y = co.y;
				co.y = y;
			}
		}
		
		this.l_co = new Coordinates();
		this.l_co.x = co.x;
		this.l_co.y = co.y;
		this.t_now = Instant.now();
	}
	
	public Coordinates get_p()
	{
		return this.c_co;
	}
	
	public void make_decision(Map map)
	{
		Instant end = Instant.now();
		Duration crTime = Duration.between(t_now, end);
		long millis = crTime.toMillis();
		
		if(millis >=((this.car.get_c_speed()==0)?0:(long)1000/this.car.get_c_speed()))
		{
			this.car_action(map);
			this.calculate_on_x(map);
			this.calculate_on_y(map);
			this.t_now = Instant.now();
		}
	}
	
	private void car_action(Map map)
	{
		
		if(this.car.get_c_speed()<map.map_data(this.c_co.x, this.c_co.y, 1))
		{
			this.car.accelerate(map.map_data(this.c_co.x, this.c_co.y, 1));
		}
		else
		{
			this.car.brake(map.map_data(this.c_co.x, this.c_co.y, 1));
		}
	}
	
	private void calculate_on_x(Map map)
	{
		if(moved_x == false && moved_y == false)
		{
			this.l_co.x = this.c_co.x;
		}
		moved_x = false;
		for(int x = this.c_co.x - 1; x <= this.c_co.x + 1; x++)
		{
			if(x != this.c_co.x && x != this.l_co.x && map.map_data(x, this.c_co.y, 0) == 1)
			{
				if(map.is_oc(new Coordinates(x,c_co.y)) == true)
				{
					moved_x = true;
					break;	
				}
				
				map.set_oc(c_co, false);
				
				this.l_co.x = this.c_co.x;
				this.c_co.x = x;
				moved_x = true;
				
				map.set_oc(c_co, true);
				
				break;
			}
		}
	}
	
	private void calculate_on_y(Map map)
	{
		if(moved_x == false && moved_y == false)
		{
			this.l_co.y = this.c_co.y;
		}
		moved_y = false;
		for(int y = this.c_co.y - 1; y <= this.c_co.y + 1; y++)
		{
			if(y != this.c_co.y && y != this.l_co.y && map.map_data(this.c_co.x, y, 0) == 1)
			{
				
				if(map.is_oc(new Coordinates(c_co.x,y)) == true)
				{
					moved_y = true;
					break;	
				}
				
				map.set_oc(c_co, false);
				
				this.l_co.y = this.c_co.y;
				this.c_co.y = y;
				moved_y = true;
				
				map.set_oc(c_co, true);
				
				break;
			}
		}
	}
	
}
