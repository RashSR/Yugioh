package cards;

public class Card {
	
	private CardType cardType;
	private String name;
	private String text;
	
	public Card(String name, String text, CardType cardType) {
		this.name = name;
		this.text = text;
		this.cardType = cardType;
	}

	public CardType getCardType() {
		return cardType;
	}

	public String getName() {
		return name;
	}

	public String getText() {
		return text;
	}	
	
}
