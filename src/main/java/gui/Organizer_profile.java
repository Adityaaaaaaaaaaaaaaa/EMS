package gui;

import app.Main;
import utility.Utility;

import javax.swing.*;
import java.awt.*;

public class Organizer_profile extends JPanel {
    private JPanel main_panel;
    private JPanel Menu;

    private Main mainFrame;

    public Organizer_profile(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);
        setLayout(new BorderLayout());
        add(main_panel);
    }

    // Method to enable or disable editing of text fields
    private void enableEditing(boolean enable) {
        OrgName.setEditable(enable);
        OrgUname.setEditable(false); // Username should typically remain non-editable
        OrgEmail.setEditable(enable);
        OrgAdd.setEditable(enable);
        OrgPhoneNum.setEditable(enable);
    }

    // Method to fetch and display user data
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
                OrgName.setText(rs.getString("name"));
                OrgUname.setText(rs.getString("username"));
                OrgEmail.setText(rs.getString("email"));
                OrgAdd.setText(rs.getString("address"));
                OrgPhoneNum.setText(rs.getString("phone"));

                // Make fields non-editable initially
                enableEditing(false);
            } else {
                // this keeps poping up
                // JOptionPane.showMessageDialog(this, "User not found!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Database error while fetching user data: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(this, "Database error. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to update the user's profile in the database
    private boolean updateOrganizerProfile() {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Db_Connect.getConnection();
            conn.setAutoCommit(false); // Disable auto-commit

            String sql = "UPDATE users SET name = ?, email = ?, address = ?, phone = ? WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, OrgName.getText().trim());
            stmt.setString(2, OrgEmail.getText().trim());
            stmt.setString(3, OrgAdd.getText().trim());
            stmt.setString(4, OrgPhoneNum.getText().trim());
            stmt.setString(5, OrgUname.getText().trim()); // Using the username as the identifier

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
    private JTextField OrgName;
    private JTextField OrgUname;
    private JTextField OrgEmail;
    private JTextField OrgPwd;
    private JTextField OrgAdd;
    private JTextField textField1;
    private JLabel OrgPhoneNum;
}
