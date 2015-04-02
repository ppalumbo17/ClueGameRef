package clueGame;

public class Suggestion {
	private String player;
	private String room;
	private String weapon;
	
	public Suggestion(){
		super();
	}
	
	@Override
	public String toString() {
		return "Suggestion [player=" + player + ", room=" + room + ", weapon="
				+ weapon + "]";
	}

	@Override
	public boolean equals(Object suggestion){
		Suggestion suggestion2 = (Suggestion)suggestion;
		
		if(suggestion2.getPlayer().equals(this.getPlayer())
			&& suggestion2.getRoom().equals(this.getRoom())
			&& suggestion2.getWeapon().equals(this.getWeapon())){
			return true;
		}
		
		return false;
	}
	public Suggestion(String player, String room, String weapon){
		this.player = player;
		this.room = room;
		this.weapon = weapon;
	}
	
	public String getPlayer(){
		return this.player;
	}
	
	public String getRoom(){
		return this.room;
		
	}
	
	public String getWeapon(){
		return this.weapon;
	}
}
