import data.RaceFacade;
import gui.GameWindow;

public class F1Application {

	public static void main(String[] args) {
		
		RaceFacade rf = new RaceFacade();
		GameWindow main_window = new GameWindow(rf);
		while(rf.get_start() == false)
		{
			System.out.println("I am in loop");
		}
		rf.simulate_race();
		rf.get_race().get_score().sort();
		main_window.score_window(rf.get_race().get_score());
	}

}
