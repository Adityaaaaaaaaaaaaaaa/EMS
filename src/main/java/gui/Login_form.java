package gui;

import app.Main;
import db.Db_Connect;
import session.Session;
import session.User;
import utility.Utility;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login_form extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(Login_form.class.getName());

    private JPanel Login_Panel;
    private JTextField user_id;
    private JPasswordField pwd;
    private JButton btnLogin;
    private JButton btnRegister;
    private JLabel errorMsg;

    private Main mainFrame;

    public Login_form(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);
        setLayout(new BorderLayout());
        add(Login_Panel);

        // Clear any previous session
        Session.currentUser = null;

        // Clear error message when user starts typing in the fields
        Utility.addFieldListeners(errorMsg, user_id, pwd);

        // Action listeners
        btnLogin.addActionListener(e -> {
            String userID = user_id.getText().trim();
            String password = new String(pwd.getPassword()).trim();

            // Validate input
            if (userID.isEmpty() || password.isEmpty()) {
                errorMsg.setText("Please enter both Username and Password.");
                return;
            }

            // Authenticate user
            User user = authenticateUser(userID, password);
            if (user != null) {
                // Assign session after successful authentication
                Session.currentUser = user;

                // Logging session details immediately
                System.out.println("\nUser Authenticated:");
                System.out.println("ID: " + user.getId());
                System.out.println("Role: " + user.getRole());

                // Check session assignment
                if (Session.currentUser != null) {
                    System.out.println("login Session Set for: " + Session.currentUser.getId());
                } else {
                    System.out.println("Failed to set session.");
                }

                // Clear form and proceed
                Utility.clearForm(new JTextField[]{user_id}, pwd, null, errorMsg);
                mainFrame.getScreenManager().showPanel("Screen1");
                mainFrame.revalidate();
                mainFrame.repaint();
            } else {
                errorMsg.setText("Invalid Username or Password. Please try again.");
            }
        });

        btnRegister.addActionListener(e -> mainFrame.getScreenManager().showPanel("Register_form"));
    }

    /*private User authenticateUser(String userID, String password) {
        User user = null;

        try (Connection connection = Db_Connect.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT role FROM Users WHERE username = ? AND password = ?")) {

            statement.setString(1, userID);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String role = resultSet.getString("role");
                    user = new User(userID, role);  // Create User object with retrieved role
                } else {
                    errorMsg.setText("Username or Password is incorrect.");
                }
            }

        } catch (SQLException ex) {
            errorMsg.setText("Database connection failed. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error during authentication: " + ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            errorMsg.setText("Internal error. Please contact support.");
            LOGGER.log(Level.SEVERE, "JDBC Driver not found: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            errorMsg.setText("Unexpected error occurred. Please try again.");
            LOGGER.log(Level.SEVERE, "Unexpected error during authentication: " + ex.getMessage(), ex);
        }

        return user;
    }*/

    private User authenticateUser(String userID, String password) {
        User user = null;

        try (Connection connection = Db_Connect.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT username, role, name, email, phone FROM Users WHERE username = ? AND password = ?")) {

            statement.setString(1, userID);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Fetch user data from the database
                    String id = resultSet.getString("username");
                    String role = resultSet.getString("role");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String phone = resultSet.getString("phone");

                    // Create a User object with the fetched data
                    user = new User(id, role, name, email, phone);
                } else {
                    errorMsg.setText("Username or Password is incorrect.");
                }
            }

        } catch (SQLException ex) {
            errorMsg.setText("Database connection failed. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error during authentication: " + ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            errorMsg.setText("Internal error. Please contact support.");
            LOGGER.log(Level.SEVERE, "JDBC Driver not found: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            errorMsg.setText("Unexpected error occurred. Please try again.");
            LOGGER.log(Level.SEVERE, "Unexpected error during authentication: " + ex.getMessage(), ex);
        }

        return user;
    }

}
