package game.map;

import cards.Card;
import game.Player;

public class FieldElement {
	
	private MonsterMode mm;
	private CardMode cm;
	private boolean isEmpty;
	private Player owner;
	private Card card;
	private int atkChange = 0;
	private int defChange = 0;
	
	public FieldElement(Card card, Player owner, MonsterMode mm, CardMode cm, boolean isEmpty) {
		this.card = card;
		this.owner = owner;
		this.mm = mm;
		this.cm = cm;
		this.isEmpty = isEmpty;
	}
	
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

	@Override
	public String toString() {
		return "FieldElement [mm=" + mm + ", cm=" + cm + ", isEmpty=" + isEmpty + ", owner=" + owner + ", card=" + card
				+ ", atkChange=" + atkChange + ", defChange=" + defChange + "]";
	}

	public boolean equals(FieldElement other) {
		if(other == null || other.getOwner() == null) {
			return false;
		}else if(other.getOwner().equals(owner)) {
			return true;
		}
		return false;
	}

	public int getAtkChange() {
		return atkChange;
	}

	public void setAtkChange(int atkChange) {
		this.atkChange = atkChange;
	}

	public int getDefChange() {
		return defChange;
	}

	public void setDefChange(int defChange) {
		this.defChange = defChange;
	}

}
