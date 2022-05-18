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
	
	private Instant cdr;
	
	private Random random_event = null;
	
	private Race race = null;
	
	public void set_race(Race race)
	{
		this.race = race;
	}
	
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
	
	public Coordinates get_lp()
	{
		return this.l_co;
	}
	
	public void make_decision(Map map)
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
			this.t_now = Instant.now();
		}
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
		for(int x = this.c_co.x - 1; x <= this.c_co.x + 1; x++)
		{
			if(!Coordinates.is_equal(l_co, new Coordinates(x,this.c_co.y)) && !Coordinates.is_equal(c_co, new Coordinates(x,this.c_co.y)) && map.map_data(x, this.c_co.y, 0) == 1)
			{
				if(map.is_oc(new Coordinates(x,c_co.y)) == true)
				{
					if(this.random_event.nextInt(1000) > 500)
					{
						for(int i = 0;i<this.race.get_pilots().size();i++)
						{
							if(x == this.race.get_pilots().get(i).get_p().x && this.c_co.y == this.race.get_pilots().get(i).get_p().y)
							{
								Coordinates.swap(this.c_co,this.race.get_pilots().get(i).get_p());
								Coordinates.swap(this.l_co,this.race.get_pilots().get(i).get_lp());					
								car.brake(this.race.get_pilots().get(i).get_car_details().get_c_speed());
								break;
							}
						}
					}
					
					break;	
				}
				System.out.println("I move");
				
				map.set_oc(c_co, false);
					
				this.l_co.x = this.c_co.x;
				this.l_co.y = this.c_co.y;
				this.c_co.x = x;
					
				map.set_oc(c_co, true);
			
				break;
				
			}
		}
	}
	
	private void calculate_on_y(Map map)
	{		
		for(int y = this.c_co.y - 1; y <= this.c_co.y + 1; y++)
		{
			if(!Coordinates.is_equal(l_co, new Coordinates(this.c_co.x,y)) && !Coordinates.is_equal(c_co, new Coordinates(this.c_co.x,y)) && map.map_data(this.c_co.x, y, 0) == 1)
			{
				if(map.is_oc(new Coordinates(c_co.x,y)) == true)
				{
					if(this.random_event.nextInt(1000) > 500)
					{
						for(int i = 0;i<this.race.get_pilots().size();i++)
						{
							if(y == this.race.get_pilots().get(i).get_p().y && this.c_co.x == this.race.get_pilots().get(i).get_p().x)
							{
								Coordinates.swap(this.c_co,this.race.get_pilots().get(i).get_p());
								Coordinates.swap(this.l_co,this.race.get_pilots().get(i).get_lp());
								car.brake(this.race.get_pilots().get(i).get_car_details().get_c_speed());
								break;
							}
						}
					}
					
					break;	
				}
				
				System.out.println("I move");
				
				map.set_oc(c_co, false);
					
				this.l_co.x = this.c_co.x;
				this.l_co.y = this.c_co.y;
				this.c_co.y = y;
					
				map.set_oc(c_co, true);
					
				break;
			}
		}
	}
	
}
