package clueGame;

public class Solution {
	private String person;
	private String weapon;
	private String room;
	
	public Solution(String person,  String room,String weapon) {
		super();
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}
	
	public String getPerson() {
		return person;
	}
	public String getWeapon() {
		return weapon;
	}
	public String getRoom() {
		return room;
	}
	
	
}
