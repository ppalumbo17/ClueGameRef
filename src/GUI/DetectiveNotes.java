package GUI;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog{

	public DetectiveNotes(){
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Detective Notes");
		setSize(500, 600);
		setResizable(true);
		setLayout(new GridLayout(2,3));
		setBackground(Color.BLACK);
		createLayout();
	}
	//Adds all panels to the JDialog
	public void createLayout(){
		setLayout(new GridLayout(3,2));
		JPanel players = checkPeople();
		add(players);
		JComboBox peopleGuess = comboPeople();
		add(peopleGuess);
		JPanel rooms = checkRooms();
		add(rooms);
		JComboBox roomsGuess = comboRooms();
		add(roomsGuess);
		JPanel weapons = checkWeapons();
		add(weapons);
		JComboBox weaponsGuess = comboWeapons();
		add(weaponsGuess);
	}
	
	//Creates JPanel of checkboxes for checking people off
	private JPanel checkPeople(){
		
		JCheckBox p1 = new JCheckBox("Miss Scarlet");
		JCheckBox p2 = new JCheckBox("Mr. Green");
		JCheckBox p3 = new JCheckBox("Mrs. Peacock");
		JCheckBox p4 = new JCheckBox("Colonel Mustard");
		JCheckBox p5 = new JCheckBox("Mrs. White");
		JCheckBox p6 = new JCheckBox("Professor Plum");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
		panel.add(p1);
		panel.add(p2);
		panel.add(p3);
		panel.add(p4);
		panel.add(p5);
		panel.add(p6);
		return panel;
	}
	
	//Creates a Combo Box of Most Likely People to Guess from
	private JComboBox comboPeople(){
		JComboBox<String> people = new JComboBox<String>();
		//people.setLayout(new GridLayout(1,2));
		people.setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		people.addItem("Miss Scarlet");
		people.addItem("Mr. Green");
		people.addItem("Mrs. Peacock");
		people.addItem("Colonel Mustard");
		people.addItem("Mrs. White");
		people.addItem("Professor Plum");
		return people;
		
	}
	
	//Creates a JPanel of Check Boxes For Rooms Guessed
private JPanel checkRooms(){
		
		JCheckBox r1 = new JCheckBox("Kitchen");
		JCheckBox r2 = new JCheckBox("Lounge");
		JCheckBox r3 = new JCheckBox("Conservatory");
		JCheckBox r4 = new JCheckBox("Study");
		JCheckBox r5 = new JCheckBox("Billiards Room");
		JCheckBox r6 = new JCheckBox("Dining Room");
		JCheckBox r7 = new JCheckBox("Ballroom");
		JCheckBox r8 = new JCheckBox("Hall");
		JCheckBox r9 = new JCheckBox("Library");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		panel.add(r1);
		panel.add(r2);
		panel.add(r3);
		panel.add(r4);
		panel.add(r5);
		panel.add(r6);
		panel.add(r7);
		panel.add(r8);
		panel.add(r9);
		return panel;
	}

//Creates a ComboBox of Rooms to Track your best Guess
	private JComboBox comboRooms(){
		JComboBox<String> rooms = new JComboBox<String>();
		//rooms.setLayout(new GridLayout(2,2));
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		rooms.addItem("Kitchen");
		rooms.addItem("Lounge");
		rooms.addItem("Conservatory");
		rooms.addItem("Study");
		rooms.addItem("Billiards Room");
		rooms.addItem("Dining Room");
		rooms.addItem("Ballroom");
		rooms.addItem("Hall");
		rooms.addItem("Library");
		return rooms;
		
	}
	
	//Creates a JPanel of Check Boxes to Keep Track of weapons already seen
private JPanel checkWeapons(){
		
		JCheckBox w1 = new JCheckBox("Candlestick");
		JCheckBox w2 = new JCheckBox("Lead Pipe");
		JCheckBox w3 = new JCheckBox("Rope");
		JCheckBox w4 = new JCheckBox("Knife");
		JCheckBox w5 = new JCheckBox("Revolver");
		JCheckBox w6 = new JCheckBox("Wrench");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		panel.add(w1);
		panel.add(w2);
		panel.add(w3);
		panel.add(w4);
		panel.add(w5);
		panel.add(w6);
		return panel;
	}

//Creates a Combo Box to Keep track of your best guess for weapons

	private JComboBox comboWeapons(){
		JComboBox<String> weapons = new JComboBox<String>();
		//weapons.setLayout(new GridLayout(1,1));
		weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons Guess"));
		weapons.addItem("Candlestick");
		weapons.addItem("Lead Pipe");
		weapons.addItem("Rope");
		weapons.addItem("Knife");
		weapons.addItem("Revolver");
		weapons.addItem("Wrench");
		return weapons;
		
	}
	/*public static void main(String[] args){
		DetectiveNotes detect = new DetectiveNotes();
		detect.setVisible(true);
	}*/
	
}
