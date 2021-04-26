package game.battle;

import java.util.Scanner;
import cards.Card;
import cards.monster.MonsterCard;
import cards.spell.SpellCard;
import cards.spell.SpellType;
import cards.trap.TrapCard;
import game.Player;
import game.map.CardMode;
import game.map.MonsterMode;
import game.map.PlayField;

@SuppressWarnings("resource")
public class Game {
	private Player player1;
	private Player player2;
	private PlayField playField1;
	private PlayField playField2;
	private static final int START_LIFE_POINTS = 8000;
	private static final int START_CARDS = 5;
	private static final int DEFAULT_SUMMON_COUNT = 1;
	private static final int HAND_CARD_LIMIT = 6;
	private PlayPhase activePhase = PlayPhase.END;
	private Player activePlayer;

	public Game(PlayField playField1, PlayField playField2) {
		if(playField1.getPlayer().getDeck().isReady() && playField2.getPlayer().getDeck().isReady()) {
			this.player1 = playField1.getPlayer();
			this.player2 = playField2.getPlayer();
			this.playField1 = playField1;
			this.playField2 = playField2;
			this.playField1.setGame(this);
			this.playField2.setGame(this);
			this.player1.setPlayField(playField1);
			this.player2.setPlayField(playField2);
			setupGame();
		}else {
			System.out.println("Your Decks are not ready.");
		}
	}

	private void setupGame() {
		this.player1.setLifePoints(START_LIFE_POINTS);
		this.player2.setLifePoints(START_LIFE_POINTS);
		activePlayer = player1;
		for(int i = 0; i < START_CARDS; i++) {
			player1.drawCard();
			player2.drawCard();
		}
		playField1.print();
		startGame();
	}

	private void startGame() {
		nextPhase();
		while(player1.getLifePoints() > 0 && player2.getLifePoints() > 0) {
			if(activePlayer.equals(player2)) {
				//endTurn();
				chooseOption();
			}else {
				chooseOption();
			}
		}
		if(player1.getLifePoints() <= 0) {
			System.out.println(player2.getName() + " won the duell.");
		}else {
			System.out.println(player1.getName() + " won the duell.");
		}
	}

	private void chooseOption() {
		System.out.println("What do you want to do?");
		System.out.println("(N): Next Phase");
		System.out.println("(C): Show Hand");
		System.out.println("(P): Play Card");
		System.out.println("(E): End your turn");
		System.out.println("(S): Show Phase");
		System.out.println("(F): Print Field");
		System.out.println("(G): Give up");
		Scanner sc = new Scanner(System.in);
		String option = sc.nextLine();
		switch (option) {
		case "N":
			nextPhase();
			break;
		case "P":
			playCard();
			break;
		case "E":
			endTurn();
			break;
		case "S":
			System.out.println("You are in: " + activePhase);
			break;
		case "G":
			activePlayer.setLifePoints(0);
			break;
		case "C":
			activePlayer.showCards();
			break;
		case "F":
			activePlayer.getPlayField().print();
			break;
		default:
			break;
		}
	}
	//TODO fragen vor einer eingabe

	private void playCard() {
		Scanner sc = new Scanner(System.in);
		int handCards = activePlayer.getHand().size();
		if(handCards == 0) {
			System.out.println("You don't have a Card in your hand.");
		}else {
			int handIndex = 0;
			if(handCards > 1) {
				System.out.println("Which Card you wanna play? (0 - " + (handCards-1) + ")");
				handIndex = sc.nextInt();
			}
			Card c = activePlayer.getHandCardAt(handIndex);
			CardMode cm = null;
			MonsterMode mm = null;
			if(c instanceof MonsterCard) {
				System.out.println("Do you want to play your Monster in ATK(0) or DEF(1) mode?");
				int monsterMode = sc.nextInt();
				if(monsterMode == 0) {
					mm = MonsterMode.ATTACK;
					cm = CardMode.FACE_UP;
				}else {
					mm = MonsterMode.DEFENSE;
					cm = CardMode.FACE_DOWN;
				}
			}else {
				cm = obviousCardMode(c);
				if(cm == null) {
					System.out.println("Do you want to play your Card Face-Up(0) or Face-Down(1)?");
					int cardMode = sc.nextInt();
					if(cardMode == 0) {
						cm = CardMode.FACE_UP;
					}else {
						cm = CardMode.FACE_DOWN;
					}
				}
			}
			activePlayer.getPlayField().playCard(handIndex, cm, mm);
		}
	}

	private CardMode obviousCardMode(Card card){
		if(card instanceof TrapCard) {
			return CardMode.FACE_DOWN;
		}
		if(card instanceof SpellCard) {
			SpellCard c = (SpellCard) card;
			if(c.getSpellType() == SpellType.FELD) {
				return CardMode.FACE_UP;
			}
		}
		return null;
	}

	private void endTurn() {
		activePhase = PlayPhase.END;
		checkTooMuchCards(HAND_CARD_LIMIT);
		nextPhase();
	}

	private void nextPhase() {
		activePhase = activePhase.nextPhase();
		if(activePhase == PlayPhase.DRAW) {
			if(player1.getName().equals(activePlayer.getName())) {
				activePlayer = player2;
			}else {
				activePlayer = player1;
			}
			activePlayer.drawCard();
			nextPhase();
		}else if(activePhase == PlayPhase.STANDBY) {
			activePlayer.setSummonCount(DEFAULT_SUMMON_COUNT);
			nextPhase();
		}else if(activePhase == PlayPhase.END) {
			endTurn();
		}
	}

	public void checkTooMuchCards(int amount) {
		if(activePlayer.getHand().size() > amount) {
			int index = 0;
			if(activePlayer.getName().equals("Computer")) {
				//TODO better AI to drop cards
			}else {
				System.out.println("You can only hold " + amount + " Cards. Which one do you want do drop?");
				activePlayer.showCards();
				Scanner sc = new Scanner(System.in);
				index = sc.nextInt();
			}
			activePlayer.getPlayField().sendCardFromHandToGrave(index);
			checkTooMuchCards(amount);
		}
	}

	public Player getNotActivePlayer() {
		if(activePlayer.equals(player1)) {
			return player2;
		}
		return player1;
	}
	
	public PlayPhase getActivePhase() {
		return this.activePhase;
	}

}
