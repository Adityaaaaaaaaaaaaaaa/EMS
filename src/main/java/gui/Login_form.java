package gui;

import app.Main;
import db.Db_Connect;
import session.Session;
import session.User;
import utility.Utility;

import javax.swing.*;
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

    private JPanel mainPanel;
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
        add(mainPanel);

        // Action listeners
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userID = user_id.getText().trim();
                String password = new String(pwd.getPassword()).trim();
                User user = authenticateUser(userID, password);
                if (user != null) {
                    Session.currentUser = user;
                    clearFields();
                    mainFrame.getScreenManager().showPanel("Screen1");
                } else {
                    errorMsg.setText("Invalid User ID or Password");
                }
            }
        });

        btnRegister.addActionListener(e -> System.exit(0));
    }

    private User authenticateUser(String userID, String password) {
        User user = null;

        try (Connection connection = Db_Connect.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE user_id = ? AND password = ?")) {

            statement.setString(1, userID);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String role = determineRole(userID);
                    user = new User(userID, role);
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Database error during authentication: " + ex.getMessage(), ex);
        }

        return user;
    }

    private String determineRole(String userID) {
        if (userID.startsWith("A")) {
            return "Admin";
        } else if (userID.startsWith("U")) {
            return "User";
        } else if (userID.startsWith("O")) {
            return "Organizer";
        }
        return "Unknown";
    }

    private void clearFields() {
        user_id.setText("");
        pwd.setText("");
    }
}
