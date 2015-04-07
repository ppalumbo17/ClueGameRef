package clueGame;

import java.awt.*;



public class Walkway extends BoardCell{
	
	@Override
	public boolean isWalkway() {
		// TODO Auto-generated method stub
		return true;
	}
	public void draw(Graphics g) {
		if(!flag){
			g.setColor(Color.YELLOW);
			g.fillRect(getColumn()*rectSize, getRow()*rectSize, rectSize, rectSize);
			g.setColor(Color.BLACK);
			g.drawRect(getColumn()*rectSize,getRow()*rectSize,rectSize,rectSize);
		}
		else{
			g.setColor(new Color(0,255,255));
			g.fillRect(getColumn()*rectSize, getRow()*rectSize, rectSize, rectSize);
			g.setColor(Color.BLACK);
			g.drawRect(getColumn()*rectSize,getRow()*rectSize,rectSize,rectSize);
		}
	}
	
	
}
