package game.map;

import cards.Card;
import cards.monster.MonsterCard;
import game.Player;
import game.effects.MonsterEffects;

public class FieldElement {

	private MonsterMode mm;
	private CardMode cm;
	private boolean isEmpty;
	private boolean canChangeMonsterMode;
	private Player owner;
	private Card card;
	private int atkChange = 0;
	private int defChange = 0;
	private int atkCount = 1;

	public FieldElement(Card card, Player owner, MonsterMode mm, CardMode cm, boolean isEmpty) {
		this.card = card;
		this.owner = owner;
		this.mm = mm;
		this.cm = cm;
		this.isEmpty = isEmpty;
		this.canChangeMonsterMode = false;
	}

	public FieldElement() {
		isEmpty = true;
	}

	public FieldElement(Player owner) {
		isEmpty = true;
		this.owner = owner;
	}

	public void changeCardMode(PlayField pf) {
		cm = cm.changeMode();
		if(card instanceof MonsterCard) {
			MonsterCard mc = (MonsterCard)card;
			if(mc.isFlipp()) {
				MonsterEffects.activateEffect(mc, pf);
			}
		}
	}

	public void changeMonsterMode(PlayField pf) {
		if(canChangeMonsterMode) {
			mm = mm.changeMode();
			if(cm == CardMode.FACE_DOWN) {
				changeCardMode(pf);
			}
			canChangeMonsterMode = false;
		}else {
			System.out.println("Can't change MonsterMode of this Card.");
		}
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((card == null) ? 0 : card.hashCode());
		result = prime * result + (isEmpty ? 1231 : 1237);
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldElement other = (FieldElement) obj;
		if (card == null) {
			if (other.card != null)
				return false;
		} else if (!card.equals(other.card))
			return false;
		if (isEmpty != other.isEmpty)
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
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

	public void addToAtkChange(int value) {
		this.atkChange += value;
	}

	public void addToDefChange(int value) {
		this.defChange += value;
	}

	public int getAtk() {
		MonsterCard mc = (MonsterCard) this.card;
		return mc.getAtk() + atkChange;
	}

	public int getDef() {
		MonsterCard mc = (MonsterCard) this.card;
		return mc.getDef() + defChange;
	}

	public int getAtkCount() {
		return this.atkCount;
	}

	public void setAtkCount(int count) {
		this.atkCount = count;
	}

	public void attack() {
		this.atkCount--;
		canChangeMonsterMode = false;
	}

	public void setMonsterModeChange(boolean canChangeMonsterMode) {
		this.canChangeMonsterMode = canChangeMonsterMode;
	}

	public boolean canChangeMonsterMode() {
		return this.canChangeMonsterMode;
	}

}
