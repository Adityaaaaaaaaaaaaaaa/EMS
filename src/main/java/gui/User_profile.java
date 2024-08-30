package gui;

import app.Main;
import db.Db_Connect;
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

public class User_profile extends JPanel {
    private JPanel mainJpanel;
    private JPanel menuBar;
    private JLabel menuBarspace;
    private JPanel userProfile;
    private JLabel userDetails;
    private JPanel details;
    private JTextField nameField;
    private JTextField usernameField;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField phoneNumberField;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JPanel buttons;
    private JButton btnBack;

    private static final Logger LOGGER = Logger.getLogger(User_profile.class.getName());

    private Main mainFrame;
    private final String username = "grandgala";

    public User_profile(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);
        setLayout(new BorderLayout());
        add(mainJpanel);

        // Fetch user data and populate fields
        fetchAndDisplayUserData(username); // Pass the username for which data is to be retrieved

        // Action listeners
        btnBack.addActionListener(e -> {
            mainFrame.getScreenManager().showPanel("Screen1"); // Replace with the actual previous screen
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        //once user click the update btn it should enable the text field to be editable and the text on the button should change
       // from "Update Profile" to "Save Profile/Changes"
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void fetchAndDisplayUserData(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Db_Connect.getConnection();
            String sql = "SELECT name, username, email, address, phone FROM users WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username); // Corrected the missing index for the prepared statement parameter
            rs = stmt.executeQuery();

            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                usernameField.setText(rs.getString("username"));
                emailField.setText(rs.getString("email"));
                addressField.setText(rs.getString("address"));
                phoneNumberField.setText(rs.getString("phone"));

                // Make fields non-editable
                nameField.setEditable(false);
                usernameField.setEditable(false);
                emailField.setEditable(false);
                addressField.setEditable(false);
                phoneNumberField.setEditable(false);
            } else {
                JOptionPane.showMessageDialog(this, "User not found!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Database error while fetching user data: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(this, "Database error. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
