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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Register_form extends JPanel {
    private JPanel Register_Panel;
    private JTextField regName;
    private JTextField regEmail;
    private JTextField regUserName;
    private JTextField regAddress;
    private JTextField regPhone;
    private JPasswordField regPwd;
    private JButton btnBack;
    private JButton btnRegister;
    private JLabel errorMsg;
    private JRadioButton roleUser;
    private JRadioButton roleOrganiser;
    private ButtonGroup roleGroup;

    private static final Logger LOGGER = Logger.getLogger(Register_form.class.getName());

    private Main mainFrame;

    public Register_form(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);
        setLayout(new BorderLayout());
        add(Register_Panel);

        // Group role radio buttons
        roleGroup = new ButtonGroup();
        roleGroup.add(roleUser);
        roleGroup.add(roleOrganiser);

        // Set default selected radio button
        roleUser.setSelected(true);

        // Clear error message when user starts typing in the fields
        Utility.addFieldListeners(errorMsg, regName, regEmail, regUserName, regAddress, regPhone, regPwd);

        // Action listeners
        btnRegister.addActionListener(e -> {
            if (validateInput()) {
                registerUser();
                Utility.clearForm(new JTextField[]{regName, regEmail, regUserName, regAddress, regPhone}, regPwd, roleUser, errorMsg);
            }
        });

        btnBack.addActionListener(e -> {
            mainFrame.getScreenManager().showPanel("Login_form");
            Utility.clearForm(new JTextField[]{regName, regEmail, regUserName, regAddress, regPhone}, regPwd, roleUser, errorMsg);
        });
    }

    private boolean validateInput() {
        if (regName.getText().trim().isEmpty() || regEmail.getText().trim().isEmpty() ||
                regUserName.getText().trim().isEmpty() || regAddress.getText().trim().isEmpty() ||
                regPhone.getText().trim().isEmpty() || new String(regPwd.getPassword()).trim().isEmpty()) {
            errorMsg.setText("All fields are required.");
            return false;
        }

        if (!roleUser.isSelected() && !roleOrganiser.isSelected()) {
            errorMsg.setText("Please select a role.");
            return false;
        }

        return true;
    }

    /*private void registerUser() {
        String role = roleUser.isSelected() ? "User" : "Organizer";

        try (Connection connection = Db_Connect.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Users (name, email, username, address, phone, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            statement.setString(1, regName.getText().trim());
            statement.setString(2, regEmail.getText().trim());
            statement.setString(3, regUserName.getText().trim());
            statement.setString(4, regAddress.getText().trim());
            statement.setString(5, regPhone.getText().trim());
            statement.setString(6, new String(regPwd.getPassword()).trim());
            statement.setString(7, role);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                // Create a new User object and set it in the Session
                Session.currentUser = new User(regUserName.getText().trim(), role);

                // Print or log the details of the registered user and the session
                System.out.println("\nUser Registered:");
                System.out.println("ID: " + regUserName.getText().trim());
                System.out.println("Role: " + role);
                System.out.println("\nCurrent Session User:");
                System.out.println("ID: " + Session.currentUser.getId());
                System.out.println("Role: " + Session.currentUser.getRole());

                //mainFrame.getScreenManager().showPanel("Screen1");

                // Switch to Screen1 and refresh the UI
                mainFrame.getScreenManager().showPanel("Screen1");
                mainFrame.revalidate();
                mainFrame.repaint();

            } else {
                JOptionPane.showMessageDialog(this, "Registration failed!", "Error", JOptionPane.INFORMATION_MESSAGE);
                errorMsg.setText("Registration failed. Please try again.");
            }

        } catch (SQLException ex) {
            errorMsg.setText("Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error during registration: " + ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            errorMsg.setText("Internal error. Please contact support.");
            LOGGER.log(Level.SEVERE, "JDBC Driver not found: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            errorMsg.setText("Unexpected error occurred. Please try again.");
            LOGGER.log(Level.SEVERE, "Unexpected error during registration: " + ex.getMessage(), ex);
        }
    }*/

    /*private void registerUser() {
        String role = roleUser.isSelected() ? "User" : "Organizer";

        try (Connection connection = Db_Connect.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Users (name, email, username, address, phone, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            String name = regName.getText().trim();
            String email = regEmail.getText().trim();
            String username = regUserName.getText().trim();
            String address = regAddress.getText().trim();
            String phone = regPhone.getText().trim();
            String password = new String(regPwd.getPassword()).trim();

            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, username);
            statement.setString(4, address);
            statement.setString(5, phone);
            statement.setString(6, password);
            statement.setString(7, role);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                // Create a new User object with all the registered user's details
                Session.currentUser = new User(username, role, name, email, phone);

                // Print or log the details of the registered user and the session
                System.out.println("\nUser Registered:");
                System.out.println("ID: " + username);
                System.out.println("Role: " + role);
                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Phone: " + phone);
                System.out.println("\nCurrent Session User:");
                System.out.println("ID: " + Session.currentUser.getId());
                System.out.println("Role: " + Session.currentUser.getRole());
                System.out.println("Name: " + Session.currentUser.getName());
                System.out.println("Email: " + Session.currentUser.getEmail());
                System.out.println("Phone: " + Session.currentUser.getPhone());

                // Switch to Screen1 and refresh the UI
                mainFrame.getScreenManager().showPanel("Screen1");
                mainFrame.revalidate();
                mainFrame.repaint();

            } else {
                JOptionPane.showMessageDialog(this, "Registration failed!", "Error", JOptionPane.INFORMATION_MESSAGE);
                errorMsg.setText("Registration failed. Please try again.");
            }

        } catch (SQLException ex) {
            errorMsg.setText("Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error during registration: " + ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            errorMsg.setText("Internal error. Please contact support.");
            LOGGER.log(Level.SEVERE, "JDBC Driver not found: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            errorMsg.setText("Unexpected error occurred. Please try again.");
            LOGGER.log(Level.SEVERE, "Unexpected error during registration: " + ex.getMessage(), ex);
        }
    }*/

    /*private void registerUser() {
        String role = roleUser.isSelected() ? "User" : "Organizer";

        try (Connection connection = Db_Connect.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Users (name, email, username, address, phone, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            String name = regName.getText().trim();
            String email = regEmail.getText().trim();
            String username = regUserName.getText().trim();
            String address = regAddress.getText().trim();
            String phone = regPhone.getText().trim();
            String password = new String(regPwd.getPassword()).trim();

            // Set parameters for the prepared statement
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, username);
            statement.setString(4, address);
            statement.setString(5, phone);
            statement.setString(6, password);
            statement.setString(7, role);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                // Session creation: Create a User object and store it in the session
                Session.currentUser = new User(username, role, name, email, phone);

                // Logging the details to verify session creation
                System.out.println("\nUser Registered:");
                System.out.println("ID: " + username);
                System.out.println("Role: " + role);
                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Phone: " + phone);
                System.out.println("\nCurrent Session User:");
                System.out.println("ID: " + Session.currentUser.getId());
                System.out.println("Role: " + Session.currentUser.getRole());
                System.out.println("Name: " + Session.currentUser.getName());
                System.out.println("Email: " + Session.currentUser.getEmail());
                System.out.println("Phone: " + Session.currentUser.getPhone());

                // Redirect to Screen1 after successful registration
                mainFrame.getScreenManager().showPanel("Screen1");
                mainFrame.revalidate();
                mainFrame.repaint();

            } else {
                JOptionPane.showMessageDialog(this, "Registration failed!", "Error", JOptionPane.INFORMATION_MESSAGE);
                errorMsg.setText("Registration failed. Please try again.");
            }

        } catch (SQLException ex) {
            errorMsg.setText("Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error during registration: " + ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            errorMsg.setText("Internal error. Please contact support.");
            LOGGER.log(Level.SEVERE, "JDBC Driver not found: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            errorMsg.setText("Unexpected error occurred. Please try again.");
            LOGGER.log(Level.SEVERE, "Unexpected error during registration: " + ex.getMessage(), ex);
        }
    }*/

    private void registerUser() {
        String role = roleUser.isSelected() ? "User" : "Organizer";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Establish a connection
            connection = Db_Connect.getConnection();

            // Disable auto-commit for manual transaction control
            connection.setAutoCommit(false);

            // Prepare the SQL statement
            statement = connection.prepareStatement(
                    "INSERT INTO Users (name, email, username, address, phone, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)");

            String name = regName.getText().trim();
            String email = regEmail.getText().trim();
            String username = regUserName.getText().trim();
            String address = regAddress.getText().trim();
            String phone = regPhone.getText().trim();
            String password = new String(regPwd.getPassword()).trim();

            // Set parameters for the prepared statement
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, username);
            statement.setString(4, address);
            statement.setString(5, phone);
            statement.setString(6, password);
            statement.setString(7, role);

            // Execute the update and get the number of affected rows
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                // Commit the transaction after successful insertion
                connection.commit();

                // Create a User object for the session
                Session.currentUser = new User(username, role, name, email, phone);

                // Log details to ensure session was set correctly
                System.out.println("\nUser Registered and Session Set:");
                System.out.println("ID: " + username);
                System.out.println("Role: " + role);
                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Phone: " + phone);

                // Redirect to Screen1
                mainFrame.getScreenManager().showPanel("Screen1");
                mainFrame.revalidate();
                mainFrame.repaint();

            } else {
                // Rollback in case of failure
                connection.rollback();
                JOptionPane.showMessageDialog(this, "Registration failed!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            // Handle SQL exceptions and rollback in case of an error
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    LOGGER.log(Level.SEVERE, "Failed to rollback transaction", rollbackEx);
                }
            }
            errorMsg.setText("Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error during registration: " + ex.getMessage(), ex);

        } catch (ClassNotFoundException ex) {
            errorMsg.setText("Internal error. Please contact support.");
            LOGGER.log(Level.SEVERE, "JDBC Driver not found: " + ex.getMessage(), ex);

        } finally {
            // Ensure resources are properly closed
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, "Failed to close statement", ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, "Failed to close connection", ex);
                }
            }
        }
    }



}
