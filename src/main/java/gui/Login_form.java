package gui;

import app.Main;
import db.Db_Connect;
import session.Session;
import session.User;
import utility.Utility;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        // Clear error message when user starts typing in the fields
        addFieldListeners();

        // Action listeners
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userID = user_id.getText().trim();
                String password = new String(pwd.getPassword()).trim();

                // Validate input
                if (userID.isEmpty() || password.isEmpty()) {
                    errorMsg.setText("Please enter both Username and Password.");
                    return;
                }

                User user = authenticateUser(userID, password);
                if (user != null) {
                    Session.currentUser = user;
                    clearFields();
                    mainFrame.getScreenManager().showPanel("Screen1");
                } else {
                    errorMsg.setText("Invalid Username or Password. Please try again.");
                }
            }
        });

        btnRegister.addActionListener(e -> System.exit(0));
    }

    // Method to add listeners to clear the error message when typing
    private void addFieldListeners() {
        DocumentListener clearErrorListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                clearErrorMessage();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                clearErrorMessage();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                clearErrorMessage();
            }
        };

        user_id.getDocument().addDocumentListener(clearErrorListener);
        pwd.getDocument().addDocumentListener(clearErrorListener);
    }

    // Clear error message
    private void clearErrorMessage() {
        errorMsg.setText("");
    }

    private User authenticateUser(String userID, String password) {
        User user = null;

        try (Connection connection = Db_Connect.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT role FROM Users WHERE username = ? AND password = ?")) {

            statement.setString(1, userID);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String role = resultSet.getString("role");
                    user = new User(userID, role);
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

    private void clearFields() {
        user_id.setText("");
        pwd.setText("");
    }
}
