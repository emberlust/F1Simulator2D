package data;

import java.util.Random;

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
		Random random_values = new Random();
		this.id = random_values.nextInt(1,100);
		this.top_speed = random_values.nextFloat(10, 150);
		this.acc = random_values.nextFloat(1, 15);
		this.fuel_consumption = random_values.nextFloat(1,2);
		this.tire_life = random_values.nextFloat(100,200);
		this.c_tire_life = this.tire_life;
		this.fuel = (float)100;
	}
	
	public Car(int id,float top_speed,float fuel_consumption,float tire_life, float acc)
	{
		this.id = id;
		this.top_speed=top_speed;
		this.fuel_consumption=fuel_consumption;
		this.tire_life=tire_life;
		this.acc=acc;
		this.c_tire_life = this.tire_life;
		this.fuel = (float)100;
	}

	public float get_c_speed()
	{
		return this.c_speed;
	}
	
	public float get_fuel_consumptiopn()
	{
		return this.fuel_consumption;
	}
	
	public float get_fuel()
	{
		return this.fuel;
	}
	
	public void in_box()
	{
		this.fuel = 100;
		this.c_tire_life = this.tire_life;
	}
	
	public void accelerate(float desired_speed)
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
		
		this.c_tire_life--;
		this.fuel-=this.fuel_consumption;
	}
	
	public void brake(float desired_speed)
	{
		this.c_speed = desired_speed;
		
		this.c_tire_life-=10;
		this.fuel-=this.fuel_consumption/2;
	}
	
	public boolean feedback()
	{
		boolean fdb = false;
		if(this.fuel < 100/5 || this.c_tire_life < this.tire_life/4)
		{
			fdb = true;
		}
	
		return fdb;
	}
	
	public int get_id()
	{
		return this.id;
	}
	
}


