package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Card;

public class GameSetupTests {

	private static Board board;
	private static ClueGame game;
	public static final int NUM_ROOM_CARDS = 9;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 23;
	public static final int NUM_PLAYERS = 6;
	public static final int NUM_WEAPONS = 6;
	public static final int NUM_DECK_CARDS = 21;
	public static final int NUM_CARDS = 18;
	
	
	@BeforeClass
	public static void setUp() throws BadConfigFormatException {
		game = new ClueGame("ClueLayout.csv", "RoomLegend.txt", "Players.txt", "Deck.txt");
		//game.loadConfigFiles("ClueLayout.csv");
		board = game.getBoard();
		//Map<String, String> players = board.getPlayers();
		
	}
	
	//The Following are PLAYER LOADING TESTS ---------
	//
	//Test that the players are loaded
	@Test
	public void testPlayers() {
		Map<String, Color> players = game.getPlayers();
		
		assertTrue(players.containsKey("Colonel Mustard"));
		assertTrue(players.containsKey("Professor Plum"));
		assertTrue(players.containsKey("Miss Scarlet"));
		assertTrue(players.containsKey("Mrs. White"));
		assertTrue(players.containsKey("Reverend Green"));
		assertTrue(players.containsKey("Mrs. Peacock"));
	}
	
	//Tests that there are the correct number of players
	@Test
	public void testPlayerNumber(){
		ArrayList<Player> gamePlayers = game.getGamePlayers();
		assertEquals(gamePlayers.size(), NUM_PLAYERS);
	}
	
	//Tests the Player Initial Locations
	@Test
	public void testPlayerLocation(){
		ArrayList<Player> gamePlayers = game.getGamePlayers();
		for(Player x: gamePlayers){
			switch(x.getName()){
			case "Colonel Mustard":
				assertEquals(x.getLocation(), board.getBoardCellAt(4,0));
				break;
			case "Professor Plum":
				assertEquals(x.getLocation(), board.getBoardCellAt(12,0));
				break;
			case "Miss Scarlet":
				assertEquals(x.getLocation(), board.getBoardCellAt(0,14));
				break;
			case "Mrs. Peacock":
				assertEquals(x.getLocation(), board.getBoardCellAt(17,22));
				break;
			case "Mrs. White":
				assertEquals(x.getLocation(), board.getBoardCellAt(0,19));
				break;
			case "Reverend Green":
				assertEquals(x.getLocation(), board.getBoardCellAt(10,22));
				break;
			default:
				fail("Incorrect Player found!");
				break;
				
			}
		}
		
	}
	
	//Tests the players have proper colors
	@Test
	public void testPlayerColors() {
		Map<String, Color> players = game.getPlayers();
		assertEquals(Color.YELLOW, players.get("Colonel Mustard"));
		assertEquals(Color.MAGENTA, players.get("Professor Plum"));
		assertEquals(Color.RED, players.get("Miss Scarlet"));
		assertEquals(Color.WHITE, players.get("Mrs. White"));
		assertEquals(Color.GREEN, players.get("Reverend Green"));
		assertEquals(Color.BLUE, players.get("Mrs. Peacock"));
	}
	
	//The following are DECK LOADING AND DEALING TESTS---------------------------
	//
	
	//Test that the correct number of cards are loaded
	@Test
	public void testDeckSize(){
		ArrayList<Card> testDeck = game.getDeck();
		assertEquals( NUM_DECK_CARDS, testDeck.size());
		
	}
	
	//Test that all the different cards are loaded
	@Test
	public void testAllCards(){
		ArrayList<Card> testDeck = game.getDeck();
		Set<String> cardNames =  new HashSet<String>();
		for(Card x: testDeck){
			cardNames.add(x.getName());
		}
	
		assertTrue(cardNames.contains("Colonel Mustard"));
		assertTrue(cardNames.contains("Professor Plum"));
		assertTrue(cardNames.contains("Miss Scarlet"));
		assertTrue(cardNames.contains("Mrs. Peacock"));
		assertTrue(cardNames.contains("Mrs. White"));
		assertTrue(cardNames.contains("Reverend Green"));
		assertTrue(cardNames.contains("Conservatory"));
		assertTrue(cardNames.contains("Kitchen"));
		assertTrue(cardNames.contains("Ballroom"));
		assertTrue(cardNames.contains("Billiard Room"));
		assertTrue(cardNames.contains("Library"));
		assertTrue(cardNames.contains("Study"));
		assertTrue(cardNames.contains("Dining Room"));
		assertTrue(cardNames.contains("Lounge"));
		assertTrue(cardNames.contains("Hall"));
		assertTrue(cardNames.contains("Knife"));
		assertTrue(cardNames.contains("Pipe"));
		assertTrue(cardNames.contains("Revolver"));
		assertTrue(cardNames.contains("Candlestick"));
		assertTrue(cardNames.contains("Rope"));
		assertTrue(cardNames.contains("Wrench"));
	}
	
	//Testing Accurate read-in of card types
	@Test
	public void testCardTypes(){
		ArrayList<Card> testDeck = game.getDeck();
		int people = 0;
		int rooms = 0;
		int weapons = 0;
		
		for(Card x: testDeck){
			switch(x.getCardType()){
			case PERSON:
				people++;
				break;
			case WEAPON:
				weapons++;
				break;
			case ROOM:
				rooms++;
				break;
				
				
			}
			
			
		}
		assertEquals(NUM_PLAYERS, people);
		assertEquals(NUM_ROOM_CARDS, rooms);
		assertEquals(NUM_WEAPONS, weapons);
		
	}

	//Test the Deal
	@Test
	public void testDeal(){
		int cardsDealt = 0;
		for(Player x: game.getGamePlayers()){
			assertTrue(x.getHand().size() < 5 && x.getHand().size() > 2);
			cardsDealt += x.getHand().size();
		}
		
		assertEquals(cardsDealt, NUM_CARDS);
	
	}
	
	//This test ensures that no card is dealt twice
	@Test
	public void testUniqueCards(){
		Set<Card> dealtCards = new HashSet<Card>();
		for(Player x: game.getGamePlayers()){
			for(Card y: x.getHand()){
				if(dealtCards.contains(y)){
					fail("card already dealt");
				}else{
					dealtCards.add(y);
				}
			}
		}
		assertEquals(dealtCards.size(), NUM_CARDS);
	}
	
	
}
