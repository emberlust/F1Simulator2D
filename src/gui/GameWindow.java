package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import car.Car;
import car.CarFactory;
import data.DataHandler;
import data.Pilot;
import data.Race;
import data.RaceFacade;
import data.ScoreBoard;

public class GameWindow {
	
	//general data
	private final Dimension btn_dim = new Dimension(100,25);
	private final Dimension window_dim = new Dimension(1024,712);
	
	private Race race;
	private RaceFacade rf;
	//main frame
	private JFrame frame;
	
	//racing simulation data
	private Grid grid;
	
	//pilot selection data
	int added = 0;
	private Vector<Pilot> pilots = new Vector<Pilot>(0);
	private int map_size;
	private int loops;
	
	private Vector<CarModel> cars;
	
	public GameWindow(RaceFacade rf)
	{
		this.cars = new Vector<CarModel>(0);
		this.rf = rf;
		this.init_gui();
	}
	
	private void init_gui()
	{
		
		this.frame = new JFrame();
		
		frame.setLayout(null);
		
		frame.setName("FORMULA 1 SIMULATOR");
		frame.setTitle("FORMULA 1 SIMULATOR");
		frame.setSize(window_dim);
		frame.setMinimumSize(window_dim);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().setBackground(Color.green);
	    
		JLabel start_text;
		//setting up the start text
		start_text = new JLabel("FORMULA 1 SIMULATOR");
		start_text.setSize(500, 100);
		start_text.setFont(new Font("Serif",Font.BOLD,40));
		start_text.setLocation(1024/2-250,200);
		start_text.setForeground(Color.red);
		frame.add(start_text);	
		
		JButton start_button;
		//setting up the start button
		start_button = new JButton();
		start_button.setLabel("START");
		start_button.setSize(btn_dim);
		start_button.setLocation(1024/2-50,300);
		start_button.setBackground(Color.DARK_GRAY);
		start_button.setForeground(Color.red);
		start_button.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {map_selection_window();}});
		
		
		//Scores button
		JButton score_button;
		score_button = new JButton();
		score_button.setLabel("Score");
		score_button.setSize(btn_dim);
		score_button.setLocation(1024/2-50,500);
		score_button.setBackground(Color.DARK_GRAY);
		score_button.setForeground(Color.red);
		score_button.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {score_window(DataHandler.pull_data());}});
		
		frame.add(score_button);
		
		frame.add(start_button);
		
	    frame.setVisible(true);
	   
	}
	
	
	
	public void pilot_selection_window()
	{
		//pilots added
		added = 0;
		
		frame.getContentPane().removeAll();
		frame.repaint();
		
		JTextField name_field;
		JLabel name_label;
		//setting input fields
		//name field
		name_field = new JTextField();
		name_field.setSize(btn_dim);
		name_field.setLocation(200, 200);
		frame.add(name_field);
		name_label = new JLabel();
		name_label.setText("Name");
		name_label.setLocation(150, 200);
		name_label.setSize(btn_dim);
		frame.add(name_label);
		
		
		JComboBox car_selection;
		JLabel car_label;
		//car field
		String cars[] = {"AlfaRomeo","Ferrari","Mercedes"};
		car_selection = new JComboBox(cars);
		car_selection.setSize(btn_dim);
		car_selection.setLocation(600, 200);
		frame.add(car_selection);
		car_label = new JLabel();
		car_label.setText("Car");
		car_label.setLocation(550, 200);
		car_label.setSize(btn_dim);
		frame.add(car_label);
		
		JButton add_button;
		JButton finish_button;
		//setting up the buttons
		add_button = new JButton();
		add_button.setLabel("Add");
		add_button.setSize(btn_dim);
		add_button.setLocation(200,500);
		add_button.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {prepare_data_pilots(name_field,car_selection);added++;}});
		frame.add(add_button);
		
		finish_button = new JButton();
		finish_button.setLabel("Finish");
		finish_button.setSize(btn_dim);
		finish_button.setLocation(824,500);
		finish_button.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {is_ready();}});
		frame.add(finish_button);
		
	}
	
	public void is_ready()
	{
		if(added <= 0)
		{
			JOptionPane.showMessageDialog(null, "You need to add at leat two pilots!","Warning!",JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		this.rf.set_pilots(this.pilots);
		
		this.race_window();
		
	}
	
	public void prepare_data_pilots(JTextField name_field,JComboBox car_selection)
	{
		
		if(added > 8)
		{
			JOptionPane.showMessageDialog(null, "Maximum number of pilots is eight!","Warning!",JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		String name = name_field.getText();
		String car = (String) car_selection.getItemAt(car_selection.getSelectedIndex());
		
		float speed = 0;
		
		if(car == "Ferrari")
		{
			speed = 11;
		}
		
		if(car == "Mercedes")
		{
			speed = 10;
		}
		
		if(car == "AlfaRomeo")
		{
			speed = 7;
		}
		
		CarFactory cf = new CarFactory();
		
		pilots.add(new Pilot(name,cf.get_car(speed),""));
		
		name_field.setText("");
	}
	
	public Vector<Pilot> get_pilots_data()
	{
		return this.pilots;
	}
	
	public void map_selection_window()
	{
		frame.getContentPane().removeAll();
		frame.repaint();
		
		JLabel map_label;
		JComboBox map_selection;
		//map selection
		String size[] = {"20","40"};
		map_selection = new JComboBox(size);
		map_selection.setSize(btn_dim);
		map_selection.setLocation(600, 200);
		frame.add(map_selection);
		map_label = new JLabel();
		map_label.setText("Size");
		map_label.setLocation(550, 200);
		map_label.setSize(btn_dim);
		frame.add(map_label);
		
		JComboBox loop_selection;
		JLabel loop_label;
		//loop
		String loops[] = {"1","2","3"};
		loop_selection = new JComboBox(loops);
		loop_selection.setSize(btn_dim);
		loop_selection.setLocation(300, 200);
		frame.add(loop_selection);
		loop_label = new JLabel();
		loop_label.setText("Loops");
		loop_label.setLocation(250, 200);
		loop_label.setSize(btn_dim);
		frame.add(loop_label);
		
		JButton finish_map;
		//finish selection button
		finish_map = new JButton();
		finish_map.setLabel("Finish");
		finish_map.setSize(btn_dim);
		finish_map.setLocation(824,500);
		finish_map.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {prepare_data_map(map_selection,loop_selection);}});
		frame.add(finish_map);
		
		
	}
	
	public void prepare_data_map(JComboBox map_selection,JComboBox loop_selection)
	{
		map_size = Integer.parseInt((String)map_selection.getItemAt(map_selection.getSelectedIndex()));
		loops = Integer.parseInt((String)loop_selection.getItemAt(loop_selection.getSelectedIndex()));
		
		this.rf.set_map(map_size,loops);
		this.pilot_selection_window();
	}
	
	public void race_window()
	{
		frame.getContentPane().removeAll();
		frame.repaint();
		
		this.rf.prepare_race();
		this.rf.get_race().set_window(this);
		
		this.grid_setup();
		
		frame.add(grid,0);
		
		this.rf.set_start(true);
		
	}
	
	public int get_map_size()
	{
		return this.map_size;
	}
	
	public int get_no_loops()
	{
		return this.loops;
	}
	
	public void grid_setup()
	{
		grid = new Grid(rf.get_map());
		grid.setSize(600, 600);
		grid.setLocation(10, 10);
	}
	
	public void score_window(ScoreBoard sb)
	{
		enum cars_enum {NULL,AlfaRomeo,Ferrari,Mercedes};
		
		frame.getContentPane().removeAll();
		frame.repaint();
		
		this.race = this.rf.get_race();
		sb.sort();

		DefaultTableModel tb = new DefaultTableModel();
		JTable score = new JTable(tb);
		
		tb.addColumn("Name");
		tb.addColumn("Score");
		tb.addColumn("Car");
		
		int nop = sb.get_no_participants();
		
		for(int i = 0;i<nop;i++)
		{

			Car car = sb.get_participant(i).get_car_details();

			String n_car;

			if(car == null)
			{
				n_car = "no";
			}
			else{
				n_car = cars_enum.values()[sb.get_participant(i).get_car_details().get_id()].toString();
			}


			tb.insertRow(0,new Object[] {
					sb.get_participant(i).get_name(),
					sb.get_score(i),
					n_car
					});
		}
		
		JScrollPane jp = new JScrollPane(score);
		
		jp.setLocation(1, 1);
		jp.setSize(500, 200);
		
		frame.add(jp,0);
		
	}
	
	//car manager
	
	public void add_car(int x, int y)
	{
		SquircleCarModel car = new SquircleCarModel(this.rf.get_map().get_size());
		    
		float off1 = (600/this.rf.get_map().get_size());
		float off2 = (600/this.rf.get_map().get_size())/2;
		int px =(int)(x*off1 + off2);
		int py =(int)(y*off1 + off2);
		    
		car.setLocation(px,py);
		
		frame.add(car,0);
		
		cars.add(car);
	}
	
	public void dispose(int i)
	{
		frame.remove(cars.get(i));
		cars.removeElementAt(i);
	}
	
	public void set_co(int x, int y,int i)
	{
		float off1 = (600/this.rf.get_map().get_size());
	    float off2 = (600/this.rf.get_map().get_size())/2;
	    int px =(int)(x*off1 + off2);
	    int py =(int)(y*off1 + off2);
	    cars.get(i).setLocation(px,py);
	    
	}
}
