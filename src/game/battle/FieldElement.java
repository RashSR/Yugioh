package game.battle;

import cards.Card;
import game.Player;

public class FieldElement {
	private MonsterMode mm;
	private CardMode cm;
	private boolean isEmpty;
	private Player owner;
	private Card card;

	public FieldElement() {
		isEmpty = true;
	}
	
	public FieldElement(Player owner) {
		isEmpty = true;
		this.owner = owner;
	}
	
	public void changeCardMode() {
		cm = cm.changeMode();
	}
	
	public void changeMonsterMode() {
		mm = mm.changeMode();	
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public MonsterMode getMonsterMode() {
		return mm;
	}

	public void setMonsterMode(MonsterMode mm) {
		this.mm = mm;
	}

	public CardMode getCardMode() {
		return cm;
	}

	public void setCardMode(CardMode cm) {
		this.cm = cm;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}


}
