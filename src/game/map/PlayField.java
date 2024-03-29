package game.map;

import java.util.ArrayList;
import java.util.Scanner;

import cards.Card;
import cards.Deck;
import cards.monster.Attribute;
import cards.monster.MonsterCard;
import cards.monster.fusion.FusionMonster;
import cards.spell.SpellCard;
import cards.spell.SpellType;
import cards.trap.TrapCard;
import game.Player;
import game.battle.Game;
import game.effects.SpellEffects;

@SuppressWarnings("resource")
public class PlayField {
	private Player player;
	private Game game;
	private SpellCard fieldSpell; //TODO: evtl auch als FieldElement
	private Deck deck;
	private FieldPrinter fieldPrinter;
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
		this.fieldPrinter = new FieldPrinter(this);
	}

	public void attack() {
		System.out.println("Which Monster should attack?");
		int myMonsterIndex;
		Scanner sc = new Scanner(System.in);
		if(getAtkMonsterCount() == 1) {
			System.out.println("You only have one Monster in ATK Position.");
			myMonsterIndex = getOnlyAtkMonsterIndex();			
		}else if(getAtkMonsterCount() == 0){
			System.out.println("All your Monster already attacked.");
			return;
		}else {
			for(int i = 0; i < 5; i++) {
				if(!monsterField[i].isEmpty()) {
					if(monsterField[i].getMonsterMode() == MonsterMode.ATTACK && monsterField[i].getAtkCount() > 0) {
						System.out.println(i+": " + monsterField[i].getCard().getName());
					}	
				}
			}
			myMonsterIndex = sc.nextInt();
		}
		if(!getGame().getNotActivePlayer().getPlayField().containsMonster()) {
			directAttack(myMonsterIndex);	
		}else {
			System.out.println("Which Monster do you want to attack?");
			for(int i = 0; i < 5; i++) {
				String name = "Card is facedown.";
				if(!getGame().getNotActivePlayer().getPlayField().getMonsterField()[i].isEmpty()) {
					if(getGame().getNotActivePlayer().getPlayField().getMonsterField()[i].getCardMode() == CardMode.FACE_UP) {
						name = getGame().getNotActivePlayer().getPlayField().getMonsterField()[i].getCard().getName();
					}
					System.out.println(i + ": " + name);
				}
			}
			int opMonsterIndex;
			if(getGame().getNotActivePlayer().getPlayField().getMonsterCount() == 1) {
				System.out.println("Your opponent has only one Monster.");
				opMonsterIndex = getGame().getNotActivePlayer().getPlayField().getOnlyMonsterIndex();
			}else {
				opMonsterIndex = sc.nextInt();
			}
			FieldElement opFieldElement = getGame().getNotActivePlayer().getPlayField().getMonsterField()[opMonsterIndex];
			int myAtkPower = monsterField[myMonsterIndex].getAtk();
			if(opFieldElement.getMonsterMode() == MonsterMode.DEFENSE) {
				if(opFieldElement.getCardMode() == CardMode.FACE_DOWN) {
					getGame().getNotActivePlayer().getPlayField().getMonsterField()[opMonsterIndex].changeCardMode(getGame().getNotActivePlayer().getPlayField());
				}
				int defPower = opFieldElement.getDef();
				if(myAtkPower > defPower) {
					getGame().getNotActivePlayer().getPlayField().sendCardFromFieldToGrave(getGame().getNotActivePlayer().getPlayField().getMonsterField(), opMonsterIndex);
				}else if(myAtkPower < defPower) {
					player.setLifePoints(player.getLifePoints() - (defPower - myAtkPower) );
					System.out.println(player.getName() + " lose " + (defPower - myAtkPower) + " lifepoints and has now " + player.getLifePoints() + " LP.");
				}
			}else {
				int opAtkPower = getGame().getNotActivePlayer().getPlayField().getMonsterField()[opMonsterIndex].getAtk();
				if(myAtkPower > opAtkPower) {
					getGame().getNotActivePlayer().getPlayField().sendCardFromFieldToGrave(getGame().getNotActivePlayer().getPlayField().getMonsterField(), opMonsterIndex);
					getGame().getNotActivePlayer().setLifePoints(getGame().getNotActivePlayer().getLifePoints() - (myAtkPower - opAtkPower) );
					System.out.println(getGame().getNotActivePlayer().getName() + " lose " + (myAtkPower - opAtkPower) + " lifepoints and has now " + getGame().getNotActivePlayer().getLifePoints() + " LP.");
				}else if(myAtkPower < opAtkPower) {
					sendCardFromFieldToGrave(monsterField, myMonsterIndex);
					player.setLifePoints(player.getLifePoints() - (opAtkPower - myAtkPower) );
					System.out.println(player.getName() + " lose " + (opAtkPower - myAtkPower) + " lifepoints and has now " + player.getLifePoints() + " LP.");
				}else {
					getGame().getNotActivePlayer().getPlayField().sendCardFromFieldToGrave(getGame().getNotActivePlayer().getPlayField().getMonsterField(), opMonsterIndex);
					sendCardFromFieldToGrave(monsterField, myMonsterIndex);
				}
			}
		}
		monsterField[myMonsterIndex].attack();
	}

	private void directAttack(int myMonsterIndex) {
		int opponentLp = getGame().getNotActivePlayer().getLifePoints();
		int attackPower = monsterField[myMonsterIndex].getAtk();
		getGame().getNotActivePlayer().setLifePoints(opponentLp - attackPower);
		System.out.println(getGame().getNotActivePlayer().getName() + " lose " + attackPower + " lifepoints and has now " + getGame().getNotActivePlayer().getLifePoints() + " LP.");
	}

	public void resetAtkCount(int count) {
		for(int i = 0; i < 5; i++) {
			if(!monsterField[i].isEmpty()) {
				monsterField[i].setAtkCount(count);
			}
		}
	}

	public void resetMonsterModeChange() {
		for(int i = 0; i < 5; i++) {
			if(!monsterField[i].isEmpty()) {
				monsterField[i].setMonsterModeChange(true);
			}
		}
	}

	public void playCard(int handIndex, CardMode cm, MonsterMode mm) {
		Card card = player.getHandCardAt(handIndex);
		int index;
		if(card instanceof MonsterCard) {
			if(player.getSummonCount() > 0) {
				if(canBeSummoned(card)) {
					index = getFreeIndex(monsterField);
					if(index > -1) {
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
		if(tributes == 1 && getMonsterCount() > 0) {
			if(getMonsterCount() == 1) {
				sendCardFromFieldToGrave(monsterField, getFreeTribute());
			}else {
				makeTribute(tributes);
			}
			return true;
		}else if(tributes == 2 && getMonsterCount() > 1) {
			if(getMonsterCount() == 2) {
				sendCardFromFieldToGrave(monsterField, getFreeTribute());
				sendCardFromFieldToGrave(monsterField, getFreeTribute());
			}else {
				makeTribute(tributes);
			}
			return true;
		}else if(tributes == 3 && getMonsterCount() > 2) {
			if(getMonsterCount() == 3) {
				sendCardFromFieldToGrave(monsterField, getFreeTribute());
				sendCardFromFieldToGrave(monsterField, getFreeTribute());
				sendCardFromFieldToGrave(monsterField, getFreeTribute());	
			}else {
				makeTribute(tributes);
			}
			return true;
		}
		System.out.println("You don't have enough Monster on the Field.");
		return false;
	}

	private void makeTribute(int tributes) {
		Scanner sc = new Scanner(System.in);
		for(int i = 0; i < tributes; i++) {
			System.out.println("Choose Tribute " + (i+1) + ":");
			for(int j = 0; j < 5; j++) {
				if(!monsterField[j].isEmpty()) {
					System.out.println(j + ": " + monsterField[j].getCard().getName());
				}
			}
			int selection = sc.nextInt();
			if(monsterField[selection].isEmpty()) {
				System.out.println("Choose again!");
				i--;
			}else {
				sendCardFromFieldToGrave(monsterField, selection);
			}
		}
	}

	private int getFreeTribute() {
		for(int i = 0; i < 5; i++) {
			if(!monsterField[i].isEmpty()) {
				return i;
			}
		}
		return -1;
	}

	private void playHelper(FieldElement[] arr, Card card, CardMode cm, MonsterMode mm, int index, int handIndex) {
		if(arr == null) {
			playFieldSpell((SpellCard)card);
		}else {
			arr[index] = new FieldElement(card, player, mm, cm, false);
		}
		System.out.println("You played " + card.getName() + ".");
		player.dropHandCard(handIndex);
		if(card instanceof SpellCard && cm == CardMode.FACE_UP) {
			activateSpellEffect(index);
		}
	}

	public void playFieldSpell(SpellCard fieldSpell) {
		PlayField otherSide = game.getNotActivePlayer().getPlayField();
		if(otherSide.hasFieldSpell()) {
			otherSide.removeFieldSpell();
		}else if(hasFieldSpell()) {
			removeFieldSpell();
		}
		setFieldSpell(fieldSpell);
	}

	public void activateSpellEffect(int index) {
		SpellCard sc = null;
		if(index == -1) {
			sc = fieldSpell;
		}else {
			sc = (SpellCard) spellAndTrapField[index].getCard();
			if(spellAndTrapField[index].getCardMode() == CardMode.FACE_DOWN) {
				spellAndTrapField[index].setCardMode(CardMode.FACE_UP);
			}
			if(sc.getSpellType() == SpellType.NORMAL || sc.getSpellType() == SpellType.SCHNELL) {
				sendCardFromFieldToGrave(spellAndTrapField, index);
			}
		}
		SpellEffects.activateEffect(sc, this, index);
	}

	public void changeMonsterMode() {
		if(canChangeMonsterMode()) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Which Monster do you want to Change?");
			for(int i = 0; i < 5; i++) {
				if(!monsterField[i].isEmpty()) {
					if(monsterField[i].canChangeMonsterMode()) {
						System.out.println(i + ": " + monsterField[i].getCard().getName());
					}
				}
			}
			int index = sc.nextInt();
			monsterField[index].changeMonsterMode(this);
		}else {
			System.out.println("You don't have Monster which can be changed.");
		}
	}

	private boolean canChangeMonsterMode() {
		for(int i = 0; i < 5; i++) {
			if(!monsterField[i].isEmpty()) {
				if(monsterField[i].canChangeMonsterMode()) {
					return true;
				}
			}
		}
		return false;
	}

	//TODO: sent the Card to the right Grave
	public void sendCardFromHandToGrave(int index) {
		graveyard.add(player.getHandCardAt(index));
		System.out.println(player.getName() + " dropped " + player.getHandCardAt(index).getName() + ".");
		player.dropHandCard(index);
	}

	public void sendCardFromFieldToGrave(FieldElement[] arr, int index) {
		if(arr[index].getOwner().equals(player)) {
			graveyard.add(arr[index].getCard());
			System.out.println(player.getName() + " send " + arr[index].getCard().getName() + " to the graveyard.");
		}else {
			game.getNotActivePlayer().getPlayField().getGraveyard().add(arr[index].getCard());
			System.out.println(player.getName() + " send " + arr[index].getCard().getName() + " to the opponents graveyard.");
		}
		arr[index] = new FieldElement(player);
	}

	public int getFreeIndex(FieldElement[] arr) {
		for(int i = 0; i < 5; i++) {
			if(arr[i].isEmpty()) {
				return i;
			}
		}
		System.out.println("There is no more space for this card at the field.");
		return -1;
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
		if(hasFieldSpell()) {
			graveyard.add(fieldSpell);
		}
		this.fieldSpell = null;
	}

	public boolean hasFieldSpell() {
		if(fieldSpell == null) {
			return false;
		}
		return true;
	}

	public boolean containsAtkMonster() {
		for(int i = 0; i < 5; i++) {
			if(!monsterField[i].isEmpty()) {
				if(monsterField[i].getMonsterMode() == MonsterMode.ATTACK) {
					return true;
				}
			}
		}
		return false;
	}

	public int getAtkMonsterCount() {
		int count = 0;
		for(int i = 0; i < 5; i++) {
			if(!monsterField[i].isEmpty()) {
				if(monsterField[i].getMonsterMode() == MonsterMode.ATTACK && monsterField[i].getAtkCount() > 0) {
					count ++;
				}
			}
		}
		return count;
	}

	public boolean containsMonster() {
		for(int i = 0; i < 5; i++) {
			if(!monsterField[i].isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public boolean containsSpellOrTrapCard() {
		return (containsTrapCard() || containsSpellCard() );
	}

	public boolean containsTrapCard() {
		for(int i = 0; i < 5; i++) {
			if(!spellAndTrapField[i].isEmpty()) {
				if(spellAndTrapField[i].getCard() instanceof TrapCard) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean containsSpellCard() {
		if(hasFieldSpell()) {
			return true;
		}
		for(int i = 0; i < 5; i++) {
			if(!spellAndTrapField[i].isEmpty()) {
				if(spellAndTrapField[i].getCard() instanceof SpellCard) {
					return true;
				}
			}
		}
		return false;
	}

	public int getOnlySpellOrTrapIndex() {
		int index = -1;
		if(hasFieldSpell()) {
			return 5;
		}
		for(int i = 0; i < 5; i++) {
			if(!spellAndTrapField[i].isEmpty()) {
				return i;
			}
		}
		return index;
	}

	public int getOnlySpellOrTrapIndex(int fieldIndex) {
		int index = -1;
		if(hasFieldSpell()) {
			return 5;
		}
		for(int i = 0; i < 5; i++) {
			if(!spellAndTrapField[i].isEmpty()) {
				if(i != fieldIndex) {
					return i;
				}
			}
		}
		return index;
	}

	public int getSpellAndTrapCount() {
		return (getTrapCount() + getSpellCount() );
	}

	public int getTrapCount() {
		int count = 0;
		for(int i = 0; i < 5; i++) {
			if(!spellAndTrapField[i].isEmpty()) {
				if(spellAndTrapField[i].getCard() instanceof TrapCard) {
					count++;
				}
			}
		}
		return count;
	}

	public int getSpellCount() {
		int count = 0;
		for(int i = 0; i < 5; i++) {
			if(!spellAndTrapField[i].isEmpty()) {
				if(spellAndTrapField[i].getCard() instanceof SpellCard) {
					count++;
				}
			}
		}
		if(hasFieldSpell()) {
			count++;
		}
		return count;
	}

	private int getMonsterCount() {
		int count = 0;
		for(int i = 0; i < 5; i++) {
			if(!monsterField[i].isEmpty()) {
				count++;
			}
		}
		return count;
	}

	public FieldElement[] getMonsterField() {
		return monsterField;
	}

	public FieldElement[] getSpellAndTrapField() {
		return spellAndTrapField;
	}

	public ArrayList<FusionMonster> getFusionMonsters() {
		return fusionMonsters;
	}

	public void print() {
		fieldPrinter.printField();
	}

	public SpellCard getFieldSpell() {
		return this.fieldSpell;
	}

	public Game getGame() {
		return this.game;
	}

	public boolean hasGraveMonster() {
		for(Card c : graveyard) {
			if(c instanceof MonsterCard) {
				return true;
			}
		}
		return false;
	}

	public boolean hasFreeMonsterSpace() {
		for(int i = 0; i < 5; i++) {
			if(monsterField[i].isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public void destroyAllMonster() {
		for(int i = 0; i < 5; i++) {
			if(!monsterField[i].isEmpty()) {
				sendCardFromFieldToGrave(monsterField, i);
			}
		}
	}
	//TODO untersuchen warum es nicht funktioniert
	public PlayField getOpponentField() {
		if(getGame().getActivePlayer().equals(player)) {
			return getGame().getNotActivePlayer().getPlayField();
		}
		return this;
	}

	public int getMonsterWithAttributeCount(Attribute att) {
		int count = 0;
		for(int i = 0; i < 5; i++) {
			if(!monsterField[i].isEmpty()) {
				if(monsterField[i].getCardMode() == CardMode.FACE_UP) {
					MonsterCard mc = (MonsterCard) monsterField[i].getCard();
					if(mc.getAttribute() == att) {
						count++;
					}
				}
			}
		}
		return count;
	}

	public void listMonsterByAttribute(Attribute att) {
		System.out.println(player.getName() + " has following " + att + " Monsters: ");
		for(int i = 0; i < 5; i++) {
			if(!monsterField[i].isEmpty()) {
				if(monsterField[i].getCardMode() == CardMode.FACE_UP) {
					MonsterCard mc = (MonsterCard) monsterField[i].getCard();
					if(mc.getAttribute() == att) {
						System.out.println(i + ": " + mc.getName());
					}
				}
			}
		}
	}

	public int getOnlyAttributeMonsterIndex(Attribute att) {
		int index = -1;
		for(int i = 0; i < 5; i++) {
			if(!monsterField[i].isEmpty()) {
				if(monsterField[i].getCardMode() == CardMode.FACE_UP) {
					MonsterCard mc = (MonsterCard) monsterField[i].getCard();
					if(mc.getAttribute() == att) {
						return i;
					}
				}
			}
		}
		return index;
	}

	public int getOnlyAtkMonsterIndex() {
		int index = -1;
		for(int i = 0; i < 5; i++) {
			if(!monsterField[i].isEmpty()) {
				if(monsterField[i].getMonsterMode() == MonsterMode.ATTACK && monsterField[i].getAtkCount() > 0) {
					return i;
				}
			}
		}
		return index;
	}

	public int getOnlyMonsterIndex() {
		int index = -1;
		for(int i = 0; i < 5; i++) {
			if(!monsterField[i].isEmpty()) {
				return i;
			}
		}
		return index;
	}

	public void printSpellCards() {
		for(int i = 0; i < 5; i++) {
			if(!spellAndTrapField[i].isEmpty()) {
				if(spellAndTrapField[i].getCard() instanceof SpellCard) {
					System.out.println(i + ": " + spellAndTrapField[i].getCard().getName());
				}
			}
		}
	}

}
