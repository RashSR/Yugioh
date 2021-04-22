package game.map;

import java.util.ArrayList;

import cards.Card;
import cards.monster.MonsterCard;
import cards.trap.TrapCard;

public class FieldPrinter {
	private PlayField playField;

	public FieldPrinter(PlayField playField) {
		this.playField = playField;
	}
	//TODO wenn weniger als 10 Karten im Deck bzw mehr als 9 im Friedhof verschiebt sich das Feld
	public void printField() {
		ArrayList<String > s = new ArrayList<>();
		PlayField oppnentField = playField.getGame().getNotActivePlayer().getPlayField();
		String lp = "LP: " + string2rightSize(5, ""+oppnentField.getPlayer().getLifePoints(), false);
		String fieldName = "Name: -------";
		if(oppnentField.hasFieldSpell()) {
			fieldName = "Name: " + string2rightSize(7, oppnentField.getFieldSpell().getName(), false);
		}
		String[] atk = getAtt(oppnentField);
		String[] def = getDef(oppnentField);
		String[] mm = getMonsterMode(oppnentField);
		String[] cm = getCardMode(oppnentField.getMonsterField());
		String[] c = getCardMode(oppnentField.getSpellAndTrapField());
		String[] t = getType(oppnentField);
		
		s.add("-----------------        _________________________________________________________________________________        -----------------");
		s.add("|               |        |               |               |               |               |               |        |               |");
		s.add("|               |        |               |               |               |               |               |        |               |");
		s.add("|               |        |  " + t[0] + "  |  " + t[1] + "  |  " + t[2] + "  |  " + t[3] + "  |  " + t[4] + "  |        |   Fusions-    |");
		s.add("|     Deck:     |        |  " + c[0] + " |  " + c[1] + " |  " + c[2] + " |  " + c[3] + " |  " + c[4] + " |        |   monster:    |");
		s.add("|      " + oppnentField.getDeckCount() + "       |        |               |               |               |               |               |        |      " + oppnentField.getFusionMonsters().size() + "        |");
		s.add("|               |        |               |               |               |               |               |        |               |");
		s.add("|               |        |               |               |               |               |               |        |               |");
		s.add("|               |        |               |               |               |               |               |        |               |");
		s.add("|_______________|        |-------------------------------------------------------------------------------|        |_______________|");
		s.add("-----------------        |_______________________________________________________________________________|        |_______________|");
		s.add("|               |        |               |               |               |               |               |        |   "+lp+"   |");
		s.add("|               |        |   " + atk[0] + "   |   " + atk[1] + "   |   " + atk[2] + "   |   " + atk[3] + "   |   " + atk[4] + "   |        |       ,       |");
		s.add("|   Friedhof:   |        |   " + def[0] + "   |   " + def[1] + "   |   " + def[2] + "   |   " + def[3] + "   |   " + def[4] + "   |        |    __/ \\__    |");
		s.add("|       " + oppnentField.getGraveyard().size() + "       |        |               |               |               |               |               |        |    \\     /    |");
		s.add("|               |        |   " + mm[0] + "    |   " + mm[1] + "    |   " + mm[2] + "    |   " + mm[3] + "    |   " + mm[4] + "    |        |    /_   _\\    |");
		s.add("|               |        |  " + cm[0] + " |  " + cm[1] + " |  " + cm[2] + " |  " + cm[3] + " |  " + cm[4] + " |        |      \\ /      |");
		s.add("|               |        |               |               |               |               |               |        |       '       |");
		s.add("|               |        |               |               |               |               |               |        | " + fieldName + " |");
		s.add("|_______________|        ---------------------------------------------------------------------------------        |_______________|");
		s.add("\n************************************************************************************************************************************");
		fieldName = "Name: -------";
		if(playField.hasFieldSpell()) {
			fieldName = "Name: " + string2rightSize(7, playField.getFieldSpell().getName(), false);
		}
		lp = "LP: " + string2rightSize(5, ""+playField.getPlayer().getLifePoints(), false);
		atk = getAtt(playField);
		def = getDef(playField);
		mm = getMonsterMode(playField);
		cm = getCardMode(playField.getMonsterField());
		c = getCardMode(playField.getSpellAndTrapField());
		t = getType(playField);
		s.add("************************************************************************************************************************************");
		s.add("_________________        _________________________________________________________________________________        _________________");
		s.add("|   "+lp+"   |        |               |               |               |               |               |        |               |");
		s.add("|       ,       |        |   " + atk[0] + "   |   " + atk[1] + "   |   " + atk[2] + "   |   " + atk[3] + "   |   " + atk[4] + "   |        |               |");
		s.add("|    __/ \\__    |        |   " + def[0] + "   |   " + def[1] + "   |   " + def[2] + "   |   " + def[3] + "   |   " + def[4] + "   |        |   Friedhof:   |");
		s.add("|    \\     /    |        |               |               |               |               |               |        |       " + playField.getGraveyard().size() + "       |");
		s.add("|    /_   _\\    |        |   " + mm[0] + "    |   " + mm[1] + "    |   " + mm[2] + "    |   " + mm[3] + "    |   " + mm[4] + "    |        |               |");
		s.add("|      \\ /      |        |  " + cm[0] + " |  " + cm[1] + " |  " + cm[2] + " |  " + cm[3] + " |  " + cm[4] + " |        |               |");
		s.add("|       '       |        |               |               |               |               |               |        |               |");
		s.add("| " + fieldName + " |        |               |               |               |               |               |        |               |");
		s.add("-----------------        |-------------------------------------------------------------------------------|        -----------------");
		s.add("_________________        |_______________________________________________________________________________|        |_______________|");
		s.add("|               |        |               |               |               |               |               |        |               |");
		s.add("|               |        |               |               |               |               |               |        |               |");
		s.add("|   Fusions-    |        |  " + t[0] + "  |  " + t[1] + "  |  " + t[2] + "  |  " + t[3] + "  |  " + t[4] + "  |        |               |");
		s.add("|   monster:    |        |  " + c[0] + " |  " + c[1] + " |  " + c[2] + " |  " + c[3] + " |  " + c[4] + " |        |               |");
		s.add("|      " + playField.getFusionMonsters().size() + "        |        |               |               |               |               |               |        |     Deck:     |");
		s.add("|               |        |               |               |               |               |               |        |      " + playField.getDeckCount() + "       |");
		s.add("|               |        |               |               |               |               |               |        |               |");
		s.add("|               |        |               |               |               |               |               |        |               |");
		s.add("-----------------        ---------------------------------------------------------------------------------        -----------------");	
		s.forEach(System.out::println);
	}
	
	private String[] getType(PlayField pf) {
		String[] s = new String[5];
		for(int i = 0; i < 5; i++) {
			if(pf.getSpellAndTrapField()[i].isEmpty()) {
				s[i] = "Type: -----";
			}else {
				Card card = pf.getSpellOrTrapCardAt(i);
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

	private String[] getMonsterMode(PlayField pf) {
		String[] s = new String[5];
		for(int i = 0; i < 5; i++) {
			if(pf.getMonsterField()[i].isEmpty()) {
				s[i] = "Pos: ---";
			}else {
				MonsterMode mm = pf.getMonsterField()[i].getMonsterMode();
				s[i] = "Pos: " + mm;
			}
		}
		return s;
	}

	private String[] getAtt(PlayField pf) {
		String[] s = new String[5];
		for(int i = 0; i < 5; i++) {
			if(pf.getMonsterField()[i].isEmpty()) {
				s[i] = "ATK: ----";
			}else {
				MonsterCard mc = (MonsterCard) pf.getMonsterField()[i].getCard();
				s[i] = "ATK: " + string2rightSize(4, ""+mc.getAtk(), true);
			}
		}
		return s;
	}

	private String[] getDef(PlayField pf) {
		String[] s = new String[5];
		for(int i = 0; i < 5; i++) {
			if(pf.getMonsterField()[i].isEmpty()) {
				s[i] = "DEF: ----";
			}else {
				MonsterCard mc = (MonsterCard) pf.getMonsterField()[i].getCard();
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
