package main;

import race.Race;
import score.ScoreBoard;
import map.Map;

public class Main {

	public static void main(String[] args) {
		System.out.println("Test");
		
		ScoreBoard score;
		
		Race f1 = new Race(1,20,1);
		score = f1.begin_race();
		
		score.ShowScore();
		
		System.out.println("End test");
	}

}
