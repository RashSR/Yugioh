package database.player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import database.DBConnection;

public class PlayerExport {
private static Scanner sc = new Scanner(System.in);
	
	public static void exportPlayer() {
		String pre = "[Player Insert] ";
		Connection conn = DBConnection.connect();
		if(conn == null) {
			return;
		}
		System.out.println("Geben Sie den Namen des neuen Spielers an:");
		String name = sc.nextLine();
		
		PreparedStatement insertPlayer;
		try {
			insertPlayer = conn.prepareStatement("INSERT INTO player (name) VALUES (?)");
			insertPlayer.setString(1, name);
			insertPlayer.executeUpdate();
		}catch(SQLException e) {
			System.out.println(pre + "Can't insert into Database.");
		}
		DBConnection.closeConnection();
	}
	
}
