package logging;

public class Level {
	public final static Level SEVERE = new Level("SEVERE");
	public final static Level WARNING = new Level("WARNING");
	public final static Level DEBUG = new Level("DEBUG");
	public final static Level INFO = new Level("INFO");
	
	
	private String level_name;
	
	private Level(String name)
	{
		this.level_name = name;
	}
	
	public String get_name()
	{
		return this.level_name;
	}
	
}
