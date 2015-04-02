package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.lang.reflect.Field;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import GUI.ControlPanelGUI;
import GUI.DetectiveNotes;


public class ClueGame extends JFrame{
	private Board gameBoard = new Board();
	private final int WINDOW_WIDTH = 1000;
	private final int WINDOW_HEIGHT = 870;
	private Player humanPlayer;
	private static Map<Character, String> rooms = new HashMap<Character, String>();
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Card> people = new ArrayList<Card>();
	private ArrayList<Card> weapons = new ArrayList<Card>();
	private Set<Card> dontDeal = new HashSet<Card>();
	private static ArrayList<Player> gamePlayers = new ArrayList<Player>();
	private Map<String,ArrayList<Integer>> startingPositions = new HashMap<String, ArrayList<Integer>>();
	private Map<String, Color> players = new HashMap<String, Color>();
	private Solution solution;
	private ControlPanelGUI controlPanel = new ControlPanelGUI();
	private DetectiveNotes detectiveNotes = new DetectiveNotes();
	
	private JMenuBar menuBar = new JMenuBar();
	public ClueGame() throws BadConfigFormatException {
		loadRoomConfig("ClueLegend2.txt");
		loadConfigFiles("ClueLayout2.csv");
		
		/*setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		setSize(1000, 800);
		setResizable(false);
		setJMenuBar(menuBar);
		//menuBar.add(createFileMenu());
		setBackground(Color.BLACK);*/
		
	}
	//General Constructors
	public ClueGame(String layout, String legend) throws BadConfigFormatException {
		loadRoomConfig(legend);
		loadConfigFiles(layout);
	}
	
	public ClueGame(String layout, String legend, String player, String deck) throws BadConfigFormatException {
		loadRoomConfig(legend);
		loadConfigFiles(layout);
		loadPlayerConfig(player);
		loadDeckConfig(deck);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		
		JPanel board = gameBoard;
		add(board, BorderLayout.CENTER);
		JPanel cp = controlPanel.createLayout();
		add(cp, BorderLayout.SOUTH);
	}
	
	//Creates the Menu Bar
	private JMenu createFileMenu(){
		JMenu menu = new JMenu("File");
		menu.add(createFileNote());
		menu.add(createFileExit());
		
		return menu;
	}
	
	private JMenuItem createFileNote() {
		JMenuItem item = new JMenuItem("Detective Notes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				detectiveNotes.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	private JMenuItem createFileExit() {
		JMenuItem item = new JMenuItem("Exit");
		  class MenuItemListener implements ActionListener {
		    public void actionPerformed(ActionEvent e)
		    {
		       System.exit(0);
		    }
		  }
		  item.addActionListener(new MenuItemListener());
		  return item;
	}
	//Config Loads
	//Loads the Deck
	private void loadDeckConfig(String deck) throws BadConfigFormatException{
		File f = new File(deck);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String tmp;
			String[] tmp2;
			try {
				while ((tmp=br.readLine())!=null){
					tmp2 = new String[2];
					tmp2 = tmp.split(", ");
					if (tmp2.length != 2)
						throw new BadConfigFormatException();
					this.deck.add(new Card(tmp2[0], tmp2[1].charAt(0)));
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
		selectAnswer();
		deal();
		
	}
	//Loads game config
	public void loadConfigFiles(String layout) throws BadConfigFormatException{
		gameBoard.loadBoardConfig(layout);
	}
	
	//Loads room configs
	public void loadRoomConfig(String legend) throws BadConfigFormatException{
		File f = new File(legend);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String tmp;
			String[] tmp2;
			try {
				while ((tmp=br.readLine())!=null){
					tmp2 = new String[2];
					tmp2 = tmp.split(", ");
					if (tmp2.length != 2)
						throw new BadConfigFormatException();
					rooms.put(tmp2[0].charAt(0), tmp2[1]);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
		gameBoard.setRooms(rooms);
	}
	//Loads the player config
	public void loadPlayerConfig(String player) throws BadConfigFormatException{
		File f = new File(player);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String tmp;
			//String tmp1;
			String[] tmp2;
			ArrayList<Integer> startPos;
			
			try {
				while ((tmp=br.readLine())!=null){
					tmp2 = new String[2];
					startPos = new ArrayList<Integer>();
					tmp2 = tmp.split(", ");
					if (tmp2.length != 4)
						throw new BadConfigFormatException();
					players.put(tmp2[0],convertColor(tmp2[1]));
					startPos.add(Integer.parseInt(tmp2[2]));
					startPos.add(Integer.parseInt(tmp2[3]));
					startingPositions.put(tmp2[0], startPos);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
		
		setGamePlayers();

	}
	//Color conversion
	public Color convertColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined } 
		}
		return color;
	} 
	//Deals the cards
	public void deal(){
		ArrayList<Card> dealDeck = new ArrayList<Card>(deck);
		for(Card x: dontDeal){
			if(dealDeck.contains(x)){
				dealDeck.remove(x);
			}
		}
		Random rand = new Random();
		while(!dealDeck.isEmpty()){
			for(Player x: gamePlayers){
				if(dealDeck.isEmpty()){
					break;
				}
				int randInt =  rand.nextInt(dealDeck.size());
				x.getHand().add(dealDeck.get(randInt));
				x.seenCard(dealDeck.get(randInt));
				dealDeck.remove(randInt);
				
			}
		}
	}
	//Sets the type of Cards
	public void SetCardTypes(ArrayList<Card> deck){
		for(Card x:deck){
			switch(x.getCardType()){
			case PERSON:
				people.add(x);
				break;
			case WEAPON:
				weapons.add(x);
				break;
			case ROOM:
				break;
			}
		}
	}
	//Chooses the definite answer
	public void selectAnswer(){
		String person = null;
		String room = null;
		String weapon = null;
		Random rand = new Random();
		int randCard;
		while(person == null || room == null || weapon == null){
			randCard =  rand.nextInt(deck.size());
			if(deck.get(randCard).getCardType() == Card.CardType.PERSON){
				if(person == null){
					person = deck.get(randCard).getName();
					dontDeal.add(deck.get(randCard));
				}
			}else if(deck.get(randCard).getCardType() == Card.CardType.ROOM){
				if(room == null){
					room = deck.get(randCard).getName();
					dontDeal.add(deck.get(randCard));
				}
			}else if(deck.get(randCard).getCardType() == Card.CardType.WEAPON){
				if(weapon == null){
					weapon = deck.get(randCard).getName();
					dontDeal.add(deck.get(randCard));
				}
			}
			
			
		}
		
		this.solution = new Solution(person, room, weapon);
	}
	
	//Compares Suggestion to Answer
	public String checkSuggestion(String person, String room, String weapon, Player suggestingPlayer){
		String response = null;
		for(Player x: gamePlayers){
			if(x != suggestingPlayer){
				response = handleSuggestion(person, room, weapon, x);
			}
			if(response != null){
				return response;
			}
		}
		return null;
	}
	
	public String handleSuggestion(String person, String room, String weapon, Player respondingPerson){
		ArrayList<String> possibleResponses = new ArrayList<String>();
		for(Card x: respondingPerson.getHand()){
			if(x.getName() == person || x.getName() == room || x.getName() == weapon){
				possibleResponses.add(x.getName());
			}
		}
		
		if(possibleResponses.isEmpty()){
			return null;
		}
		Random rand = new Random();
		int randResponse = rand.nextInt(possibleResponses.size());
		
		return possibleResponses.get(randResponse);
	}
	
	public boolean checkAccusation(Solution solution){
		if(solution.getPerson() == this.solution.getPerson()
				&& solution.getRoom() == this.solution.getRoom()
				&& solution.getWeapon() == this.solution.getWeapon()){
			return true;
		}else{
			return false;
		}
	}
	
	//MAIN METHOD -----------------------------------------------------------------------------------------------
	//
	//
	public static void main(String[] args) throws BadConfigFormatException {
		ClueGame g = new ClueGame("ClueLayout.csv", "RoomLegend.txt", "Players.txt", "Deck.txt");
		
		g.setVisible(true);
		
	}

	//GETTERS AND SETTERS
	//
	public Board getBoard() {
		return gameBoard;
	}
	public ArrayList<Card> getDeck(){
		return deck;
	}
	public void setDeck(ArrayList<Card> deck){
		this.deck = deck;
	}
	public static Map<Character, String> getRooms() {
		return rooms;
	}
	
	public void setSolution(Solution solution){
		this.solution = solution;
	}
	
	public void setPlayers(Map<String, Color> players) {
		this.players = players;
	}
	public Map<String, Color> getPlayers() {
		return players;
	}
	public static ArrayList<Player> getGamePlayers() {
		return gamePlayers;
	}
	
	public ArrayList<Card> getPeopleCards(){
		return people;
	}
	public ArrayList<Card> getweaponsCards(){
		return weapons;
	}
	public void setGamePlayersForTest(ArrayList<Player> testPlayers){
		this.gamePlayers = testPlayers;
	}


	public void setGamePlayers(){
		boolean first = true;
		for(String key: players.keySet()){
			if(first){
				gamePlayers.add(
						new HumanPlayer(key, 
										players.get(key), 
										gameBoard.getBoardCellAt(startingPositions.get(key).get(0), startingPositions.get(key).get(1))));
				humanPlayer = gamePlayers.get(0);
				first = false;
			}else{
				gamePlayers.add(
						new ComputerPlayer(key, 
										players.get(key), 
										gameBoard.getBoardCellAt(startingPositions.get(key).get(0), startingPositions.get(key).get(1))));
					
			}
		}
	}
	
	
	
}
