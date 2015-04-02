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

public class OurAdjTargetTests {
	private static Board board;
	@BeforeClass
	public static void setUp() throws BadConfigFormatException {
		ClueGame game = new ClueGame("ClueLayout.csv", "RoomLegend.txt");
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
		testList = board.getAdjList(6,21);
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(18,12);
		Assert.assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(19,21);
		Assert.assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(11,20);
		Assert.assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(0,9);
		Assert.assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY DOWN 
		LinkedList<BoardCell> testList = board.getAdjList(3,2);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getBoardCellAt(4,2)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(19,7);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getBoardCellAt(19,6)));
		//TEST DOORWAY RIGHT
		testList = board.getAdjList(16,3);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getBoardCellAt(16, 4)));
		//TEST DOORWAY UP
		testList = board.getAdjList(12,19);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getBoardCellAt(11,19)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		LinkedList<BoardCell> testList = board.getAdjList(0,5);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(0,4)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(1,5)));
		Assert.assertEquals(2, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(4,2);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(3,2)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(5,2)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(4,1)));
		Assert.assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(19,6);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(19,7)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(19,5)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(20,6)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(18,6)));
		Assert.assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(11,19);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(12,19)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(10,19)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(11,18)));
		Assert.assertEquals(3, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		LinkedList<BoardCell> testList = board.getAdjList(0, 4);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(0, 5)));
		Assert.assertEquals(1, testList.size());
		
		testList = board.getAdjList(6, 0);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(5, 0)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(6, 1)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(7, 0)));
		Assert.assertEquals(3, testList.size());

		testList = board.getAdjList(9,19);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(9,20)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(9,18)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(10,19)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(8,19)));
		Assert.assertEquals(4, testList.size());

		testList = board.getAdjList(19,16);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(19,15)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(18,16)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(20,16)));
		Assert.assertEquals(3, testList.size());
		
		testList = board.getAdjList(17,22);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(16,22)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(17,21)));
		Assert.assertEquals(2, testList.size());
		
		testList = board.getAdjList(14,14);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(14, 15)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(13,14)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(15,14)));
		Assert.assertEquals(3, testList.size());

		testList = board.getAdjList(4,18);
		Assert.assertTrue(testList.contains(board.getBoardCellAt(4,19)));
		Assert.assertTrue(testList.contains(board.getBoardCellAt(5,18)));
		Assert.assertEquals(2, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(11,0, 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(11,1)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(12,0)));	
		
		board.calcTargets(17,4, 1);
		targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(17,5)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(17,3)));	
		Assert.assertTrue(targets.contains(board.getBoardCellAt(18,4)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(16,4)));
	}
	
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(0,19, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(2,19)));
		
		board.calcTargets(18,17, 2);
		targets= board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(19,17)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(17,18)));	
		Assert.assertTrue(targets.contains(board.getBoardCellAt(16,17)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(19,16)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(18,15)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(17,16)));
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(0,5, 4);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(0,4)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(3,6)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(4,5)));
		
		board.calcTargets(17,22, 4);
		targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(16,19)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(17,20)));	
		Assert.assertTrue(targets.contains(board.getBoardCellAt(16,21)));	
		Assert.assertTrue(targets.contains(board.getBoardCellAt(17,18)));
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(0,19, 6);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(6,19)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(5,18)));	
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(1,5, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(0,4)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(3,5)));
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(4,2,2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(3,2)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(4,0)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(5,1)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(6,2)));
		
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(19,17, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(18,17)));
		// Take two steps
		board.calcTargets(15,17, 2);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getBoardCellAt(16,18)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(17,17)));
		Assert.assertTrue(targets.contains(board.getBoardCellAt(16,16)));
	}

}