package cards.monster.ritual;

import cards.monster.MonsterCard;

public class RitualMonster extends MonsterCard {
	
	private String spellCard;
	
	public RitualMonster(String name, int atk, int def, String text, int stars, boolean hasEffect, String attribute, String type, String spellCard) {
		super(name, atk, def, text, stars, hasEffect, attribute, type);
		this.spellCard = spellCard;
	}
	
	public String getRitualSpellCard() {
		return this.spellCard;
	}
	
	@Override
	public String toString() {
		return getName() + " (ATK: " + getAtk() + ", def: " + getDef() + ", stars: " + getStars() 
		+ ", Effekt: " + hasEffect() + ", Attribute: " + getAttribute() + ", type: " + getMonsterType() + ", text: " + "Du kannst diese Karte mit \"" + spellCard + "\" als Ritualbeschwörung beschwören. " + getText() + ")"; 
	}

}
