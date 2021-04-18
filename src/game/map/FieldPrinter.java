package game.map;

import cards.monster.MonsterCard;
import game.battle.PlayField;

public class FieldPrinter {
	private PlayField playField;
	
	public FieldPrinter(PlayField playField) {
		this.playField = playField;
	}
	
	public void printField() {
		String s = "";
		String inUse = "   ";
		if(playField.hasFieldSpell()) {
			inUse = "USE";
		}
		String[] atk = getAtt();
		String[] def = getDef();
		s += ("\n_________________        _________________________________________________________________________________        _________________");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n|       ,       |        |   " + atk[0] + "   |   " + atk[1] + "   |   " + atk[2] + "   |   " + atk[3] + "   |   " + atk[4] + "   |        |               |");
		s += ("\n|    __/ \\__    |        |   " + def[0] + "   |   " + def[1] + "   |   " + def[2] + "   |   " + def[3] + "   |   " + def[4] + "   |        |   Friedhof:   |");
		s += ("\n|    \\ " + inUse +" /    |        |               |               |               |               |               |        |       " + playField.getGraveyard().size() + "       |");
		s += ("\n|    /_   _\\    |        |               |               |               |               |               |        |               |");
		s += ("\n|      \\ /      |        |               |               |               |               |               |        |               |");
		s += ("\n|       '       |        |               |               |               |               |               |        |               |");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n-----------------        |-------------------------------------------------------------------------------|        -----------------");
		s += ("\n_________________        |_______________________________________________________________________________|        |_______________|");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n|   Fusions-    |        |               |               |               |               |               |        |               |");
		s += ("\n|   monster:    |        |               |               |               |               |               |        |               |");
		s += ("\n|      " + playField.getFusionMonsters().size() + "        |        |               |               |               |               |               |        |     Deck:     |");
		s += ("\n|               |        |               |               |               |               |               |        |      " + playField.getDeckCount() + "       |");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n-----------------        ---------------------------------------------------------------------------------        -----------------");
		System.out.println(s);

	}

		private String[] getAtt() {
			String[] s = new String[5];
			for(int i = 0; i < 5; i++) {
				if(playField.getMonsterField()[i].isEmpty()) {
					s[i] = "ATK: ----";
				}else {
					MonsterCard mc = (MonsterCard) playField.getMonsterField()[i].getCard();
					s[i] = "ATK: " + string2rightSize(4, ""+mc.getAtk());
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
					s[i] = "DEF: " + string2rightSize(4, ""+mc.getDef());
				}
			}
			return s;
		}

		private String string2rightSize(int size, String s) {
			String ofset = "";
			for(int i = 0; i < size - s.length(); i++) {
				ofset += " ";
			}
			return ofset + s;
		}
}
