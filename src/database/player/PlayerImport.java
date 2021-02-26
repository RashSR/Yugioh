package database.player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection;
import game.Player;

public class PlayerImport {
	private static String pre = "[Player Import] ";
	private static ArrayList<Player> players;

	public static void importPlayers() {
		Connection conn = DBConnection.connect();
		if(conn != null) {
			try {
				PreparedStatement selectPlayers = conn.prepareStatement("SELECT * FROM player");
				ResultSet player = selectPlayers.executeQuery();
				players = new ArrayList<>();
				while(player.next()) {
					String name = player.getString(1);
					int totalGames = player.getInt(2);
					int wins = player.getInt(3);
					players.add(new Player(name, totalGames, wins));
				}
				printPlayers();
				DBConnection.closeConnection();
			}catch(SQLException e) {
				System.out.println(pre + "Can't get Players from Database.");
			}
		}
	}
	
	private static void printPlayers() {
		if(players == null || players.size() == 0) {
			System.out.println("----------------------------------------------------------\n" +pre + "There are no Players to import.");
		}else {
			System.out.println("----------------------------------------------------------\n" + pre + "Folgende " + players.size() + " Spieler sind im System:\n");
			for(Player p : players) {
				System.out.println(p);
			}
		}
	}
	
	public static Player getPlayerFromName(String name) {
		if(players != null) {
			for(Player p : players) {
				if(name.equals(p.getName())) {
					return p;
				}
			}
			System.out.println(pre + "Spieler " + name + " ist nicht im System.");
			return null;
		}
		System.out.println(pre + "Du musst die Spieler zuerst importieren, bevor du auf sie zugreifen kannst.");
		return null;
	}
}
