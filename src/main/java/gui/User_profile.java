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
    private boolean isEditing = false; // Track whether the user is editing the profile

    public User_profile(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);
        setLayout(new BorderLayout());
        add(mainJpanel);

        // Fetch user data and populate fields
        fetchAndDisplayUserData(username);

        // Action listeners
        btnBack.addActionListener(e -> {
            mainFrame.getScreenManager().showPanel("Screen1");
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        // Enable editing and change button text on click
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isEditing) {
                    enableEditing(true);
                    btnUpdate.setText("Save Profile");
                    isEditing = true;
                } else {
                    if (updateUserProfile()) {
                        enableEditing(false);
                        btnUpdate.setText("Update Profile");
                        isEditing = false;
                    }
                }
            }
        });
    }

    private void enableEditing(boolean enable) {
        nameField.setEditable(enable);
        usernameField.setEditable(false); // Username should typically remain non-editable
        emailField.setEditable(enable);
        addressField.setEditable(enable);
        phoneNumberField.setEditable(enable);
    }

    private boolean updateUserProfile() {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Db_Connect.getConnection();
            conn.setAutoCommit(false); // Disable auto-commit

            String sql = "UPDATE users SET name = ?, email = ?, address = ?, phone = ? WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameField.getText().trim());
            stmt.setString(2, emailField.getText().trim());
            stmt.setString(3, addressField.getText().trim());
            stmt.setString(4, phoneNumberField.getText().trim());
            stmt.setString(5, username); // Using the username as the identifier

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                conn.commit(); // Commit the transaction
                JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                conn.rollback(); // Rollback in case of failure
                JOptionPane.showMessageDialog(this, "Profile update failed!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Database error while updating user data: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(this, "Database error. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback on error
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Rollback failed: " + e.getMessage(), e);
            }
            return false;
        }
    }

    private void fetchAndDisplayUserData(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Db_Connect.getConnection();
            String sql = "SELECT name, username, email, address, phone FROM users WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                usernameField.setText(rs.getString("username"));
                emailField.setText(rs.getString("email"));
                addressField.setText(rs.getString("address"));
                phoneNumberField.setText(rs.getString("phone"));

                // Make fields non-editable initially
                enableEditing(false);
            } else {
                JOptionPane.showMessageDialog(this, "User not found!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Database error while fetching user data: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(this, "Database error. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
