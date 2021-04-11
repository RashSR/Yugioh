import cards.Card;
import cards.Deck;
import database.cards.CardExport;
import database.cards.CardImport;
import database.player.PlayerExport;
import database.player.PlayerImport;
import game.Player;
import game.battle.Game;
import game.battle.PlayField;
import game.battle.PlayPhase;
import game.rockpaperscissors.*;

public class Main {

	public static void main(String... args) {
		PlayerImport.importPlayers();
		Player rash = PlayerImport.getPlayerFromName("Rash");
		Player computer = PlayerImport.getPlayerFromName("Computer");
		testCardImport();
		Deck rashDeck = testDeck();
		rash.setDeck(rashDeck);
		PlayField rashField = testPlayField(rash);
		Deck computerDeck = testDeck();
		computer.setDeck(computerDeck);
		PlayField computerField = testPlayField(computer);
		Game g = new Game(rashField, computerField);
	}
	
	public static PlayField testPlayField(Player p) {
		PlayField pf = new PlayField(p);
		pf.print();
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

	public static Deck testDeck() {
		Deck d = new Deck();
		while(d.getDeckCount() < 40) {
			Card c = CardImport.drawRandomCard();
			if(c == null) {
				return null;
			}
			d.addToDeck(c);
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

	public static void testRPS() {
		RockPaperScissors.play();
	}
}
