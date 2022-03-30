package logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

public class Logger {

	
	private static final String logfile = "logs.txt";
	private static Logger logger = null;
	private PrintWriter logwriter;
	
	private Logger()
	{
		try {
			FileWriter fw = new FileWriter(logfile,true);
			logwriter = new PrintWriter(fw,true);
			logwriter.println("\nNew logging session at: " + Instant.now());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Logger get_instance()
	{
		
		if(logger==null)
		{
			logger = new Logger();
		}
		return logger;
	}
	
	public void write(Level level,Exception exception)
	{
		this.logwriter.println();
		this.logwriter.println(Instant.now());
		this.logwriter.println(level.get_name() + "--" + exception);
	}
	
	public void write(Level level, String log)
	{
		this.logwriter.println();
		this.logwriter.println(Instant.now());
		this.logwriter.println(level.get_name() + "--" + log);
	}
}
