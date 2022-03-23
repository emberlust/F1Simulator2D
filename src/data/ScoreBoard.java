package data;

import java.util.Vector;

public class ScoreBoard {

	private int no_pilots;
	private Vector<Pilot> pilots;
	private Vector<Integer> scores;
	private int poz;
	
	public ScoreBoard()
	{
		no_pilots = 0;
		pilots = new Vector<Pilot>(0);
		scores = new Vector<Integer>(0);
	}
	
	public void place_participant(Pilot pilot)
	{
		this.no_pilots++;
		this.pilots.add(pilot);
		this.scores.add(0);
		for(int i = 0;i<this.no_pilots;i++)
		{
			this.scores.set(i,this.scores.get(i)+1);
		}
	}
	
	public void place_participant(Pilot pilot, int score)
	{
		this.no_pilots++;
		this.pilots.add(pilot);
		this.scores.add(score);
	}
	
	public void show_score()
	{
		System.out.println("There were " + this.no_pilots + " participants with the next rezults: ");
		this.poz = 1;
		this.pilots.forEach(pilot->{System.out.println(pilot.get_name() + " placed " + poz++ + " with score " + this.get_score(poz-2));});
	}
	
	public void sort()
	{
		for(int i = 0; i < this.no_pilots - 1; i++)
		{
			for(int j = 0; j < this.no_pilots; j++)
			{
				if(scores.get(i)<scores.get(j))
				{
					Pilot aux_pilot = pilots.get(i);
					int aux_score = scores.get(i);
					
					scores.set(i, scores.get(j));
					scores.set(j, aux_score);
					
					pilots.set(i, pilots.get(j));
					pilots.set(j, aux_pilot);
				}
			}
		}
	}
	
	
	public int get_no_participants()
	{
		return this.no_pilots;
	}
	
	public Pilot get_participant(int poz)
	{
			return this.pilots.get(poz);
	}
	
	public int get_score(int poz)
	{
		return this.scores.get(poz);
	}
	
}
