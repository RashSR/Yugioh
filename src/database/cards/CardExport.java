package database.cards;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import database.DBConnection;

public class CardExport {

	/*
	 * This class exports Cards to the database.
	 */
	private static Scanner sc = new Scanner(System.in);

	/*
	 * Exports a MonsterCard after getting all needed input.
	 * Input: Name, ATK, DEF, Text, Stars, hasEffect, Attribute, Type
	 */
	public static void exportMonsterCard() {
		String pre = "[Monster Insert] ";
		Connection conn = DBConnection.connect();

		if(conn != null) {	
			System.out.println(pre + "Geben Sie den Namen des Monsters ein:");
			String name = sc.nextLine();
			System.out.println(pre + "Geben Sie ATK des Monsters ein:");
			int atk = sc.nextInt();
			System.out.println(pre + "Geben Sie DEF des Monsters ein:");
			int def = sc.nextInt();
			System.out.println(pre + "Geben Sie den Kartentext des Monsters ein:");
			sc.nextLine();
			String text = sc.nextLine();
			System.out.println(pre + "Wie viele Sterne hat das Monster?");
			int stars = sc.nextInt();
			System.out.println(pre + "Hat das Monster einen Effekt? (true/false)");
			boolean hasEffect = sc.nextBoolean();
			System.out.println(pre + "Geben Sie das Attribute des Monsters ein: (Finsternis, Licht, Erde, Wasser, Feuer, Wind)");
			sc.nextLine();
			String attribute = sc.nextLine();
			System.out.println(pre + "Geben Sie den Monstertyp an:");
			String type = sc.nextLine();
			System.out.println();

			try {
				PreparedStatement insertMonsterCard = conn.prepareStatement("INSERT INTO monster_card VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
				insertMonsterCard.setString(1, name);
				insertMonsterCard.setInt(2, atk);
				insertMonsterCard.setInt(3, def);
				insertMonsterCard.setString(4, text);
				insertMonsterCard.setInt(5, stars);
				insertMonsterCard.setBoolean(6, hasEffect);
				insertMonsterCard.setString(7, attribute);
				insertMonsterCard.setString(8, type);
				
				insertMonsterCard.executeUpdate();
			} catch (SQLException e) {
				System.out.println(pre + "Can't insert into Database.");
			}
			DBConnection.closeConnection();
		}else {
			System.out.println(pre + "The connection is null.");
		}
	}

	/*
	 * Exports a FusionMonster after getting all needed input.
	 * Input: Name, Monster1, Monster2, ATK, DEF, Text, Stars, hasEffect, Attribute, Type
	 */
	public static void exportFusionMonster() {
		String pre = "[Fusion Monster Insert] ";
		Connection conn = DBConnection.connect();

		if(conn != null) {	
			System.out.println(pre + "Geben Sie den Namen des Monsters ein:");
			String name = sc.nextLine();
			System.out.println(pre + "Geben Sie das erste Fusionsmaterial an:");
			String monster1 = sc.nextLine();
			System.out.println(pre + "Geben Sie das zweite Fusionsmaterial an:");
			String monster2 = sc.nextLine();
			System.out.println(pre + "Geben Sie ATK des Monsters ein:");
			int atk = sc.nextInt();
			System.out.println(pre + "Geben Sie DEF des Monsters ein:");
			int def = sc.nextInt();
			System.out.println(pre + "Geben Sie den Kartentext des Monsters ein:");
			sc.nextLine();
			String text = sc.nextLine();
			System.out.println(pre + "Wie viele Sterne hat das Monster?");
			int stars = sc.nextInt();
			System.out.println(pre + "Hat das Monster einen Effekt? (true/false)");
			boolean hasEffect = sc.nextBoolean();
			System.out.println(pre + "Geben Sie das Attribute des Monsters ein: (Finsternis, Licht, Erde, Wasser, Feuer, Wind)");
			sc.nextLine();
			String attribute = sc.nextLine();
			System.out.println(pre + "Geben Sie den Monstertyp an:");
			String type = sc.nextLine();

			try {
				PreparedStatement insertFusionMonster = conn.prepareStatement("INSERT INTO fusion_monster VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				insertFusionMonster.setString(1, name);
				insertFusionMonster.setInt(2, atk);
				insertFusionMonster.setInt(3, def);
				insertFusionMonster.setString(4, text);
				insertFusionMonster.setInt(5, stars);
				insertFusionMonster.setBoolean(6, hasEffect);
				insertFusionMonster.setString(7, attribute);
				insertFusionMonster.setString(8, type);
				insertFusionMonster.setString(9, monster1);
				insertFusionMonster.setString(10, monster2);

				insertFusionMonster.executeUpdate();
			} catch (SQLException e) {
				System.out.println(pre + "Can't insert into Database.");
			}	

			DBConnection.closeConnection();
		}else {
			System.out.println(pre + "The connection is null.");
		}
	}

	/*
	 * Exports a SpellCard after getting all needed input.
	 * Input: Name, Type, Text
	 */
	public static void exportSpellCard() {
		String pre = "[Spell Insert] ";
		Connection conn = DBConnection.connect();

		if(conn != null) {	
			System.out.println(pre + "Geben Sie den Namen der Zauberkarte ein:");
			String name = sc.nextLine();
			System.out.println(pre + "Geben Sie den Typ der Zauberkarte ein: (Normal, Ritual, Permanent, Ausrüstung, Feld, Schnell)");
			String type = sc.nextLine();
			System.out.println(pre + "Geben Sie den Zauberkartentext ein:");
			String text = sc.nextLine();

			try {
				PreparedStatement insertSpellCard = conn.prepareStatement("INSERT INTO spell_card VALUES (?, ?, ?)");
				insertSpellCard.setString(1, name);
				insertSpellCard.setString(2, type);
				insertSpellCard.setString(3, text);

				insertSpellCard.executeUpdate();
			} catch (SQLException e) {
				System.out.println(pre + "Can't insert into Database.");
			}	

			DBConnection.closeConnection();
		}else {
			System.out.println(pre + "The connection is null.");
		}
	}

	/*
	 * Exports a TrapCard after getting all needed input.
	 * Input: Name, Type, Text
	 */
	public static void exportTrapCard() {
		String pre = "[Trap Insert] ";
		Connection conn = DBConnection.connect();

		if(conn != null) {
			System.out.println(pre + "Geben Sie den Namen der Fallenkarte ein:");
			String name = sc.nextLine();
			System.out.println(pre + "Geben Sie den Typ der Fallenkarte ein: (Normal, Permanent, Konter)");
			String type = sc.nextLine();
			System.out.println(pre + "Geben Sie den Fallenkartentext ein:");
			String text = sc.nextLine();

			try {
				PreparedStatement insertTrapCard = conn.prepareStatement("INSERT INTO trap_card VALUES (?, ?, ?)");
				insertTrapCard.setString(1, name);
				insertTrapCard.setString(2, type);
				insertTrapCard.setString(3, text);

				insertTrapCard.executeUpdate();
			} catch (SQLException e) {
				System.out.println(pre + "Can't insert into Database.");
			}

			DBConnection.closeConnection();
		}else {
			System.out.println(pre + "The connection is null.");
		}
	}
}

