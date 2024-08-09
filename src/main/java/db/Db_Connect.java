package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db_Connect {
    // Database connection details
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/swingui";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    // Method to establish and return a database connection
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Load the MySQL JDBC driver
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // Method to close the connection, statement, and result set
    public static void close(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
