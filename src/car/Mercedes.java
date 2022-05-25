package car;

public class Mercedes extends Car{
	
	public Mercedes()
	{
		this.id = 1;
		this.top_speed = 10;
		this.fuel_consumption = (float) 5.5;
		this.tire_life = 100;
		this.acc = (float) 2.5;
		this.c_tire_life = this.tire_life;
		this.fuel = (float)100;
	}
}
