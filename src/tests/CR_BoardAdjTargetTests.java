package tests;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ClueGame;

public class CR_BoardAdjTargetTests {
	private static Board board;
	@BeforeClass
	public static void setUp() throws BadConfigFormatException {
		ClueGame game = new ClueGame("ClueLayout2.csv", "ClueLegend2.txt");
		//game.loadConfigFiles();
		board = game.getBoard();
		board.calcAdjacencies();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		LinkedList<BoardCell> testList = board.getAdjList(0, 0);
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(4, 0);
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(15, 20);
		Assert.assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(18, 11);
		Assert.assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(14, 12);
		Assert.assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(5, 20);
		Assert.assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		LinkedList<BoardCell> testList = board.getAdjList(11, 6);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getBoardCellAt(11, 7)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(10, 17);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getBoardCellAt(10, 16)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(5, 15);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getBoardCellAt(6, 15)));
		//TEST DOORWAY UP
		testList = board.getAdjList(5, 15);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getBoardCellAt(6, 15)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		LinkedList<BoardCell> testList = board.getAdjList(4, 4);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(4, 3)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(4, 5)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(5, 4)));
		Assert.assertEquals(3, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(6, 15);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(5, 15)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(6, 14)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(6, 16)));
		Assert.assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(15, 17);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(15, 16)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(15, 18)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(14, 17)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(16, 17)));
		Assert.assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(13, 11);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(13, 10)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(13, 12)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(12, 11)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(14, 11)));
		Assert.assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, just one walkway piece
		LinkedList<BoardCell> testList = board.getAdjList(0, 4);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(0, 5)));
		Assert.assertEquals(1, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(6, 0);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(5, 0)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(6, 1)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(7, 0)));
		Assert.assertEquals(3, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(6, 21);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(6, 20)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(6, 22)));
		Assert.assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(15,7);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(15, 8)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(15, 6)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(14, 7)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(16, 7)));
		Assert.assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(21, 15);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(21, 16)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(20, 15)));
		Assert.assertEquals(2, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(14, 22);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(14, 21)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(13, 22)));
		Assert.assertEquals(2, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(5, 3);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(5, 2)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(5, 4)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(6, 3)));
		Assert.assertEquals(3, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(21, 7, 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(20, 7)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(21, 6)));	
		
		board.calcTargets(14, 0, 1);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(14, 1)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(13, 0)));	
		Assert.assertTrue(targets.contains(board.getBoardCellAt(15, 0)));			
	}
	
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(21, 7, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(19, 7)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(20, 6)));
		
		board.calcTargets(14, 0, 2);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(12, 0)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(14, 2)));	
		Assert.assertTrue(targets.contains(board.getBoardCellAt(15, 1)));			
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(21, 7, 4);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(17, 7)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(19, 7)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(18, 6)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(20, 6)));
		
		// Includes a path that doesn't have enough length
		board.calcTargets(14, 0, 4);
		targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(14, 4)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(15, 3)));	
		Assert.assertTrue(targets.contains(board.getBoardCellAt(14, 2)));	
		Assert.assertTrue(targets.contains(board.getBoardCellAt(15, 1)));	
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(14, 0, 6);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(14, 6)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(15, 5)));	
		Assert.assertTrue(targets.contains(board.getBoardCellAt(15, 3)));	
		Assert.assertTrue(targets.contains(board.getBoardCellAt(14, 4)));	
		Assert.assertTrue(targets.contains(board.getBoardCellAt(15, 1)));	
		Assert.assertTrue(targets.contains(board.getBoardCellAt(14, 2)));	
		Assert.assertTrue(targets.contains(board.getBoardCellAt(13, 4)));	
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(17, 16, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(7, targets.size());
		// directly left (can't go right 2 steps)
		Assert.assertTrue(targets.contains(board.getBoardCellAt(17, 14)));
		// directly up and down
		Assert.assertTrue(targets.contains(board.getBoardCellAt(15, 16)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(19, 16)));
		// one up/down, one left/right
		Assert.assertTrue(targets.contains(board.getBoardCellAt(18, 17)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(18, 15)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(16, 17)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(16, 15)));
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(12, 7, 3);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(12, targets.size());
		// directly up and down
		Assert.assertTrue(targets.contains(board.getBoardCellAt(15, 7)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(9, 7)));
		// directly right (can't go left)
		Assert.assertTrue(targets.contains(board.getBoardCellAt(12, 10)));
		// right then down
		Assert.assertTrue(targets.contains(board.getBoardCellAt(13, 9)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(13, 7)));
		// down then left/right
		Assert.assertTrue(targets.contains(board.getBoardCellAt(14, 6)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(14, 8)));
		// right then up
		Assert.assertTrue(targets.contains(board.getBoardCellAt(10, 8)));
		// into the rooms
		Assert.assertTrue(targets.contains(board.getBoardCellAt(11, 6)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(10, 6)));		
		// 
		Assert.assertTrue(targets.contains(board.getBoardCellAt(11, 7)));		
		Assert.assertTrue(targets.contains(board.getBoardCellAt(12, 8)));		
		
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(4, 20, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(4, 19)));
		// Take two steps
		board.calcTargets(4, 20, 2);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(3, 19)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(5, 19)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(4, 18)));
	}

}