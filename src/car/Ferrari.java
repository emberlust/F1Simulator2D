package car;

public class Ferrari extends Car{
	
	public Ferrari()
	{
		this.id = 3;
		this.top_speed = 11;
		this.fuel_consumption = (float) 7.5;
		this.tire_life = 150;
		this.acc = (float) 2.3;
		this.c_tire_life = this.tire_life;
		this.fuel = (float)100;
	}

}
