package game.battle;

import java.util.Scanner;

import cards.Card;
import cards.monster.MonsterCard;
import cards.spell.SpellCard;
import cards.spell.SpellType;
import game.Player;

public class Game {
	private Player player1;
	private Player player2;
	private PlayField playField1;
	private PlayField playField2;
	private static final int START_LIFE_POINTS = 8000;
	private static final int START_CARDS = 5;
	private PlayPhase activePhase = PlayPhase.END;
	private Player activePlayer;

	public Game(PlayField playField1, PlayField playField2) {
		this.player1 = playField1.getPlayer();
		this.player2 = playField2.getPlayer();
		this.playField1 = playField1;
		this.playField2 = playField2;
		this.playField1.setGame(this);
		this.playField2.setGame(this);
		this.player1.setPlayField(playField1);
		this.player2.setPlayField(playField2);
		setupGame();
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
				endTurn();
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
			playField1.print();
			break;
		default:
			break;
		}
	}

	private void playCard() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Which Card you wanna play?");
		int handIndex = sc.nextInt();
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
			System.out.println("Do you want to play your Card Face-Up(0) or Face-Down(1)?");
			int cardMode = sc.nextInt();
			if(cardMode == 0) {
				cm = CardMode.FACE_UP;
			}else {
				cm = CardMode.FACE_DOWN;
			}
		}
		playField1.playCard(handIndex, cm, mm);
	}

	private void endTurn() {
		activePhase = PlayPhase.END;
		checkTooMuchCards();
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
			nextPhase();
		}else if(activePhase == PlayPhase.END) {
			endTurn();
		}
	}
	
	private void checkTooMuchCards() {
		if(activePlayer.getHand().size() > 6) {
			int index = 0;
			if(activePlayer.getName().equals("Computer")) {
				
			}else {
				System.out.println("You can only hold 6 Cards. Which one do you want do drop?");
				activePlayer.showCards();
				Scanner sc = new Scanner(System.in);
				index = sc.nextInt();
			}
			activePlayer.getPlayField().sendCardToGrave(index);
			checkTooMuchCards();
		}
	}
	
	public void playFieldSpell(SpellCard fieldSpell, Player player) {
		if(player.equals(player1)) {
			if(playField2.hasFieldSpell()) {
				playField2.removeFieldSpell();
			}
			playField1.setFieldSpell(fieldSpell);
		}else {
			if(playField1.hasFieldSpell()) {
				playField1.removeFieldSpell();
			}
			playField2.setFieldSpell(fieldSpell);
		}
	}

	public void changeCardState(PlayField pf, int row, int col) {
		pf.changeState(row, col);
	}

	public void attack(PlayField attPf, int attCol, PlayField defPf, int defCol) {
		FieldElement attElement = attPf.getFieldElement(0, attCol);
		FieldElement defElement = defPf.getFieldElement(0, defCol);
		MonsterCard attacker = (MonsterCard) attElement.getCard();
		MonsterCard defender = (MonsterCard) defElement.getCard();
		int attATK = attacker.getAtk();
		int defATK = defender.getAtk();
		int defDEF = defender.getDef();
		if(defElement.getMonsterMode() == MonsterMode.DEFENSE) {
			if(attATK == defATK) {
				sendCardToGrave(attacker);
				sendCardToGrave(defender);
			}else if(attATK < defATK) {
				sendCardToGrave(attacker);
				reduceLifePoints(activePlayer, defATK - attATK);
			}else if(attATK > defATK) {
				sendCardToGrave(defender);
				reduceLifePoints(getNotActivePlayer(), attATK - defATK);
			}
		}else if(defElement.getMonsterMode() == MonsterMode.ATTACK) {
			if(attATK > defDEF) {
				sendCardToGrave(defender);
			}else if(attATK < defDEF) {
				reduceLifePoints(activePlayer, defDEF-attATK);
			}else if(attATK == defDEF) {
				System.out.println("Nothing happen.");
			}	
		}
	}

	private void sendCardToGrave(Card c) {
		//TODO: send Cards to the Graveyard
	}

	private void reduceLifePoints(Player p, int amount) {
		p.setLifePoints(p.getLifePoints()-amount);
	}

	private Player getNotActivePlayer() {
		if(activePlayer.equals(player1)) {
			return player2;
		}
		return player1;
	}

}
