package data;

import java.util.Random;

import map.Coordinates;
import map.Map;

public class PilotPositionEngine {
	
	private Coordinates c_co;
	private Coordinates l_co;
	private Map map;
	private Random random_event = null;
	private boolean global_actions = false;
	private boolean presence = false;
	
	private int c_loops = 0;
	
	public PilotPositionEngine()
	{
		this.random_event = new Random();
	}
	
	public void set_global_actions(boolean on_off)
	{
		this.global_actions = on_off;
	}
	
	public void set_presence_action(boolean on_off)
	{
		this.presence = on_off;
		map.set_oc(c_co, on_off);	
	}
	
	public void set_remote_c(Coordinates co)
	{
		this.c_co = co;
	}
	
	public void set_remote_l(Coordinates co)
	{
		this.l_co = co;
	}
	
	public void set_remote_map(Map map)
	{
		this.map = map;
	}
	
	public int check_loops()
	{
		return this.c_loops;
	}
	
	public void calculate_next()
	{
		this.calculate_on_x();
		this.calculate_on_y();	
	}
	
	
	private int rsp;
	
	public int crash_rsp()
	{
		return rsp;
	}
	
	public void calculate_on_x()
	{	
		
		rsp = 0;
		
		boolean overcome = false;
		for(int x = this.c_co.x - 1; x <= this.c_co.x + 1; x++)
		{
			if(!Coordinates.is_equal(l_co, new Coordinates(x,this.c_co.y)) && !Coordinates.is_equal(c_co, new Coordinates(x,this.c_co.y)) && map.map_data(x, this.c_co.y, 0) == 1)
			{
				if(map.is_oc(new Coordinates(x,c_co.y)) == true && this.global_actions)
				{
					
					int rand = this.random_event.nextInt(1000);
					
					if(rand > 998)
					{
						rsp = 3;
						this.calculate_on_x();
						this.calculate_on_y();
					}
					else
					{
						if(rand > 990)
						{
							rsp = 2;
						}
						else if(rand > 500)
						{
							overcome = true;
						}
						else
						{
							break;
						}
					}
				}
				
				if(this.presence)
				{
					map.set_oc(c_co, false);
				}
					
				this.l_co.x = this.c_co.x;
				this.l_co.y = this.c_co.y;
				this.c_co.x = x;
				
				if(Coordinates.is_equal(c_co, map.get_start_line()))
				{
					c_loops++;
				}
				
				if(overcome)
				{
					this.calculate_on_x();
					this.calculate_on_y();
				}
				
				if(this.presence)
				{
					map.set_oc(c_co, true);
				}
				
				break;
				
			}
		}
	}
	
	
	public void calculate_on_y()
	{		
		
		rsp = 0;
		
		boolean overcome = false;
		
		for(int y = this.c_co.y - 1; y <= this.c_co.y + 1; y++)
		{
			if(!Coordinates.is_equal(l_co, new Coordinates(this.c_co.x,y)) && !Coordinates.is_equal(c_co, new Coordinates(this.c_co.x,y)) && map.map_data(this.c_co.x, y, 0) == 1)
			{
				if(map.is_oc(new Coordinates(c_co.x,y)) == true && this.global_actions)
				{
					
					int rand = this.random_event.nextInt(1000);
					
					if(rand > 998)
					{
						rsp = 3;
						this.calculate_on_x();
						this.calculate_on_y();
					}
					else if(rand > 990)
					{
							rsp = 2;
					} 
					else if(rand > 500)
					{
						overcome = true;
					}
					else
					{
						break;
					}
				}
				
				
				if(this.presence)
				{
					map.set_oc(c_co, false);
				}
					
				this.l_co.x = this.c_co.x;
				this.l_co.y = this.c_co.y;
				this.c_co.y = y;
					
				if(Coordinates.is_equal(c_co, map.get_start_line()))
				{
					c_loops++;
				}
				
				if(overcome)
				{
					this.calculate_on_x();
					this.calculate_on_y();
				}
				
				if(this.presence)
				{
					map.set_oc(c_co, true);
				}
					
				break;
			}
		}
	}
}
