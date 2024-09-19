package gui;

import app.Main;
import db.Db_Connect;
import session.Session;
import utility.MenuInterface;
import utility.Utility;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Organizer_profile extends JPanel implements MenuInterface {
    private JPanel main_panel;
    private JTextField OrgName;
    private JTextField OrgUname;
    private JTextField OrgEmail;
    private JTextField OrgAdd;
    private JTextField OrgPhoneNum;
    private JButton btnUpdate;
    private JButton btnCancel;
    private JMenuBar menuBar;

    private static final Logger LOGGER = Logger.getLogger(Organizer_profile.class.getName());

    private Main mainFrame;
    private boolean isEditing = false;

    public Organizer_profile(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);
        setLayout(new BorderLayout());
        add(main_panel, BorderLayout.CENTER);

        SwingUtilities.invokeLater(() -> {
            String currentUser = Session.currentUser.getId();

            System.out.println("Attempting to fetch data for username: " + currentUser);
            fetchAndDisplayUserData(currentUser);
        });

        // Create a menu bar and initialize it with the menu items and listeners
        menuBar = new JMenuBar();
        initializeMenu(menuBar, mainFrame, main_panel.getBackground(), main_panel.getForeground());
        add(menuBar, BorderLayout.NORTH);

        // Action listener for the Cancel button
        btnCancel.addActionListener(e -> {
            mainFrame.getScreenManager().showPanel("Screen1");
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        // Action listener for the Update/Save Profile button
        btnUpdate.addActionListener(e -> {
            if (!isEditing) {
                enableEditing(true);
                btnUpdate.setText("Save Profile");
                isEditing = true;
            } else {
                if (updateOrganizerProfile()) {
                    enableEditing(false);
                    btnUpdate.setText("Update Profile");
                    isEditing = false;
                }
            }
        });
    }

    // Method to enable or disable editing of text fields
    private void enableEditing(boolean enable) {
        OrgName.setEditable(enable);
        OrgUname.setEditable(false);
        OrgEmail.setEditable(enable);
        OrgAdd.setEditable(enable);
        OrgPhoneNum.setEditable(enable);
    }

    // Method to fetch and display user data original function
    void fetchAndDisplayUserData(String username) {
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
                System.out.println("User found in the database.");
                OrgName.setText(rs.getString("name"));
                OrgUname.setText(rs.getString("username"));
                OrgEmail.setText(rs.getString("email"));
                OrgAdd.setText(rs.getString("address"));
                OrgPhoneNum.setText(rs.getString("phone"));
                enableEditing(false);
            } else {
                System.out.println("User not found in the database for username: " + username);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Database error while fetching user data: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(this, "Database error. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean updateOrganizerProfile() {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Db_Connect.getConnection();

            String sql = "UPDATE users SET name = ?, email = ?, address = ?, phone = ? WHERE username = ?";
            stmt = conn.prepareStatement(sql);

            // Logging values for debugging
            System.out.println("Updating user: " + OrgUname.getText().trim());
            System.out.println("New Name: " + OrgName.getText().trim());
            System.out.println("New Email: " + OrgEmail.getText().trim());
            System.out.println("New Address: " + OrgAdd.getText().trim());
            System.out.println("New Phone: " + OrgPhoneNum.getText().trim());

            // Set the values in the PreparedStatement
            stmt.setString(1, OrgName.getText().trim());
            stmt.setString(2, OrgEmail.getText().trim());
            stmt.setString(3, OrgAdd.getText().trim());
            stmt.setString(4, OrgPhoneNum.getText().trim());
            stmt.setString(5, OrgUname.getText().trim()); // Username should be correct

            int rowsUpdated = stmt.executeUpdate();

            // Check if rows were updated and log the result
            if (rowsUpdated > 0) {
                System.out.println("Rows updated: " + rowsUpdated);
                JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                System.out.println("No rows updated");
                JOptionPane.showMessageDialog(this, "Profile update failed!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Database error while updating user data: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(this, "Database error. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Method to clear all fields in the profile
    public void clearFields() {
        OrgName.setText("");
        OrgUname.setText("");
        OrgEmail.setText("");
        OrgAdd.setText("");
        OrgPhoneNum.setText("");
    }

}
