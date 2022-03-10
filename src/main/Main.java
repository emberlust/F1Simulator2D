package main;

import race.Race;
import score.ScoreBoard;
import map.Map;
import car.Car;

public class Main {

	public static void main(String[] args) {
		System.out.println("Test");
		
		ScoreBoard score;
		
		Map race_map = new Map();
		race_map.init_map(20);
		race_map.generate_simple_path();
		
		Car[] cars = new Car[1];
		
		cars[0] = new Car();
		
		Race f1 = new Race(cars,race_map,1);
		score = f1.begin_race();
		
		score.ShowScore();
		
		System.out.println("End test");
	}

}
