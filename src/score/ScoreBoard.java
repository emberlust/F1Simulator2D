package score;

import car.Car;
import java.util.Vector;

public class ScoreBoard {

	private int no_cars;
	private Vector<Car> cars;
	private int poz;
	
	public ScoreBoard()
	{
		no_cars = 0;
		cars = new Vector<Car>(0);
	}
	
	public void PlaceCar(Car car)
	{
		this.no_cars++;
		this.cars.add(car);
	}
	
	public void ShowScore()
	{
		this.poz = 1;
		this.cars.forEach(car->{System.out.println(car.get_id() + " pe locul " + poz++);});
	}
	
}
