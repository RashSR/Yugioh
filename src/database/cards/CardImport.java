package database.cards;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import cards.Card;
import cards.monster.MonsterCard;
import cards.monster.fusion.FusionMonster;
import cards.spell.SpellCard;
import cards.trap.TrapCard;
import database.DBConnection;

public class CardImport {
	/*
	 * This Class imports Cards from a database.
	 */
	private static String pre = "[Card Import] ";
	private static ArrayList<MonsterCard> monsterCards;
	private static ArrayList<FusionMonster> fusionMonsters;
	private static ArrayList<SpellCard> spellCards;
	private static ArrayList<TrapCard> trapCards;
	private static Random rand = new Random(); 
	private static Connection conn;
	
	/*
	 * Imports all available CardTypes.
	 */
	public static void importCards() {
		conn = DBConnection.connect();
		if(conn != null) {
			importMonsterCards();
			importFusionCards();
			importSpellCards();
			importTrapCards();
			DBConnection.closeConnection();
		}
	}
	
	/*
	 * Imports all MonsterCards from the database and saves it in the local Array monsterCards.
	 */
	private static void importMonsterCards() {
		try {
			PreparedStatement selectMonsterCards = conn.prepareStatement("SELECT * FROM monster_card");
			ResultSet card = selectMonsterCards.executeQuery();
			monsterCards = new ArrayList<>();
			
			while(card.next()) {
				String name = card.getString(1);
				int atk = card.getInt(2);
				int def = card.getInt(3);
				String text = card.getString(4);
				int stars = card.getInt(5);
				boolean hasEffect = card.getBoolean(6);
				String attr = card.getString(7);
				String type = card.getString(8);
				
				MonsterCard mc = new MonsterCard(name, atk, def, text, stars, hasEffect, attr, type);
				monsterCards.add(mc);
			}
			
			printMonsterCards();
		}catch(SQLException e) {
			System.out.println(pre + "Can't get Monster Cards from Database.");
		}
	}

	/*
	 * Imports all FusionMonster from the database and saves it in the local Array fusionMonsters.
	 */
	private static void importFusionCards() {
		try {
			PreparedStatement selectFusionCards = conn.prepareStatement("SELECT * FROM fusion_monster");
			ResultSet card = selectFusionCards.executeQuery();
			fusionMonsters = new ArrayList<>();
			
			while(card.next()) {
				String name = card.getString(1);
				int atk = card.getInt(2);
				int def = card.getInt(3);
				String text = card.getString(4);
				int stars = card.getInt(5);
				boolean hasEffect = card.getBoolean(6);
				String attr = card.getString(7);
				String type = card.getString(8);
				String monster1 = card.getString(9);
				String monster2 = card.getString(10);
				
				FusionMonster fm = new FusionMonster(name, atk, def, text, stars, hasEffect, attr, type, monster1, monster2);
				fusionMonsters.add(fm);
			}
			
			printFusionMonsters();
		}catch(SQLException e) {
			System.out.println(pre + "Can't get Fusion Cards from Database.");
		}
	}

	/*
	 * Imports all SpellCards from the database and saves it in the local Array spellCards.
	 */
	private static void importSpellCards() {
		try {
			PreparedStatement selectSpellCards = conn.prepareStatement("SELECT * FROM spell_card");
			ResultSet card = selectSpellCards.executeQuery();
			spellCards = new ArrayList<>();
			
			while(card.next()) {
				String name = card.getString(1);
				String type = card.getString(2);
				String text = card.getString(3);
				
				SpellCard sc = new SpellCard(name, type, text);
				spellCards.add(sc);
			}
			
			printSpellCards();
		}catch(SQLException e) {
			System.out.println(pre + "Can't get Spell Cards from Database.");
		}
	}

	/*
	 * Imports all TrapCards from the database and saves it in the local Array trapCards.
	 */
	private static void importTrapCards() {
		try {
			PreparedStatement selectTrapCards = conn.prepareStatement("SELECT * FROM trap_card");
			ResultSet card = selectTrapCards.executeQuery();
			trapCards = new ArrayList<>();
			
			while(card.next()) {
				String name = card.getString(1);
				String type = card.getString(2);
				String text = card.getString(3);
				
				TrapCard tc = new TrapCard(name, type, text);
				trapCards.add(tc);
			}
			
			printTrapCards();
		}catch(SQLException e) {
			System.out.println(pre + "Can't get Trap Cards from Database.");
		}
	}

	/*
	 * Prints all MonsterCards to the console.
	 */
	private static void printMonsterCards() {
		if(monsterCards == null || monsterCards.size() == 0) {
			System.out.println("----------------------------------------------------------\n" +pre + "There are no Monster Cards to import.");
		}else {
			System.out.println("----------------------------------------------------------\n" + pre + "Folgende " + monsterCards.size() + " Monsterkarten sind im System:\n");
			for(MonsterCard mc : monsterCards) {
				System.out.println(mc);
			}
		}
	}
	
	/*
	 * Prints all FusionMonster to the console.
	 */
	private static void printFusionMonsters() {
		if(fusionMonsters == null || fusionMonsters.size() == 0) {
			System.out.println("----------------------------------------------------------\n" +pre + "There are no Fusion Cards to import.");
		}else {
			System.out.println("----------------------------------------------------------\n" + pre + "Folgende " + fusionMonsters.size() + " Fusionsmonster sind im System:\n");
			for(FusionMonster fm : fusionMonsters) {
				System.out.println(fm);
			}
		}
	}
	
	/*
	 * Prints all SpellCards to the console.
	 */
	private static void printSpellCards() {
		if(spellCards == null || spellCards.size() == 0) {
			System.out.println("----------------------------------------------------------\n" +pre + "There are no Spell Cards to import.");
		}else {
			System.out.println("----------------------------------------------------------\n" + pre + "Folgende " + spellCards.size() + " Zauberkarten sind im System:\n");
			for(SpellCard sc : spellCards) {
				System.out.println(sc);
			}
		}
	}

	/*
	 * Prints all TrapCards to the console.
	 */
	private static void printTrapCards() {
		if(trapCards == null || trapCards.size() == 0) {
			System.out.println("----------------------------------------------------------\n" +pre + "There are no Trap Cards to imported.");
		}else {
			System.out.println("----------------------------------------------------------\n" + pre + "Folgende " + trapCards.size() + " Fallenkarten sind im System:\n");
			for(TrapCard tc : trapCards) {
				System.out.println(tc);
			}
		}
	}

	/*
	 * Returns a random MonsterCard.
	 */
	public static Card drawRandomMonsterCard() {
		return monsterCards.get(rand.nextInt(monsterCards.size()));
	}

	/*
	 * Returns a random FusionMonster.
	 */
	public static Card drawRandomFusionMonster() {
		return fusionMonsters.get(rand.nextInt(fusionMonsters.size()));
	}

	/*
	 * Returns a random SpellCard.
	 */
	public static Card drawRandomSpellCard() {
		return spellCards.get(rand.nextInt(spellCards.size()));
	}

	/*
	 * Returns a random TrapCard.
	 */
	public static Card drawRandomTrapCard() {
		return trapCards.get(rand.nextInt(trapCards.size()));
	}

	/*
	 * Returns a random Card from all available Cards.
	 */
	public static Card drawRandomCard() {
		ArrayList<Card> cards = new ArrayList<>();
		try {
			for(Card c : monsterCards) {
				cards.add(c);
			}
			for(Card c : fusionMonsters) {
				cards.add(c);
			}
			for(Card c : spellCards) {
				cards.add(c);
			}
			for(Card c : trapCards) {
				cards.add(c);
			}
			Collections.shuffle(cards);
			return cards.get(rand.nextInt(cards.size()));
		}catch(NullPointerException e) {
			System.out.println(pre + "No Cards imported.");
			return null;
		}
	}

	/*
	 * Returns a random Card from all available Cards without FusionMonster.
	 */
	public static Card drawRandomCardNoFusion() {
		ArrayList<Card> cards = new ArrayList<>();
		for(Card c : monsterCards) {
			cards.add(c);
		}
		for(Card c : spellCards) {
			cards.add(c);
		}
		for(Card c : trapCards) {
			cards.add(c);
		}
		Collections.shuffle(cards);
		return cards.get(rand.nextInt(cards.size()));
	}
	
	/*
	 * Returns a Card by the given Name as input.
	 */
	public static Card getCardByName(String name) {
		for(Card c : getAllImportedCards()) {
			if(c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	/*
	 * Returns a ArrayList with all available Cards.
	 */
	public static ArrayList<Card> getAllImportedCards(){
		ArrayList<Card> cards = new ArrayList<>();
		for(Card c : monsterCards) {
			cards.add(c);
		}
		for(Card c : spellCards) {
			cards.add(c);
		}
		for(Card c : trapCards) {
			cards.add(c);
		}
		for(Card c : fusionMonsters) {
			cards.add(c);
		}
		return cards;
	}
}
