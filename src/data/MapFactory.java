package data;

public class MapFactory {

	static private int small = 20;
	static private int medium = 50; 
	static private int large = 100;
	
	public Map get_small_map()
	{
		Map map = new Map();
		
		map.init_map(small);
		map.generate_simple_path();
		
		return map;
	}
	
	public Map get_medium_map()
	{
		Map map = new Map();
		
		map.init_map(medium);
		map.generate_simple_path();
		
		return map;
		
	}
	
	public Map get_large_map()
	{
		Map map = new Map();
		
		map.init_map(large);
		map.generate_simple_path();
		
		return map;	
	}
}
