package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	/*
	 * This class connects to a database and if it is successfull it returns a Connection.
	 */
	private static Connection connection;
	private static String pre = "[PostgreSQL] ";
	private final static String URL = "jdbc:postgresql://hera.hs-regensburg.de/scr41948?currentSchema=yugioh";
	private final static String HOST = "scr41948";	
	private final static String PASSWORD = HOST;
	
	/*
	 * Tries to connect to a database with class-variables URL, HOST and PASSWORD
	 */
	public static Connection connect() {
		try {
			connection = DriverManager.getConnection(URL, HOST, PASSWORD);
			System.out.println(pre + "Connected to database.\n");
			return connection;
		}catch(SQLException e) {
			System.out.println(pre + "Can't connect to database.");
		}
		return null;
	}
	
	/*
	 * Closes the Connection to the database.
	 */
	public static void closeConnection() {
		if(connection != null) {
			try {
				connection.close();
			}catch(SQLException e) {
				System.out.println(pre + "Something is wrong by trying to disconnect.");
			}
			System.out.println("\n" + pre + "Disconnected from database.");
		}else {
			System.out.println("[ERROR] The connection is null!");
		}
	}
}
