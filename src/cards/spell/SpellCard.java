package cards.spell;

import cards.Card;
import cards.CardType;

public class SpellCard extends Card{
	private SpellType type;

	public SpellCard(String name, String type, String text) {
		super(name, text, CardType.SPELL);
		this.type = createType(type);
	}
	
	public SpellCard(String name, SpellType type, String text) {
		super(name, text, CardType.SPELL);
		this.type = type;
	}

	private SpellType createType(String type) {
		switch (type) {
		case "Normal":
			return SpellType.NORMAL;
		case "Ritual":
			return SpellType.RITUAL;
		case "Ausrüstung":
			return SpellType.AUSRÜSTUNG;
		case "Permanent":
			return SpellType.PERMANENT;
		case "Feld": 
			return SpellType.FELD;
		case "Schnell":
			return SpellType.SCHNELL;
		}
		return null;
	}

	public SpellType getSpellType() {
		return this.type;
	}

	@Override
	public String toString() {
		return getName() + " (type: " + type + ", text: " + getText() + ")";
	}
	
	
	
	

}
