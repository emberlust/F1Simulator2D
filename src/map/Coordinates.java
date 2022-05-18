package map;

public class Coordinates{
	public int x;
	public int y;
	
	public Coordinates(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
	
	public Coordinates()
	{
		this.x = 0;
		this.y = 0;
	}
	
	public static void swap(Coordinates c1, Coordinates c2)
	{
		Coordinates swap = new Coordinates();
		
		swap.x = c1.x;
		swap.y = c1.y;
		
		c1.x = c2.x;
		c1.y = c2.y;
		
		c2.x = swap.x;
		c2.y = swap.y;
		
	}
	
	public static boolean is_equal(Coordinates c1, Coordinates c2)
	{
		
		if(c1.x == c2.x && c1.y == c2.y)
		{
			return true;
		}
		
		return false;
	}
	
}