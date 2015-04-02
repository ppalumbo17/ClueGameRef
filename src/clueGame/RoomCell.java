package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class RoomCell extends BoardCell{;
	static public DoorDirection DoorDirection;
	private char roomInitial;
	
	
	public RoomCell(String room) throws BadConfigFormatException {
		super();
		
		roomInitial = room.charAt(0);
		if (room.length() == 2){
			if(room.charAt(1) == 'R'){
				super.dd = DoorDirection.RIGHT;
			}
			else if(room.charAt(1) == 'L'){
				super.dd = DoorDirection.LEFT;
			}
			else if(room.charAt(1) == 'U'){
				super.dd = DoorDirection.UP;
			}
			else if(room.charAt(1) == 'D'){
				super.dd = DoorDirection.DOWN;
			}
			else if(room.charAt(1) == 'N'){
				super.dd = DoorDirection.NONE;
			}
			else {
				throw new BadConfigFormatException();
			}
		}
		else {
			super.dd = DoorDirection.NONE;
		}
		
	}
	
	


	@Override
	public boolean isRoom() {
		return true;
	}




	@Override
	public boolean isDoorway() {
		if (super.dd == DoorDirection.NONE){
			return false;
		}else {
			return true;
		}
	}


	public char getRoomInitial(){
		return roomInitial;
	}

	public DoorDirection getDoorDirection() {
		return super.dd;
	}
	public char getInitial() {
		return roomInitial;
	}



	@Override
	public void draw(Graphics g, Board b) {
		int rowpix = getRow();
		int colpix = getColumn();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(colpix*rectSize, rowpix*rectSize, rectSize, rectSize);
		g.setColor(Color.BLUE);
		if(isDoorway()){
			switch(dd){
			case DOWN:
				g.fillRect(colpix*rectSize, rowpix*rectSize + (rectSize - rectSize/5) , rectSize, rectSize/5);
				break;
			case LEFT:
				g.fillRect(colpix*rectSize, rowpix*rectSize, rectSize/5, rectSize);
				break;
			case RIGHT:
				g.fillRect(colpix*rectSize + (rectSize - rectSize/5), rowpix*rectSize, rectSize/5, rectSize);
				break;
			case UP:
				g.fillRect(colpix*rectSize, rowpix*rectSize, rectSize, rectSize/5);
				break;
			}
		}
		
	}

	
}
