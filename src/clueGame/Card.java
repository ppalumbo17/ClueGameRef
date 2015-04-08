package clueGame;

public class Card {
	private String name;
	public enum CardType {ROOM,WEAPON,PERSON}
	private CardType cardType;
	
	public Card(){
		name="";
	}
	
	public Card(String name, CardType cardType){
		super();
		this.name = name;
		this.cardType = cardType;
	}
	public Card(String name, char cardType) {
		super();
		this.name = name;
		
		switch(cardType){
		case 'P':
			this.cardType = CardType.PERSON;
			break;
		case 'W':
			this.cardType = CardType.WEAPON;
			break;
		case 'R':
			this.cardType = CardType.ROOM;
			break;
			
		}
	}
	@Override
	public boolean equals(Object o){
		Card d=(Card)o;
		return this.name.equals(d.name);
	}
	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	};
	
	
}
