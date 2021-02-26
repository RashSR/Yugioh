package cards.monster.fusion;

import cards.monster.MonsterCard;

public class FusionMonster extends MonsterCard{
	
	private String monster1;
	private String monster2;
	
	public FusionMonster(String name, int atk, int def, String text, int stars, boolean hasEffect, String attribute, 
			String type, String monster1, String monster2) {
		super(name, atk, def, text, stars, hasEffect, attribute, type);
		this.monster1 = monster1;
		this.monster2 = monster2;
	}

	public String getMonster1() {
		return monster1;
	}

	public String getMonster2() {
		return monster2;
	}
	
	@Override
	public String toString() {
		return getName() + " (ATK: " + getAtk() + ", def: " + getDef() + ", stars: " + getStars() 
		+ ", Effekt: " + hasEffect() + ", Attribute: " + getAttribute() + ", type: " + getMonsterType() + ", text: " + getText() + ")"; 
	}
}
