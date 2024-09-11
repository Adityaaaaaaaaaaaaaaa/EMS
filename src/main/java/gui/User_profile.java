package gui;

import app.Main;
import db.Db_Connect;
import session.Session;
import utility.Utility;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class User_profile extends JPanel implements MenuInterface {
    private JPanel mainJpanel;
    private JPanel userProfile;
    private JPanel details;
    private JTextField nameField;
    private JTextField usernameField;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField phoneNumberField;
    private JPanel buttons;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnCancel;
    private JMenuBar menuBar;


    private static final Logger LOGGER = Logger.getLogger(User_profile.class.getName());

    private Main mainFrame;

    private boolean isEditing = false; // Track whether the user is editing the profile

    private BufferedImage backgroundImage;

    public User_profile(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);
        try {
            backgroundImage = ImageIO.read(new File("src/main/resources/image/bg4.png"));  // Provide the correct path to your image
        } catch (IOException ex) {
            Logger.getLogger(User_profile.class.getName()).log(Level.SEVERE, null, ex);
        }



        // Set layout and make the panel transparent
        setLayout(new BorderLayout());
        add(mainJpanel, BorderLayout.CENTER);
        setOpaque(false);  // Make this JPanel transparent so the background image is visible

        details.setBackground(new Color(0, 0, 0, 150));  // Semi-transparent black background
        details.setOpaque(true);  // Ensure the 'details' panel is opaque to show the background

        buttons.setBackground(new Color(0, 0, 0, 90));  // Semi-transparent black background for the buttons panel
        buttons.setOpaque(true);  // Ensure the 'buttons' panel is opaque to show the background

        // Remove border and focus indicator for the Update button
        btnUpdate.setBorderPainted(false);  // Removes the border color
        btnUpdate.setFocusPainted(false);   // Removes the focus border
        btnUpdate.setContentAreaFilled(true); // Set to false if you want the background transparent

// Remove border and focus indicator for the Delete button
        btnDelete.setBorderPainted(false);  // Removes the border color
        btnDelete.setFocusPainted(false);   // Removes the focus border
        btnDelete.setContentAreaFilled(true); // Set to false if you want the background transparent

// Remove border and focus indicator for the Cancel button
        btnCancel.setBorderPainted(false);  // Removes the border color
        btnCancel.setFocusPainted(false);   // Removes the focus border
        btnCancel.setContentAreaFilled(true); // Set to false if you want the background transparent


        // Disable background painting for components
        makeComponentsTransparent(mainJpanel);

        // Add the main JPanel containing your user profile components
        add(mainJpanel);

        // Defer session checks until after initialization
        SwingUtilities.invokeLater(() -> {
            if (Session.currentUser != null) {
                String username = Session.currentUser.getId();
                //fetchAndDisplayUserData(Session.currentUser.getId());
                fetchAndDisplayUserData(username);
            } else {
                mainFrame.getScreenManager().showPanel("Login_form");
            }
        });

        btnCancel.addActionListener(e -> {
            mainFrame.getScreenManager().showPanel("Screen1");
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        // Create a menu bar and initialize it with the menu items and listeners
        menuBar = new JMenuBar();
        initializeMenu(menuBar, mainFrame, mainJpanel.getBackground(), mainJpanel.getForeground());

        // Add the menu bar to the panel
        add(menuBar, BorderLayout.NORTH);

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isEditing) {
                    enableEditing(true);
                    btnUpdate.setText("Save");
                    isEditing = true;
                } else {
                    if (updateUserProfile()) {
                        enableEditing(false);
                        btnUpdate.setText("Update");
                        isEditing = false;
                    }
                }
            }
        });

        // Action listener for Delete button (optional functionality)
        btnDelete.addActionListener(e -> {
            int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your profile?", "Delete Profile", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                //deleteUserProfile(username);
            }
        });
    }

    // Helper method to make all components inside the panel transparent
    private void makeComponentsTransparent(JPanel panel) {
        panel.setOpaque(false);  // Set the panel itself to transparent
        Component[] components = panel.getComponents();  // Get all child components
        for (Component component : components) {
            if (component instanceof JPanel) {
                // Recursively make child JPanels transparent, except 'details' and 'buttons'
                if (component != details && component != buttons) {
                    ((JPanel) component).setOpaque(false);
                    makeComponentsTransparent((JPanel) component);
                }
            } else if (component instanceof JComponent) {
                // Set all other components (e.g., buttons, text fields) to be non-opaque
                ((JComponent) component).setOpaque(false);
            }
        }
    }

    // Override paintComponent to draw the background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void enableEditing(boolean enable) {
        nameField.setEditable(enable);
        usernameField.setEditable(false);
        emailField.setEditable(enable);
        addressField.setEditable(enable);
        phoneNumberField.setEditable(enable);
    }

    private boolean updateUserProfile() {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Db_Connect.getConnection();

            String sql = "UPDATE users SET name = ?, email = ?, address = ?, phone = ? WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameField.getText().trim());
            stmt.setString(2, emailField.getText().trim());
            stmt.setString(3, addressField.getText().trim());
            stmt.setString(4, phoneNumberField.getText().trim());
            stmt.setString(5, usernameField.getText().trim());
            /*username);*/

            System.out.println("Updating user: " + usernameField.getText().trim());
            System.out.println("New Name: " + nameField.getText().trim());
            System.out.println("New Email: " + emailField.getText().trim());
            System.out.println("New Address: " + addressField.getText().trim());
            System.out.println("New Phone: " + phoneNumberField.getText().trim());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                // No need to manually commit when autoCommit is true
                JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                // No need to rollback, just handle it as an error
                JOptionPane.showMessageDialog(this, "Profile update failed!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Database error while updating user data: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(this, "Database error. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing database resources: " + e.getMessage(), e);
            }
        }
    }

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

    public void clearFields() {
        nameField.setText("");
        usernameField.setText("");
        emailField.setText("");
        addressField.setText("");
        phoneNumberField.setText("");
    }


    // Optional delete profile method
    private void deleteUserProfile(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Db_Connect.getConnection();
            String sql = "DELETE FROM users WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Profile deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                mainFrame.getScreenManager().showPanel("Screen1"); // Navigate back to a different screen after deletion
            } else {
                JOptionPane.showMessageDialog(this, "Profile deletion failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Database error while deleting user data: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(this, "Database error. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
