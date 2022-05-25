package car;

public class AlfaRomeo extends Car{
	
	public AlfaRomeo()
	{
		this.id = 2;
		this.top_speed = 7;
		this.fuel_consumption = (float) 5.0;
		this.tire_life = 110;
		this.acc = (float) 2;
		this.c_tire_life = this.tire_life;
		this.fuel = (float)100;
	}
}
