package game.map;

import cards.Card;
import cards.monster.MonsterCard;
import cards.trap.TrapCard;

public class FieldPrinter {
	private PlayField playField;

	public FieldPrinter(PlayField playField) {
		this.playField = playField;
	}

	public void printField() {
		String s = "";
		String lp = "LP: " + string2rightSize(5, ""+playField.getPlayer().getLifePoints(), false);
		String fieldName = "Name: -------";
		if(playField.hasFieldSpell()) {
			fieldName = "Name: " + string2rightSize(7, playField.getFieldSpell().getName(), false);
		}
		String[] atk = getAtt();
		String[] def = getDef();
		String[] mm = getMonsterMode();
		String[] cm = getCardMode(playField.getMonsterField());
		String[] c = getCardMode(playField.getSpellAndTrapField());
		String[] t = getType();
		
		s += ("\n_________________        _________________________________________________________________________________        _________________");
		s += ("\n|   "+lp+"   |        |               |               |               |               |               |        |               |");
		s += ("\n|       ,       |        |   " + atk[0] + "   |   " + atk[1] + "   |   " + atk[2] + "   |   " + atk[3] + "   |   " + atk[4] + "   |        |               |");
		s += ("\n|    __/ \\__    |        |   " + def[0] + "   |   " + def[1] + "   |   " + def[2] + "   |   " + def[3] + "   |   " + def[4] + "   |        |   Friedhof:   |");
		s += ("\n|    \\     /    |        |               |               |               |               |               |        |       " + playField.getGraveyard().size() + "       |");
		s += ("\n|    /_   _\\    |        |   " + mm[0] + "    |   " + mm[1] + "    |   " + mm[2] + "    |   " + mm[3] + "    |   " + mm[4] + "    |        |               |");
		s += ("\n|      \\ /      |        |  " + cm[0] + " |  " + cm[1] + " |  " + cm[2] + " |  " + cm[3] + " |  " + cm[4] + " |        |               |");
		s += ("\n|       '       |        |               |               |               |               |               |        |               |");
		s += ("\n| " + fieldName + " |        |               |               |               |               |               |        |               |");
		s += ("\n-----------------        |-------------------------------------------------------------------------------|        -----------------");
		s += ("\n_________________        |_______________________________________________________________________________|        |_______________|");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n|   Fusions-    |        |  " + t[0] + "  |  " + t[1] + "  |  " + t[2] + "  |  " + t[3] + "  |  " + t[4] + "  |        |               |");
		s += ("\n|   monster:    |        |  " + c[0] + " |  " + c[1] + " |  " + c[2] + " |  " + c[3] + " |  " + c[4] + " |        |               |");
		s += ("\n|      " + playField.getFusionMonsters().size() + "        |        |               |               |               |               |               |        |     Deck:     |");
		s += ("\n|               |        |               |               |               |               |               |        |      " + playField.getDeckCount() + "       |");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n-----------------        ---------------------------------------------------------------------------------        -----------------");
		System.out.println(s);
	}
	
	private String[] getType() {
		String[] s = new String[5];
		for(int i = 0; i < 5; i++) {
			if(playField.getSpellAndTrapField()[i].isEmpty()) {
				s[i] = "Type: -----";
			}else {
				Card card = playField.getSpellOrTrapCardAt(i);
				if(card instanceof TrapCard) {
					s[i] = "Type: TRAP ";
				}else {
					s[i] = "Type: SPELL";
				}
			}
		}
		return s;
	}
	
	private String[] getCardMode(FieldElement[] arr) {
		String[] s = new String[5];
		for(int i = 0; i < 5; i++) {
			if(arr[i].isEmpty()) {
				s[i] = "Mode: ------";
			}else {
				CardMode cm = arr[i].getCardMode();
				s[i] = "Mode: " + string2rightSize(6, ""+cm, false);
			}
		}
		return s;
	}

	private String[] getMonsterMode() {
		String[] s = new String[5];
		for(int i = 0; i < 5; i++) {
			if(playField.getMonsterField()[i].isEmpty()) {
				s[i] = "Pos: ---";
			}else {
				MonsterMode mm = playField.getMonsterField()[i].getMonsterMode();
				s[i] = "Pos: " + mm;
			}
		}
		return s;
	}

	private String[] getAtt() {
		String[] s = new String[5];
		for(int i = 0; i < 5; i++) {
			if(playField.getMonsterField()[i].isEmpty()) {
				s[i] = "ATK: ----";
			}else {
				MonsterCard mc = (MonsterCard) playField.getMonsterField()[i].getCard();
				s[i] = "ATK: " + string2rightSize(4, ""+mc.getAtk(), true);
			}
		}
		return s;
	}

	private String[] getDef() {
		String[] s = new String[5];
		for(int i = 0; i < 5; i++) {
			if(playField.getMonsterField()[i].isEmpty()) {
				s[i] = "DEF: ----";
			}else {
				MonsterCard mc = (MonsterCard) playField.getMonsterField()[i].getCard();
				s[i] = "DEF: " + string2rightSize(4, ""+mc.getDef(), true);
			}
		}
		return s;
	}

	private String string2rightSize(int size, String s, boolean prefix) {
		String ofset = "";
		for(int i = 0; i < size - s.length(); i++) {
			ofset += " ";
		}
		if(prefix) {
			return ofset + s;
		}
		return s+ofset;
	}
}
