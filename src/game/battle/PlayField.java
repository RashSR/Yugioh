package game.battle;

import java.util.ArrayList;

import cards.Card;
import cards.Deck;
import cards.monster.MonsterCard;
import cards.monster.fusion.FusionMonster;
import cards.spell.SpellCard;
import cards.spell.SpellType;
import game.Player;

public class PlayField {
	private Player player;
	private Game game;
	private SpellCard fieldSpell;
	private Deck deck;
	private ArrayList<Card> graveyard = new ArrayList<>();
	private FieldElement[] monsterField = new FieldElement[5];
	private FieldElement[] spellAndTrapField = new FieldElement[5];
	private ArrayList<FusionMonster> fusionMonsters;

	public PlayField(Player player) {
		this.player = player;
		this.deck = player.getDeck();
		this.fusionMonsters = deck.getFusionMonster();
		this.deck.shuffleDeck();
		for(int i = 0; i < 5; i++) {
			monsterField[i] = new FieldElement();
			spellAndTrapField[i] = new FieldElement();
		}
	}
	
	public void playCard(int handIndex, CardMode cm, MonsterMode mm) {
		Card card = player.getHandCardAt(handIndex);
		int index;
		if(card instanceof MonsterCard) {
			index = getFreeIndex(monsterField);
			if(index > -1) {
				monsterField[index].setCard(card); 
				monsterField[index].setOwner(player);
				monsterField[index].setMonsterMode(mm);
				monsterField[index].setCardMode(cm);
				monsterField[index].setEmpty(false);
				System.out.println("You played " + card.getName() + ".");
				player.dropHandCard(handIndex);
			}
		}else {
			if(card instanceof SpellCard) {
				SpellCard sc = (SpellCard) card;
				if(sc.getSpellType() == SpellType.FELD) {
					game.playFieldSpell(sc, player);
					System.out.println("You played " + card.getName() + ".");
					player.dropHandCard(handIndex);
					return;
				}
			}
			index = getFreeIndex(spellAndTrapField);
			if(index > -1) {
				spellAndTrapField[index].setCard(card);
				spellAndTrapField[index].setCardMode(cm);
				spellAndTrapField[index].setEmpty(false);
				System.out.println("You played " + card.getName() + ".");
				player.dropHandCard(handIndex);
			}
		}
	}

	public FusionMonster getFusionMonsterByName(String name) {
		for(FusionMonster fm : fusionMonsters) {
			if(name.equals(fm.getName())) {
				fusionMonsters.remove(fm);
				return fm;
			}
		}
		return null;
	}

	private int getFreeIndex(FieldElement[] arr) {
		for(int i = 0; i < 5; i++) {
			if(arr[i].isEmpty()) {
				return i;
			}
		}
		System.out.println("Wir sind bei -1");
		return -1;
	}

	public void changeState(int row, int column) {
		if(isValidFieldPos(row, column) && !isEmpty(row, column)) {
			if(row == 0) {
				monsterField[column].changeMonsterMode();
				if(monsterField[column].getCardMode() == CardMode.FACE_DOWN) {
					monsterField[column].changeCardMode();
				}
			}else if(row == 1) {
				spellAndTrapField[column].changeCardMode();
			}
		}
	}

	public Card getCardAt(int row, int column) {
		if(isValidFieldPos(row, column) && !isEmpty(row, column)) {
			if(row == 0) {
				return getMonsterAt(column);
			}else if(row == 1) {
				return getSpellOrTrapCardAt(column);
			}
		}
		return null;
	}

	public FieldElement getFieldElement(int row, int column) {
		if(isValidFieldPos(row, column)) {
			if(row == 0) {
				return monsterField[column];
			}else if(row == 1) {
				return spellAndTrapField[column];
			}
		}
		return null;
	}
	
	private boolean isEmpty(int row, int column) {
		if(isValidFieldPos(row, column)) {
			if(row == 0 && monsterField[column].isEmpty()) {
				System.out.println("[Playfield] There is no Card at " + row + column);
				return true;
			}else if(row == 1 && spellAndTrapField[column].isEmpty()) {
				System.out.println("[Playfield] There is no Card at " + row + column);
				return true;
			}
		}
		return false;
	}
	
	private boolean isValidFieldPos(int row, int column) {
		if(column > 4 || column < 0) {
			System.out.println("[Playfield] column must be between 0 and 4.");
			return false;
		}else if(row < 0 || row > 1) {
			System.out.println("[Playfield] row must be 0 or 1.");
			return false;
		}
		return true;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public Card getSpellOrTrapCardAt(int index) {
		return spellAndTrapField[index].getCard();
	}

	public MonsterCard getMonsterAt(int index) {
		return (MonsterCard)monsterField[index].getCard();
	}

	public int getDeckCount() {
		return deck.getDeckCount();
	}

	public ArrayList<Card> getGraveyard(){
		return this.graveyard;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setFieldSpell(SpellCard fieldSpell){
		this.fieldSpell = fieldSpell;
	}

	public void removeFieldSpell() {
		this.fieldSpell = null;
	}

	public boolean hasFieldSpell() {
		if(fieldSpell == null) {
			return false;
		}
		return true;
	}
	
	public MonsterMode getMonsterModeAt() {
		return null;
	}
	
	public void print() {
		String s = "";
		s += ("\n_________________        _________________________________________________________________________________        _________________");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n|       ,       |        |               |               |               |               |               |        |               |");
		s += ("\n|    __/ \\__    |        |               |               |               |               |               |        |   Friedhof:   |");
		s += ("\n|    \\     /    |        |               |               |               |               |               |        |       " + graveyard.size() + "       |");
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
		s += ("\n|      " + fusionMonsters.size() + "        |        |               |               |               |               |               |        |     Deck:     |");
		s += ("\n|               |        |               |               |               |               |               |        |      " + deck.getDeckCount() + "       |");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n-----------------        ---------------------------------------------------------------------------------        -----------------");
		System.out.println(s);

	}
}
