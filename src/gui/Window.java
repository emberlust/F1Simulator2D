package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import data.Race;

public class Window {
	
	private Race race;
	
	private JFrame frame;
	private Grid grid;
	
	private Vector<CarModel> cars;
	
	public Window(Race race)
	{
		this.cars = new Vector<CarModel>(0);
		this.race = race;
		this.init_gui();
	}
	
	private void init_gui()
	{
		
		this.frame = new JFrame();
		
		frame.setLayout(null);
		
		grid = new Grid(race.get_map());
		grid.setSize(600, 600);
		grid.setLocation(10, 10);
		
		frame.setName("FORMULA 1 SIMULATOR");
		frame.setTitle("FORMULA 1 SIMULATOR");
		frame.setSize(new Dimension(1024,712));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	    frame.add(grid,0);
	    
	    frame.setVisible(true);
	    
	    this.race.set_window(this);
	    this.race.start_race();
	   
	}
	
	public void add_car(int x, int y)
	{
		CarModel car = new CarModel();
		    
		float off1 = (600/this.race.get_map().get_size());
		float off2 = (600/this.race.get_map().get_size())/2;
		int px =(int)(x*off1 + off2);
		int py =(int)(y*off1 + off2);
		    
		car.setLocation(px,py);
		    
		frame.add(car,0);
		
		cars.add(car);
	}
	
	public void dispose(int i)
	{
		frame.remove(cars.get(i));
		cars.removeElementAt(i);
	}
	
	public void set_co(int x, int y,int i)
	{
		float off1 = (600/this.race.get_map().get_size());
	    float off2 = (600/this.race.get_map().get_size())/2;
	    int px =(int)(x*off1 + off2);
	    int py =(int)(y*off1 + off2);
	    cars.get(i).setLocation(px,py);
	}
}
