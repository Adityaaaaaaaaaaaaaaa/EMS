package gui;

import db.Db_Connect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginForm extends JFrame {
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private JPanel mainPane;

    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 300;

    private static final Logger LOGGER = Logger.getLogger(LoginForm.class.getName());

    public static User user;

    public LoginForm() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the application terminates when the window is closed
        setContentPane(mainPane);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // Set consistent window size
        setLocationRelativeTo(null); // Center the window

        // Action listener for the Login button
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform login
                String email = txtEmail.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();

                // Authenticate user and retrieve additional details
                user = authenticateUser(email, password);
                if (user != null) {
                    // Open the main screen
                    Screen1 screen1 = new Screen1();
                    screen1.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // Set the size to match other screens
                    dispose(); // Dispose of the login form
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this, "Invalid email or password",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for the Cancel button
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the application
                dispose(); // Dispose of the JFrame and free up resources
            }
        });

        setVisible(true);
    }

    private User authenticateUser(String email, String password) {
        user = null;

        try {
            Connection connection = Db_Connect.getConnection();
            System.out.println("Connected to the database successfully!");

            String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.name = resultSet.getString("name");
                user.phone = resultSet.getString("phone");
                user.address = resultSet.getString("address");
                user.email = email;
                user.password = password;

                System.out.println("Name: " + user.name);
                System.out.println("Email: " + email);
                System.out.println("Phone: " + user.phone);
                System.out.println("Address: " + user.address);
            } else {
                System.out.println("No matching user found.");
            }

            resultSet.close();
            statement.close();
            Db_Connect.close(connection);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Database connection failed: " + ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "MySQL JDBC Driver not found: " + ex.getMessage(), ex);
        }
        return user;
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}
