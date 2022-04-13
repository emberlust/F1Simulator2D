package car;

public class CarFactory {

	public CarFactory()
	{
		
	}
	
	public Car get_car(float speed)
	{
		if(speed>=120)
		{
			return new Mercedes();
		}
		if(speed>=100)
		{
			return new AlfaRomeo();
		}
		
		return new Ferrari();
	}
}
