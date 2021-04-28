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

	public void printField() {
		ArrayList<String > s = new ArrayList<>();
		PlayField opponentField = playField.getGame().getNotActivePlayer().getPlayField();
		String lp = "LP: " + string2rightSize(5, ""+opponentField.getPlayer().getLifePoints(), false);
		String fieldName = "Name: -------";
		if(opponentField.hasFieldSpell()) {
			fieldName = "Name: " + string2rightSize(7, opponentField.getFieldSpell().getName(), false);
		}
		String deckCount = string2rightSize(2, ""+opponentField.getDeckCount(), true);
		String graveYardCount = string2rightSize(2, ""+opponentField.getGraveyard().size(), true);
		String fusionMonsterCount = string2rightSize(2, ""+opponentField.getFusionMonsters().size(), true);
		String[] atk = getAtt(opponentField);
		String[] def = getDef(opponentField);
		String[] mm = getMonsterMode(opponentField);
		String[] cm = getCardMode(opponentField.getMonsterField());
		String[] c = getCardMode(opponentField.getSpellAndTrapField());
		String[] t = getType(opponentField);
		String[] ac = getAttackChange(opponentField);
		String[] dc = getDefChange(opponentField);
		s.add("-----------------        _________________________________________________________________________________        -----------------");
		s.add("|               |        |               |               |               |               |               |        |               |");
		s.add("|               |        |               |               |               |               |               |        |               |");
		s.add("|               |        |  " + t[0] + "  |  " + t[1] + "  |  " + t[2] + "  |  " + t[3] + "  |  " + t[4] + "  |        |   Fusions-    |");
		s.add("|     Deck:     |        |  " + c[0] + " |  " + c[1] + " |  " + c[2] + " |  " + c[3] + " |  " + c[4] + " |        |   monster:    |");
		s.add("|      " + deckCount + "       |        |               |               |               |               |               |        |      " + fusionMonsterCount + "       |");
		s.add("|               |        |               |               |               |               |               |        |               |");
		s.add("|               |        |               |               |               |               |               |        |               |");
		s.add("|               |        |               |               |               |               |               |        |               |");
		s.add("|_______________|        |-------------------------------------------------------------------------------|        |_______________|");
		s.add("-----------------        |_______________________________________________________________________________|        |_______________|");
		s.add("|               |        |               |               |               |               |               |        |   "+lp+"   |");
		s.add("|               |        |   " + atk[0] + "   |   " + atk[1] + "   |   " + atk[2] + "   |   " + atk[3] + "   |   " + atk[4] + "   |        |       ,       |");
		s.add("|   Friedhof:   |        |   " + def[0] + "   |   " + def[1] + "   |   " + def[2] + "   |   " + def[3] + "   |   " + def[4] + "   |        |    __/ \\__    |");
		s.add("|      " + graveYardCount + "       |        |               |               |               |               |               |        |    \\     /    |");
		s.add("|               |        |   " + mm[0] + "    |   " + mm[1] + "    |   " + mm[2] + "    |   " + mm[3] + "    |   " + mm[4] + "    |        |    /_   _\\    |");
		s.add("|               |        |  " + cm[0] + " |  " + cm[1] + " |  " + cm[2] + " |  " + cm[3] + " |  " + cm[4] + " |        |      \\ /      |");
		s.add("|               |        | " + ac[0] + "  | " + ac[1] + "  | " + ac[2] + "  | " + ac[3] +"  | " + ac[4] + "  |        |       '       |");
		s.add("|               |        | " + dc[0] + "  | " + dc[1] + "  | " + dc[2] + "  | " + dc[3] +"  | " + dc[4] + "  |        | " + fieldName + " |");
		s.add("|_______________|        ---------------------------------------------------------------------------------        |_______________|");
		s.add("\n************************************************************************************************************************************");
		fieldName = "Name: -------";
		if(playField.hasFieldSpell()) {
			fieldName = "Name: " + string2rightSize(7, playField.getFieldSpell().getName(), false);
		}
		graveYardCount = string2rightSize(2, ""+playField.getGraveyard().size(), true);
		deckCount = string2rightSize(2, ""+playField.getDeckCount(), true);
		lp = "LP: " + string2rightSize(5, ""+playField.getPlayer().getLifePoints(), false);
		fusionMonsterCount = string2rightSize(2, ""+playField.getFusionMonsters().size(), true);
		atk = getAtt(playField);
		def = getDef(playField);
		mm = getMonsterMode(playField);
		cm = getCardMode(playField.getMonsterField());
		c = getCardMode(playField.getSpellAndTrapField());
		t = getType(playField);
		ac = getAttackChange(playField);
		dc = getDefChange(playField);
		s.add("************************************************************************************************************************************");
		s.add("_________________        _________________________________________________________________________________        _________________");
		s.add("|   "+lp+"   |        |               |               |               |               |               |        |               |");
		s.add("|       ,       |        |   " + atk[0] + "   |   " + atk[1] + "   |   " + atk[2] + "   |   " + atk[3] + "   |   " + atk[4] + "   |        |               |");
		s.add("|    __/ \\__    |        |   " + def[0] + "   |   " + def[1] + "   |   " + def[2] + "   |   " + def[3] + "   |   " + def[4] + "   |        |   Friedhof:   |");
		s.add("|    \\     /    |        |               |               |               |               |               |        |      " + graveYardCount + "       |");
		s.add("|    /_   _\\    |        |   " + mm[0] + "    |   " + mm[1] + "    |   " + mm[2] + "    |   " + mm[3] + "    |   " + mm[4] + "    |        |               |");
		s.add("|      \\ /      |        |  " + cm[0] + " |  " + cm[1] + " |  " + cm[2] + " |  " + cm[3] + " |  " + cm[4] + " |        |               |");
		s.add("|       '       |        | " + ac[0] + "  | " + ac[1] + "  | " + ac[2] + "  | " + ac[3] +"  | " + ac[4] + "  |        |               |");
		s.add("| " + fieldName + " |        | " + dc[0] + "  | " + dc[1] + "  | " + dc[2] + "  | " + dc[3] +"  | " + dc[4] + "  |        |               |");
		s.add("-----------------        |-------------------------------------------------------------------------------|        -----------------");
		s.add("_________________        |_______________________________________________________________________________|        |_______________|");
		s.add("|               |        |               |               |               |               |               |        |               |");
		s.add("|               |        |               |               |               |               |               |        |               |");
		s.add("|   Fusions-    |        |  " + t[0] + "  |  " + t[1] + "  |  " + t[2] + "  |  " + t[3] + "  |  " + t[4] + "  |        |               |");
		s.add("|   monster:    |        |  " + c[0] + " |  " + c[1] + " |  " + c[2] + " |  " + c[3] + " |  " + c[4] + " |        |               |");
		s.add("|      " + playField.getFusionMonsters().size() + "        |        |               |               |               |               |               |        |     Deck:     |");
		s.add("|               |        |               |               |               |               |               |        |      " + deckCount + "       |");
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

	private String[] getAttackChange(PlayField pf) {
		String s[] = new String[5];
		for(int i = 0; i < 5; i++) {
			if(pf.getMonsterField()[i].isEmpty()) {
				s[i] = "ATK-C: -----";
			}else {
				int atkChange = pf.getMonsterField()[i].getAtkChange();
				s[i] = "ATK-C: -----";
				if(atkChange != 0) {
					s[i] = "ATK-C: " + string2rightSize(5, ""+atkChange, true);
				}
			}
		}
		return s;
	}

	private String[] getDefChange(PlayField pf) {
		String s[] = new String[5];
		for(int i = 0; i < 5; i++) {
			if(pf.getMonsterField()[i].isEmpty()) {
				s[i] = "DEF-C: -----";
			}else {
				int defChange = pf.getMonsterField()[i].getDefChange();
				s[i] = "DEF-C: -----";
				if(defChange != 0) {
					s[i] = "DEF-C: " + string2rightSize(5, ""+defChange, true);
				}
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
