package car;

public class Car {
	
	private int id;
	
	private float top_speed;
	private float fuel_consumption;
	private float tire_life;
	private float acc;
	
	private float c_speed;
	private float fuel;
	private float c_tire_life;
	
	public Car()//test car
	{
		this.id =1;
		this.top_speed=(float)100;
		this.acc=(float) 1.5;
		this.fuel_consumption=(float)1.5;
		this.tire_life=50;
		this.fuel = 100;
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
	
	public boolean action(float road_max_speed)
	{
		if(this.c_speed<road_max_speed)
		{
			this.accelerate(road_max_speed);	
		}
		else
		{
			this.brake(road_max_speed);
		}
		this.fuel-=this.fuel_consumption;
		this.c_tire_life--;
		
		if(this.fuel <= this.fuel/4 || this.c_tire_life<this.c_tire_life/4)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void in_box()
	{
		this.fuel = 100;
		this.c_tire_life = this.tire_life;
		
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


