package database.player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import database.DBConnection;

public class PlayerExport {
	/*
	 * This Class exports new Player.
	 */
	private static Scanner sc = new Scanner(System.in);

	/*
	 * Exports a new Player after getting all needed input.
	 * Input: Name
	 */
	public static void exportPlayer() {
		String pre = "[Player Insert] ";
		Connection conn = DBConnection.connect();
		if(conn != null) {
			System.out.println("Geben Sie den Namen des neuen Spielers an:");
			String name = sc.nextLine();

			try {
				PreparedStatement insertPlayer = conn.prepareStatement("INSERT INTO player (name) VALUES (?)");
				insertPlayer.setString(1, name);
				insertPlayer.executeUpdate();
			}catch(SQLException e) {
				System.out.println(pre + "Can't insert into Database.");
			}

			DBConnection.closeConnection();
		}else {
			System.out.println(pre + "This connection is null.");
		}
	}

}
