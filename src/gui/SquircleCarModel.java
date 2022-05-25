package gui;

import java.awt.Graphics;

public class SquircleCarModel extends CarModel{

	public SquircleCarModel(float off) {
		super(off);
	}

	@Override
	public void paint(Graphics g)
	{
		g.fillRoundRect(0, 0, this.size().height, this.size().height, 10, 10);
	}
}
