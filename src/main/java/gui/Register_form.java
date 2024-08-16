package gui;

import app.Main;
import db.Db_Connect;
import utility.Utility;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Register_form extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(Register_form.class.getName());

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

        // Clear error message when user starts typing in the fields
        addFieldListeners();

        // Action listeners
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    registerUser();
                }
            }
        });

        btnBack.addActionListener(e -> mainFrame.getScreenManager().showPanel("Login_form"));
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

        regName.getDocument().addDocumentListener(clearErrorListener);
        regEmail.getDocument().addDocumentListener(clearErrorListener);
        regUserName.getDocument().addDocumentListener(clearErrorListener);
        regAddress.getDocument().addDocumentListener(clearErrorListener);
        regPhone.getDocument().addDocumentListener(clearErrorListener);
        regPwd.getDocument().addDocumentListener(clearErrorListener);
    }

    // Validate user input
    private boolean validateInput() {
        if (regName.getText().trim().isEmpty() || regEmail.getText().trim().isEmpty() ||
                regUserName.getText().trim().isEmpty() || regAddress.getText().trim().isEmpty() ||
                regPhone.getText().trim().isEmpty() || new String(regPwd.getPassword()).trim().isEmpty()) {
            errorMsg.setText("All fields are required.");
            return false;
        }

        // Check if a role is selected
        if (!roleUser.isSelected() && !roleOrganiser.isSelected()) {
            errorMsg.setText("Please select a role.");
            return false;
        }

        return true;
    }

    // Clear error message
    private void clearErrorMessage() {
        errorMsg.setText("");
    }

    // Register user in the database
    private void registerUser() {
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
                //JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                mainFrame.getScreenManager().showPanel("Screen1");
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
    }
}
