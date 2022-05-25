package car;

public class CarFactory {

	public CarFactory()
	{
		
	}
	
	public Car get_car(float speed)
	{
		if(speed>=11)
		{
			return new Ferrari();
		}
		if(speed>=10)
		{
			return new Mercedes();
		}
		
		return new AlfaRomeo();
	}
}
