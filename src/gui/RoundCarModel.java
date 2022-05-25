package gui;

import java.awt.Graphics;

public class RoundCarModel extends CarModel{

	public RoundCarModel(float off) {
		super(off);
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.fillOval(0, 0, this.size().height, this.size().height);
	}

}
