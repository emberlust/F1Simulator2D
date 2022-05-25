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
	
	private PilotPositionEngine engine;
	
	private Map map;
	
	public void pass_map(Map map)
	{
		this.map = map;
	}
	
	public void go_on_position(int loops)
	{	
		
		engine = new PilotPositionEngine();
		
		engine.set_global_actions(false);
		
		this.loops = loops;
		
		this.c_co = new Coordinates();
		this.l_co = new Coordinates();
		
		this.c_co.x = map.get_first_grid().x;
		this.c_co.y = map.get_first_grid().y;
		
		this.l_co.x = map.get_start_line().x;
		this.l_co.y = map.get_start_line().y;
		
		engine.set_remote_c(c_co);
		engine.set_remote_l(l_co);
		engine.set_remote_map(map);
		
		while(map.is_oc(l_co) == true)
		{
			engine.calculate_next();
		}
		
		Coordinates.swap(l_co, c_co);
		
		this.t_now = Instant.now();
		
		engine.set_global_actions(true);
		engine.set_presence_action(true);
	}
	
	public Coordinates get_p()
	{
		return this.c_co;
	}
	
	public Coordinates get_lp()
	{
		return this.l_co;
	}
	
	public int make_decision()
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
			
			this.engine.calculate_next();
			
			if(engine.crash_rsp() != 0)
			{
				return engine.crash_rsp();
			}
			
			if(this.engine.check_loops() > loops)
			{
				return 1;
			}
			
			this.t_now = Instant.now();
		}
		
		return 0;
		
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
	
	
}
