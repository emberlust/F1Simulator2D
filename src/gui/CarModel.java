package gui;

import java.awt.Color;
import java.util.Random;
import javax.swing.JPanel;

public class CarModel extends JPanel {
	
	public CarModel(float off)
	{
		
		Random rdc = new Random();
		int nr = rdc.nextInt(100);
		if(nr >90)
		{
			this.setBackground(Color.red);
		}
		else
		{
			if(nr > 60)
			{
				this.setBackground(Color.orange);
			}
			else
			{
				this.setBackground(Color.cyan);
			}
		}
		this.setSize((int)(20 * 20/off), (int)(20 * 20/off));
		
	}
}
