package tests;

// Doing a static import allows me to write assertEquals rather than
// Assert.assertEquals
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ClueGame;
import clueGame.RoomCell;

public class InstructorTests {
	// I made this static because I only want to set it up one 
	// time (using @BeforeClass), no need to do setup before each test
	private static Board board;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 23;
	
	@BeforeClass
	public static void setUp() throws BadConfigFormatException {
		ClueGame game = new ClueGame("ClueLayout2.csv", "ClueLegend2.txt");
		//game.loadConfigFiles("ClueLayout2.csv");
		board = game.getBoard();
	}
	@Test
	public void testRooms() {
		Map<Character, String> rooms = board.getRooms();
		// Ensure we read the correct number of rooms
		assertEquals(NUM_ROOMS, rooms.size());
		// Test retrieving a few from the hash, including the first
		// and last in the file and a few others
		assertEquals("Conservatory", rooms.get('C'));
		assertEquals("Ballroom", rooms.get('B'));
		assertEquals("Billiard room", rooms.get('R'));
		assertEquals("Dining room", rooms.get('D'));
		assertEquals("Walkway", rooms.get('W'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	// Test a doorway in each direction, plus two cells that are not
	// a doorway.
	// These cells are white on the planning spreadsheet
	@Test
	public void FourDoorDirections() {
		// Test one each RIGHT/LEFT/UP/DOWN
		RoomCell room = board.getRoomCellAt(4, 3);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getRoomCellAt(4, 8);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
		room = board.getRoomCellAt(15, 18);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
		room = board.getRoomCellAt(14, 11);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());
		// Test that room pieces that aren't doors know it
		room = board.getRoomCellAt(14, 14);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		BoardCell cell = board.getBoardCellAt(0, 6);
		assertFalse(cell.isDoorway());		

	}
	
	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		int totalCells = board.getNumColumns() * board.getNumRows();
		Assert.assertEquals(506, totalCells);
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getBoardCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(16, numDoors);
	}

	// Test a few room cells to ensure the room initial is
	// correct.
	@Test
	public void testRoomInitials() {
		assertEquals('C', board.getRoomCellAt(0, 0).getInitial());
		assertEquals('R', board.getRoomCellAt(4, 8).getInitial());
		assertEquals('B', board.getRoomCellAt(9, 0).getInitial());
		assertEquals('O', board.getRoomCellAt(21, 22).getInitial());
		assertEquals('K', board.getRoomCellAt(21, 0).getInitial());
	}
	
	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Game ctor takes config file names
		ClueGame game = new ClueGame("ClueLayoutBadColumns2.csv", "ClueLegend2.txt");
		// You may change these calls if needed to match your function names
		// My loadConfigFiles has a try/catch, so I can't call it directly to
		// see test throwing the BadConfigFormatException
		game.loadRoomConfig("ClueLegend2.txt");
		game.getBoard().loadBoardConfig("ClueLayoutBadColumns2.csv");
	}
	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Board ctor takes config file name
		ClueGame game = new ClueGame("ClueLayoutBadRoom2.csv", "ClueLegend2.txt");
		game.loadRoomConfig("ClueLegend2.txt");
		game.getBoard().loadBoardConfig("ClueLayoutBadRoom2.csv");
	}
	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Board ctor takes config file name
		ClueGame game = new ClueGame("ClueLayout2.csv", "ClueLegendBadFormat2.txt");
		game.loadRoomConfig("ClueLegendBadFormat2.txt");
		game.getBoard().loadBoardConfig("ClueLayout2.csv");
	}
}