import cards.Card;
import cards.Deck;
import database.cards.CardExport;
import database.cards.CardImport;
import database.player.PlayerExport;
import database.player.PlayerImport;
import game.Player;
import game.battle.Game;
import game.battle.PlayPhase;
import game.map.PlayField;
import game.rockpaperscissors.*;

public class Main {

	public static void main(String... args) {
		PlayerImport.importPlayers();
		String player1 = "Rash";
		String player2 = "Computer";
		Player rash = PlayerImport.getPlayerByName(player1);
		Player computer = PlayerImport.getPlayerByName(player2);
		testCardImport();
		Deck rashDeck = testDeck(40);
		if(rash != null && computer != null) {
			rash.setDeck(rashDeck);
			//rash.createDeck();
			PlayField rashField = testPlayField(rash);
			Deck computerDeck = testDeck(40);
			computer.setDeck(computerDeck);
			PlayField computerField = testPlayField(computer);
			Game g = new Game(rashField, computerField);
			g.startGame();
		}else {
			System.out.println("Can't start game because " + player1 + " or " + player2 + " is null.");
		}
	}

	public static PlayField testPlayField(Player p) {
		PlayField pf = new PlayField(p);
		return pf;
	}

	public static void testPlayerExport() {
		PlayerExport.exportPlayer();
	}

	public static void testPlayerImport() {
		PlayerImport.importPlayers();
	}

	public static void testPlayPhase() {
		PlayPhase a = PlayPhase.DRAW;
		System.out.println(a);
		for(int i = 0; i < 20; i++) {
			a = a.nextPhase();
			System.out.println(a);
		}
	}

	public static void testCardImport() {
		CardImport.importCards();
	}

	public static Deck testDeck(int cardLimit) {
		Deck d = new Deck();
		while(d.getDeckCount() < cardLimit) {
			Card c = CardImport.drawRandomCard();
			if(c == null) {
				return null;
			}
			d.addToDeck(c);
		}
		if(cardLimit >= 37) {
			/*
			d.addToDeck(CardImport.getCardByName("Schwert der dunklen Zerstörung"));
			d.addToDeck(CardImport.getCardByName("Schwert der dunklen Zerstörung"));
			d.addToDeck(CardImport.getCardByName("Schwert der dunklen Zerstörung"));
			 */ 
		}
		d.finishDeck();
		d.printDeck();
		return d;
	}

	public static void testExportFusion() {
		CardExport.exportFusionMonster();
	}

	public static void testExportTrap() {
		CardExport.exportTrapCard();
	}

	public static void testExportSpell() {
		CardExport.exportSpellCard();
	}

	public static void testExportMonster() {
		CardExport.exportMonsterCard();
	}

	public static void testRPS(Player human, Player computer) {
		RockPaperScissors.play(human, computer);
	}
}
