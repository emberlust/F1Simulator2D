package main;

import race.Race;
import score.ScoreBoard;
import map.Map;
import gui.Window;

public class Main {

	public static void main(String[] args) {
		System.out.println("Test");
		
		ScoreBoard score;
		
		Race f1 = new Race(1,20,1);
		score = f1.begin_race();
		
		score.ShowScore();
		
		Window main_frame = new Window();
		
		System.out.println("End test");
	}

}
