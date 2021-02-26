package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static Connection connection;
	private static String pre = "[PostgreSQL] ";
	
	public static Connection connect() {
		String url = "jdbc:postgresql://hera.hs-regensburg.de/scr41948?currentSchema=yugioh";
		String host = "scr41948";
		String password = host;
		try {
			connection = DriverManager.getConnection(url, host, password);
			System.out.println(pre + "Connected to database.\n");
			return connection;
		}catch(SQLException e) {
			System.out.println(pre + "Can't connect to database.");
		}
		return null;
	}
	
	public static void closeConnection() {
		if(connection != null) {
			try {
				connection.close();
			}catch(SQLException e) {
				System.out.println(pre + "Something is wrong by trying to disconnect.");
			}
			System.out.println("\n" + pre + "Disconnected from database.");
		}
	}
}
