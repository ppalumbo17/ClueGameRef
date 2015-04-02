package clueGame;

import java.awt.*;



public class Walkway extends BoardCell{

	
	@Override
	public boolean isWalkway() {
		// TODO Auto-generated method stub
		return true;
	}
	

	public void draw(Graphics g, Board b) {
		g.setColor(Color.YELLOW);
		g.fillRect(getColumn()*rectSize, getRow()*rectSize, rectSize, rectSize);
		g.setColor(Color.BLACK);
		g.drawRect(getColumn()*rectSize,getRow()*rectSize,rectSize,rectSize);
		
	}
	
	
}
