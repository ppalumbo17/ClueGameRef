package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.RoomCell;
import clueGame.Solution;
import clueGame.Suggestion;
import clueGame.Walkway;

public class GameActionTests {
	
	private static Board board;
	private static ClueGame game;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 23;
	public static final int NUM_PLAYERS = 6;
	public static final int NUM_CARDS = 21;
	
	private static Card mustardCard;
	private static Card greenCard;
	private static Card hallCard;
	private static Card conservatoryCard;
	private static Card wrenchCard;
	private static Card knifeCard;
	
	
	@BeforeClass
	public static void setUp() throws BadConfigFormatException {
		game = new ClueGame("ClueLayout.csv", "RoomLegend.txt", "Players.txt", "Deck.txt");
		board = game.getBoard();
		
		mustardCard = new Card("Colonel Mustard", Card.CardType.PERSON);
		greenCard = new Card("Reverend Green", Card.CardType.PERSON);
		hallCard = new Card("Hall", Card.CardType.ROOM);
		conservatoryCard = new Card("Conservatory", Card.CardType.ROOM);
		wrenchCard = new Card("Wrench", Card.CardType.WEAPON);
		knifeCard = new Card("Knife", Card.CardType.WEAPON);
	}
	
	
	//The following are ACCUSATION TESTS --------------------
	//
	
	@Test
	public void testAccusations() {
		game.setSolution(new Solution("Colonel Mustard", "Conservatory", "Knife"));
		
		//Correct Accusations
		Solution testAccurateSolution = new Solution("Colonel Mustard", "Conservatory", "Knife");
		assertTrue(game.checkAccusation(testAccurateSolution));
		
		//Incorrect Person
		Solution testInaccuratePerson = new Solution("Reverend Green", "Conservatory", "Knife");
		assertFalse(game.checkAccusation(testInaccuratePerson));
		
		//Incorrect Room
		Solution testInaccurateRoom = new Solution("Colonel Mustard", "Kitchen", "Knife");
		assertFalse(game.checkAccusation(testInaccurateRoom));
		
		//Inaccurate Weapon
		Solution testInaccurateWeapon = new Solution("Colonel Mustard", "Conservatory", "Wrench");
		assertFalse(game.checkAccusation(testInaccurateWeapon));
		
		//All three wrong
		Solution testInaccurateAll = new Solution("Reverend Green", "Kitchen", "Wrench");
		assertFalse(game.checkAccusation(testInaccurateAll));
	}
	
	//The following are DISPROVING SUGGESTION TESTS -------------------------------
	//
	
	//These tests are for the suggestion checking function
	@Test
	public void disproveSuggestionTests() {
		Player testPlayer = new Player("Colonel Mustard", Color.YELLOW, new Walkway());
		testPlayer.getHand().add(mustardCard);
		testPlayer.getHand().add(greenCard);
		testPlayer.getHand().add(hallCard);
		testPlayer.getHand().add(conservatoryCard);
		testPlayer.getHand().add(knifeCard);
		testPlayer.getHand().add(wrenchCard);
		
		//Tests the correct person is returned
		assertEquals("Colonel Mustard", game.handleSuggestion("Colonel Mustard", "Billiard Room", "Pipe", testPlayer));
		assertEquals("Reverend Green", game.handleSuggestion("Reverend Green", "Billiard Room", "Pipe", testPlayer));
		
		//Tests that the correct room is returned
		assertEquals("Hall", game.handleSuggestion("Miss Scarlet", "Hall", "Pipe", testPlayer));
		assertEquals("Conservatory", game.handleSuggestion("Miss Scarlet", "Conservatory", "Pipe", testPlayer));

		//Tests that the correct weapon is returned
		assertEquals("Knife", game.handleSuggestion("Miss Scarlet", "Billiard Room", "Knife", testPlayer));
		assertEquals("Wrench", game.handleSuggestion("Miss Scarlet", "Billiard Room", "Wrench", testPlayer));

		//Tests that nothing is returned if the player has none of the cards
		assertEquals(null, game.handleSuggestion("Miss Scarlet", "Billiard Room", "Pipe", testPlayer));

		//Tests that the player will randomly return one valid card if asked for several
		int roomCount = 0;
		int weaponCount = 0;
		int personCount = 0;
		String result;
		
		for(int i=0;i<20;i++){
			result = game.handleSuggestion("Colonel Mustard", "Conservatory", "Knife", testPlayer);
			switch(result){
			case "Colonel Mustard":
				personCount++;
				break;
			case "Conservatory":
				roomCount++;
				break;
			case "Knife":
				weaponCount++;
				break;
			default:
					fail("wrong card returned!");
					break;
					
			}
			
			
		}
		
		assertTrue(roomCount > 0);
		assertTrue(weaponCount >0);
		assertTrue(personCount > 0);
	}
	
	//Tests suggestions made to all the players, and the responses
	@Test
	public void testAllPlayersQueried(){
		ArrayList<Player> testPlayers = new ArrayList<Player>();
		Player testPlayer1 = new HumanPlayer("test", Color.GREEN, new Walkway());
		Player testPlayer2 = new ComputerPlayer("test", Color.GREEN, new Walkway());
		Player testPlayer3 = new ComputerPlayer("test", Color.GREEN, new Walkway());
		Player testPlayer4 = new ComputerPlayer("test", Color.GREEN, new Walkway());
		
		testPlayer1.getHand().add(mustardCard);
		testPlayer2.getHand().add(greenCard);
		testPlayer3.getHand().add(knifeCard);
		testPlayer4.getHand().add(conservatoryCard);
		
		testPlayers.add(testPlayer1);
		testPlayers.add(testPlayer2);
		testPlayers.add(testPlayer3);
		testPlayers.add(testPlayer4);
		
		game.setGamePlayersForTest(testPlayers);
		
		//Test that nothing is returned if no player has the suggested card
		assertEquals(null, game.checkSuggestion("Miss Scarlet", "Hall", "Pipe", testPlayer1));

		//Test that the human player can disprove correctly
		assertEquals("Colonel Mustard", game.checkSuggestion("Colonel Mustard", "Hall", "Pipe", testPlayer2));
		
		//Test that the one making the suggestion cannot disprove it
		assertEquals(null, game.checkSuggestion("Colonel Mustard", "Hall", "Pipe", testPlayer1));
		
		//Test that the first player disproves the suggestion rather than the second
		assertEquals("Colonel Mustard", game.checkSuggestion("Colonel Mustard", "Hall", "Knife", testPlayer2));
		
		//Test that all players are checked up to the last
		assertEquals("Conservatory", game.checkSuggestion("Miss Scarlet", "Conservatory", "Pipe", testPlayer1));
	}
	
	//The following are COMPUTER TARGET SELECTION TESTS --------------------------
	//
	//From here on out, we will be using the list of players established in the last test -the human is the first,
	//and the rest are computer players
	
	//This test ensures that the room is picked from a list of cells, unless it was the last room visited
	@Test
	public void testPickRoomFromList(){
		ComputerPlayer testPlayer = (ComputerPlayer)game.getGamePlayers().get(1);
		
		//Test that the computer prefers a room over a walkway cell
		Set<BoardCell> roomTest = new HashSet<BoardCell>();
		roomTest.add(board.getBoardCellAt(18, 13)); // the room cell
		roomTest.add(board.getBoardCellAt(21, 14));
		roomTest.add(board.getBoardCellAt(19, 15));
		testPlayer.setLastRoomVisited(' '); 
		for(int i = 0; i<50;i++){
			assertEquals(board.getBoardCellAt(18, 13), testPlayer.pickLocation(roomTest));
			testPlayer.setLastRoomVisited(' '); //make the computer forget it just entered the room
		}
		
		//make it so it can choose between a walkway, the room it was just in, and another room
		//Should NOT pick the last room, since it is now the last visited.
		roomTest.remove(board.getBoardCellAt(19, 15)); 
		roomTest.add(board.getBoardCellAt(19, 17));
		testPlayer.setLastRoomVisited('D');
		
		//Ensure the other room is chosen every time
		for(int i = 0; i < 50;i++){
			assertEquals(board.getBoardCellAt(19, 17), testPlayer.pickLocation(roomTest));
		}
		
	}
	
	//Test that all locations are chosen equally
	@Test
	public void testTargetSelectionRandom(){
		ComputerPlayer testPlayer = (ComputerPlayer)game.getGamePlayers().get(1);
		
		int choice1 = 0;
		int choice2 = 0;
		int choice3 = 0;
		Set<BoardCell> roomTest = new HashSet<BoardCell>();
		roomTest.add(board.getBoardCellAt(21, 14));
		roomTest.add(board.getBoardCellAt(19, 15));
		roomTest.add(board.getBoardCellAt(18,14));
		BoardCell chosen;
		for(int i = 0; i < 100; i++){
			chosen = testPlayer.pickLocation(roomTest);
			if(chosen == board.getBoardCellAt(18,14)){
				choice1++;
			}else if (chosen  == board.getBoardCellAt(21,14)){
				choice2++;
				
			}else if (chosen == board.getBoardCellAt(19, 15)){
				choice3++;
			}else{
				fail("incorrect room chosen!");
			}
		}
		//Ensure 100 total choices were made
		assertEquals(100, (choice1 + choice2 + choice3));
		
		//Make sure each was chosen more than once
		assertTrue(choice1 > 1);
		assertTrue(choice2 > 1);
		assertTrue(choice3 > 1);
	}
	
	//Tests that if a room is the last one visited, but no other room entry exists, the choice is still random
	@Test
	public void testRandomChoiceRoomJustVisited(){
		ComputerPlayer testPlayer = (ComputerPlayer)game.getGamePlayers().get(1);
			
		int choice1 = 0;
		int choice2 = 0;
		int choice3 = 0;
		Set<BoardCell> roomTest = new HashSet<BoardCell>();
		roomTest.add(board.getBoardCellAt(21, 14));
		roomTest.add(board.getBoardCellAt(19, 15));
		roomTest.add(board.getBoardCellAt(18,14));
		BoardCell chosen;
		for(int i = 0; i < 100; i++){
			testPlayer.setLastRoomVisited('D');
			chosen = testPlayer.pickLocation(roomTest);
			if(chosen == board.getBoardCellAt(18,14)){
				choice1++;
			}else if (chosen  == board.getBoardCellAt(21,14)){
				choice2++;
				
			}else if (chosen == board.getBoardCellAt(19, 15)){
				choice3++;
			}else{
				fail("incorrect room chosen!");
			}
		}
		//Ensure 100 total choices were made
		assertEquals(100, (choice1 + choice2 + choice3));
		
		//Make sure each was chosen more than once
		assertTrue(choice1 > 1);
		assertTrue(choice2 > 1);
		assertTrue(choice3 > 1);
	}
	
	
	//The following are COMPUTER SUGGESTION TESTS ----------------------
	//
	@Test
	public void testOnePossibleSuggestion(){
		ComputerPlayer testPlayer = (ComputerPlayer)game.getGamePlayers().get(1);
		ArrayList<Card> testSeen = new ArrayList<Card>();
		
		testPlayer.getHand().clear();
		testPlayer.getHand().add(mustardCard);
		testPlayer.getHand().add(greenCard);
		testPlayer.getHand().add(hallCard);
		testPlayer.getHand().add(conservatoryCard);
		testPlayer.getHand().add(knifeCard);
		testPlayer.getHand().add(wrenchCard);
		
		//Makes sure the player only thinks the cards in their hand exist in the game
		game.getPeopleCards().clear();
		game.getweaponsCards().clear();
		game.setDeck(testPlayer.getHand());
		game.SetCardTypes(game.getDeck());
		
		testPlayer.getSeen().clear();
		testSeen.add(mustardCard);
		testSeen.add(hallCard);
		testSeen.add(knifeCard);
		testPlayer.setSeen(testSeen);

		
		testPlayer.setLocation(board.getBoardCellAt(18, 13));
		
		//The computer player should suggest Mr. Green, in the Dining Room, with the wrench, since he has seen all other player/weapon options,
		//and is currently in the Dining Room
		Suggestion testSuggestion = new Suggestion("Reverend Green", "Dining Room", "Wrench");
		
		System.out.println(testPlayer.makeSuggestion(game.getPeopleCards(), game.getweaponsCards()).toString());
		
		assertTrue(testSuggestion.equals(testPlayer.makeSuggestion(game.getPeopleCards(), game.getweaponsCards())));
		
		
	}
	
	//This tests a situation where the computer may choose between a couple of different suggestions
	@Test
	public void testMultipleSuggestionChoices(){
		ComputerPlayer testPlayer = (ComputerPlayer)game.getGamePlayers().get(1);
		ArrayList<Card> testSeen = new ArrayList<Card>();
		
		testPlayer.getHand().clear();
		testPlayer.getHand().add(mustardCard);
		testPlayer.getHand().add(greenCard);
		testPlayer.getHand().add(hallCard);
		testPlayer.getHand().add(conservatoryCard);
		testPlayer.getHand().add(knifeCard);
		testPlayer.getHand().add(wrenchCard);
		
		game.getPeopleCards().clear();
		game.getweaponsCards().clear();
		game.setDeck(testPlayer.getHand());
		game.SetCardTypes(game.getDeck());
		System.out.println(game.getPeopleCards());
		System.out.println(game.getweaponsCards());
		
		testPlayer.getSeen().clear();
		testSeen.add(hallCard);
		testSeen.add(knifeCard);
		testPlayer.setSeen(testSeen);
		
		testPlayer.setLocation(board.getBoardCellAt(18, 13));
		testPlayer.setLastRoomVisited('D');
		
		System.out.println(testPlayer.makeSuggestion(game.getPeopleCards(), game.getweaponsCards()).toString());
		
		Suggestion testSuggestion = new Suggestion("Reverend Green", "Dining Room", "Wrench");
		Suggestion testSuggestion2 = new Suggestion("Colonel Mustard", "Dining Room", "Wrench");
		int count1 = 0;
		int count2 = 0;
		
		for(int i =0; i< 50;i++){
			Suggestion result = testPlayer.makeSuggestion(game.getPeopleCards(), game.getweaponsCards());
			if(testSuggestion.equals(result)){
				count1++;
			}else if(testSuggestion2.equals(result)){
				count2++;
			}else{
				fail("incorrect suggestion returned");
			}
		}
		
		assertEquals(50, (count1+count2));
		
		assertTrue(count1 > 1);
		assertTrue(count2 > 1);
	}
	

}
