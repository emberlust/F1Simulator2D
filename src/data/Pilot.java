package data;

public class Pilot {
	
	
	private String name;
	private Car car;
	
	public Pilot()
	{
		this.car = new Car();
		this.name = "Pilot" + this.car.get_id();
	}
	
	public Pilot(String name, Car car)
	{
		this.name = name;
		if(car != null)
		{
			this.car = car;
		}
	}
	
	public String get_name()
	{
		return this.name;
	}
	
	public Car get_car_details()
	{
		return this.car;
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
	
}
