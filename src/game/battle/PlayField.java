package game.battle;

import java.util.ArrayList;
import java.util.Scanner;

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
			if(player.getSummonCount() > 0) {
				index = getFreeIndex(monsterField);
				if(index > -1) {
					if(canBeSummoned(card)) {
						playHelper(monsterField, card, cm, mm, index, handIndex);
						player.setSummonCount(player.getSummonCount() - 1);
					}
				}
			}else {
				System.out.println("You only can summon one Monster each round.");
			}
		}else {
			if(card instanceof SpellCard) {
				SpellCard sc = (SpellCard) card;
				if(sc.getSpellType() == SpellType.FELD) {
					playHelper(null, card, CardMode.FACE_UP, mm, -1, handIndex);
					return;
				}
			}
			index = getFreeIndex(spellAndTrapField);
			if(index > -1) {
				playHelper(spellAndTrapField, card, cm, mm, index, handIndex);
			}
		}
	}
	
	private boolean canBeSummoned(Card card) {
		MonsterCard monster = (MonsterCard) card;
		int stars = monster.getStars();
		if(stars == 5 || stars == 6) {
			return tributeMonster(1);
		}else if(stars == 7 || stars == 8) {
			return tributeMonster(2);
		}else if(stars == 9 || stars == 10) {
			return tributeMonster(3);
		}
		return true;
	}
	
	private boolean tributeMonster(int tributes) {
		System.out.println("To summon the monster you need " + tributes + " Tributes.");
		if(tributes == 1 && getOccupiedMonsterSlotCount() > 0) {
			makeTribute(tributes);
			return true;
		}else if(tributes == 2 && getOccupiedMonsterSlotCount() > 1) {
			makeTribute(tributes);
			return true;
		}else if(tributes == 3 && getOccupiedMonsterSlotCount() > 2) {
			makeTribute(tributes);
			return true;
		}
		System.out.println("You don't have enough Monster on the Field.");
		return false;
	}
	
	private void makeTribute(int tributes) {
		Scanner sc = new Scanner(System.in);
		for(int i = 0; i < tributes; i++) {
			System.out.println("Choose Tribute " + (i+1) + ":");
			int selection = sc.nextInt();
			if(monsterField[selection].isEmpty()) {
				System.out.println("Choose again!");
				i--;
			}else {
				sendCardFromFieldToGrave(monsterField, selection);
			}
		}
	}
	
	private void playHelper(FieldElement[] arr, Card card, CardMode cm, MonsterMode mm, int index, int handIndex) {
		if(arr == null) {
			game.playFieldSpell((SpellCard)card, player);
		}else {
			arr[index].setCard(card); 
			arr[index].setOwner(player);
			arr[index].setMonsterMode(mm);
			arr[index].setCardMode(cm);
			arr[index].setEmpty(false);
		}
		System.out.println("You played " + card.getName() + ".");
		player.dropHandCard(handIndex);
	}

	public void sendCardFromHandToGrave(int index) {
		graveyard.add(player.getHandCardAt(index));
		System.out.println(player.getName() + " dropped " + player.getHandCardAt(index).getName() + ".");
		player.dropHandCard(index);
	}
	
	private void sendCardFromFieldToGrave(FieldElement[] arr, int index) {
		graveyard.add(arr[index].getCard());
		System.out.println(player.getName() + " send " + arr[index].getCard().getName() + " the to graveyard.");
		arr[index] = new FieldElement(player);
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

	private int getOccupiedMonsterSlotCount() {
		int count = 0;
		for(int i = 0; i < 5; i++) {
			if(!monsterField[i].isEmpty()) {
				count++;
			}
		}
		return count;
	}

	public void print() {
		String s = "";
		String inUse = "   ";
		if(hasFieldSpell()) {
			inUse = "USE";
		}
		s += ("\n_________________        _________________________________________________________________________________        _________________");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n|       ,       |        |               |               |               |               |               |        |               |");
		s += ("\n|    __/ \\__    |        |               |               |               |               |               |        |   Friedhof:   |");
		s += ("\n|    \\ " + inUse +" /    |        |               |               |               |               |               |        |       " + graveyard.size() + "       |");
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
