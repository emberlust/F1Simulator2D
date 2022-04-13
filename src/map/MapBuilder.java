package map;

public class MapBuilder {
	
	
	int size;
	boolean init, generate;
	
	public MapBuilder set_size(int size)
	{
		this.size = size;
		return this;
	}
	public MapBuilder init(boolean init)
	{
		this.init = init;
		return this;
	}
	public MapBuilder generate(boolean generate)
	{
		this.generate = generate;
		return this;
	}
	public Map get()
	{
		return new Map(this);
	}
}
