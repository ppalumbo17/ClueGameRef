package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public abstract class BoardCell {
	private int row, column;
	protected DoorDirection dd;
	protected int rectSize = 30;
	protected boolean flag;
	
	public boolean isWalkway(){
		return false;	
	}
	public boolean isRoom(){
		return false;
	}
	public boolean isDoorway(){
		return false;
	}
	public void setFlag(boolean b){
		flag=b;
	}
	public boolean getFlag(){
		return flag;
	}
	// Add method draw when making GUI
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	
	abstract void draw(Graphics g);
	
}
