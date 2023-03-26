package task.config;

import java.sql.*;

public class DBConfig {

    // The constant in which the connection address is stored
    private static final String CON_STR = "jdbc:sqlite::resource:test.db";
    private static DBConfig instance = null;
    private static Connection connection;

    // We use a singleton so as not to produce many instances of the DBHandler class
    public static synchronized DBConfig getInstance() {
        if (instance == null)
            instance = new DBConfig();
        return instance;
    }

    private DBConfig() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(CON_STR);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getSQLiteConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(CON_STR);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeSQLiteConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
