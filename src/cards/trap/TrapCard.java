package cards.trap;

import cards.Card;
import cards.CardType;

public class TrapCard extends Card{
	private TrapType type;
	
	public TrapCard(String name, String type, String text) {
		super(name, text, CardType.TRAP);
		this.type = createType(type);
	}
	
	public TrapCard(String name, TrapType type, String text) {
		super(name, text, CardType.TRAP);
		this.type = type;
	}
	
	private TrapType createType(String type) {
		switch (type) {
		case "Normal":
			return TrapType.NORMAL;
		case "Counter": 
			return TrapType.KONTER;
		case "Permanent":
			return TrapType.PERMANENT;
		}
		return null;
	}

	public TrapType getTrapType() {
		return type;
	}
	
	@Override
	public String toString() {
		return getName() + " (type: " + type + ", text: " + getText() + ")";
	}

}
