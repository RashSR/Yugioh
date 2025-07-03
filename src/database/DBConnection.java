package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	/*
	 * This class connects to a database and if it is successfull it returns a Connection.
	 */
    private static Connection connection;
    private static final String PRE = "[SQLite] ";

    private static final String DB_FILE = "src\\database\\Database";
    private static final String URL     = "jdbc:sqlite:" + DB_FILE;
    
	/*
	 * Tries to connect to a database with URL
	 */
    public static Connection connect() {
        try {
            connection = DriverManager.getConnection(URL);
            System.out.println(PRE + "Connected to database.");
            return connection;
        } catch (SQLException e) {
            System.out.println(PRE + "Can't connect to database.");
            e.printStackTrace();
        }
        return null;
    }

	/*
	 * Closes the Connection to the database.
	 */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println(PRE + "Disconnected from database.");
            } catch (SQLException e) {
                System.out.println(PRE + "Error while disconnecting.");
            }
        } else {
            System.out.println("[ERROR] The connection is null!");
        }
    }
}