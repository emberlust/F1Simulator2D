package gui;
	
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import map.Map;

public class Grid extends Component{
	
	private static final long serialVersionUID = 1L;

	private int size;
	
	private int width;
	private int height;
	
	private Map map;
	
	Grid(Map map)
	{
		this.map = map;
		this.width = 600;
		this.height = 600;
		this.size = map.get_size();
	}
	
	@Override
    public void paint(Graphics g) {
    	
		int mx = 0;
		int my = 0;
		
		for ( int x = 0; x < width; x += width/this.size )
		{
			my = 0;
			 for ( int y = 0; y < height; y += height/this.size ) 
			 {
				 if(this.map.map_data(mx, my, 0) == 1)
				 {
					 g.setColor(Color.gray);
				 }
				 else
				 {
					 if(this.map.map_data(mx, my, 0)==2)
					 {
						 g.setColor(Color.blue);
					 }
					 else
					 {
						 g.setColor(Color.green);
					 }
				 }
				 g.fillRect( x, y, width/this.size, height/this.size);
				 my++;
			 }
			 mx++;
		}
    }
	
}
