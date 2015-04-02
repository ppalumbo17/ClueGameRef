package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class Player {
	private String name;
	private Color color;
	private ArrayList<Card> hand = new ArrayList<Card>();
	private BoardCell location;
	protected ArrayList<Card> seen = new ArrayList<Card>();
	private int rectSize = 30;
	
	public Player(String name, Color color, BoardCell location){
		this.name =  name;
		this.color = color;
		this.location = location;
	}
	
	//Set location method for initial board setup
	public void setLocation(BoardCell currentLocation){
		location = currentLocation;
		
	}
	public BoardCell getLocation(){
		return location;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setColor(Color color){
		this.color = color;
	}
	public ArrayList<Card> getHand(){
		return hand;
	}
	public void setSeen(ArrayList<Card> seen){
		this.seen = seen;
	}
	public ArrayList<Card> getSeen(){
		return this.seen;
	}
	public void seenCard(Card card){
		seen.add(card);
	}
	public void draw(Graphics g, Board b){
		g.setColor(color);
		g.fillOval(location.getColumn()*rectSize, location.getRow()*rectSize, rectSize, rectSize);
		g.setColor(Color.black);
		g.drawOval(location.getColumn()*rectSize, location.getRow()*rectSize, rectSize, rectSize);
	}
}
