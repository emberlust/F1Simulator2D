package car;

public class Car {
	
	private int id;
	
	private float top_speed;
	private float fuel_consumption;
	private float tire_life;
	private float acc;
	
	private float c_speed;
	private float fuel;
	
	public Car()//test car
	{
		this.id =1;
		this.top_speed=(float)100;
		this.acc=(float) 10.5;
		this.fuel_consumption=Float.MAX_VALUE;
		this.tire_life=Float.MAX_VALUE;
	}
	
	public Car(float top_speed,float fuel_consumption,float tire_life, float acc)
	{
		this.top_speed=top_speed;
		this.fuel_consumption=fuel_consumption;
		this.tire_life=tire_life;
		this.acc=acc;
	}

	public float get_c_speed()
	{
		return this.c_speed;
	}
	
	public void action(float road_max_speed, boolean free_road)
	{
		if(this.c_speed<road_max_speed)
		{
			this.accelerate(road_max_speed);	
		}
		else
		{
			this.brake(road_max_speed);
		}
	}
	
	private void accelerate(float desired_speed)
	{
		
		float speed = (desired_speed<this.top_speed)?desired_speed:this.top_speed;
		
		if((this.c_speed + this.acc) <= speed)
		{
			this.c_speed += this.acc;
		}
		else
		{
			this.c_speed = speed;
		}
	}
	
	private void brake(float desired_speed)
	{
		this.c_speed = desired_speed;
	}
	
	public int get_id()
	{
		return this.id;
	}
	
}


