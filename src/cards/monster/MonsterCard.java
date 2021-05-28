package cards.monster;

import cards.Card;
import cards.CardType;

public class MonsterCard extends Card{
	/*
	 * This class contains all functions to use and modify a Yu-Gi-Oh Monster-Card
	 */
	private int atk;
	private int def;
	private int stars;
	private boolean hasEffect;
	private boolean isFlipp;
	private Attribute attribute;
	private MonsterType type;

	public MonsterCard(String name, int atk, int def, String text, int stars, boolean hasEffect, String attribute, String type) {
		super(name, text, CardType.MONSTER);
		this.atk = atk;
		this.def = def;
		this.stars = stars;
		this.hasEffect = hasEffect;
		if(hasEffect) {
			setFlipp();
		}
		this.attribute = createAttribute(attribute);
		this.type = createType(type);
	}

	public MonsterCard(String name, int atk, int def, String text, int stars, boolean hasEffect, Attribute attribute, MonsterType type) {
		super(name, text, CardType.MONSTER);
		this.atk = atk;
		this.def = def;
		this.stars = stars;
		this.hasEffect = hasEffect;
		if(hasEffect) {
			setFlipp();
		}
		this.attribute = attribute;
		this.type = type;
	}
	
	/*
	 * Get the Attribute from the corresponding String
	 */
	private Attribute createAttribute(String attribute) {
		switch (attribute) {
		case "Finsternis":
			return Attribute.FINSTERNIS;
		case "Licht":
			return Attribute.LICHT;
		case "Erde":
			return Attribute.ERDE;
		case "Wasser":
			return Attribute.WASSER;
		case "Feuer": 
			return Attribute.FEUER;
		case "Wind":
			return Attribute.WIND;
		}
		return null;
	}

	/*
	 * Get the MonsterType from the corresponding String
	 */
	private MonsterType createType(String type) {
		switch(type) {
		case "Hexer":
			return MonsterType.HEXER;
		case "Insekt":
			return MonsterType.INSEKT;
		case "Drache":
			return MonsterType.DRACHE;
		case "Krieger":
			return MonsterType.KRIEGER;
		case "Zombie":
			return MonsterType.ZOMBIE;
		case "Fels":
			return MonsterType.FELS;
		case "Ungeheuer":
			return MonsterType.UNGEHEUER;
		case "Fee":
			return MonsterType.FEE;
		case "Pflanze":
			return MonsterType.PFLANZE;
		case "Ungeheuer-Krieger":
			return MonsterType.UNGEHEUER_KRIEGER;
		case "Maschine":
			return MonsterType.MASCHINE;
		case "Aqua":
			return MonsterType.AQUA;
		case "Dinosaurier":
			return MonsterType.DINOSAURIER;
		case "Unterweltler":
			return MonsterType.UNTERWELTLER;
		case "Fisch":
			return MonsterType.FISCH;
		case "Pyro":
			return MonsterType.PYRO;
		case "Reptil":
			return MonsterType.REPTIL;
		case "Donner":
			return MonsterType.DONNER;
		case "Gefluegeltes-Ungeheuer":
			return MonsterType.GEFLUEGELTES_UNGEHEUER;
		case "Seeschlange":
			return MonsterType.SEESCHLANGE;
		}
		return null;
	}
	
	/*
	 * If a Monster has a flipp-effect this function sets the boolean isFlipp to true
	 */
	private void setFlipp() {
		if(getText().startsWith("FLIPP")) {
			this.isFlipp = true;
		}
	}

	@Override
	public String toString() {
		return getName() + " (ATK: " + atk + ", def: " + def + ", stars: " + stars + ", Effekt: " + hasEffect 
				+ ", Attribute: " + attribute + ", type: " + type + ", text: " + getText() + ")"; 
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}


	public int getStars() {
		return stars;
	}


	public boolean hasEffect() {
		return hasEffect;
	}


	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public MonsterType getMonsterType() {
		return type;
	}

	public void setMonsterType(MonsterType type) {
		this.type = type;
	}
	
	public boolean isFlipp() {
		return this.isFlipp;
	}

}
