package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Db_Connect {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/Event_Management_System";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static final Logger LOGGER = Logger.getLogger(Db_Connect.class.getName());

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Failed to close the database connection: " + e.getMessage(), e);
            }
        }
    }
}
