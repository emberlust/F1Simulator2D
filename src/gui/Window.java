package gui;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Window {
	
	private JFrame frame;
	
	public Window()
	{
		this.init_gui();
	}
	
	private void init_gui()
	{
		this.frame = new JFrame();
		frame.setName("FORMULA 1 SIMULATOR");
		frame.setTitle("FORMULA 1 SIMULATOR");
		frame.setSize(new Dimension(1024,712));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}
