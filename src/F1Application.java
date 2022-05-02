import data.RaceFacade;


public class F1Application {

	public static void main(String[] args) {

		RaceFacade f1 = new RaceFacade();
		
		f1.chose_pilots();
		f1.chose_map_and_loops();
		f1.prepare_race();
		f1.simulate_race();
	}

}
