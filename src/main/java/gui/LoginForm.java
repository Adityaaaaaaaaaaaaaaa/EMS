package gui;

import db.Db_Connect;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JFrame {
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private JPanel mainPane;

    public LoginForm() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the application terminates when the window is closed
        setContentPane(mainPane);
        pack(); // Automatically sizes the window based on content
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
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this, "Invalid email or password"
                    , "Error", JOptionPane.ERROR_MESSAGE);
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

    public static User user;

    private User authenticateUser(String email, String password) {
        user = null;

        try {
            // Get a connection from the Db_Connect class
            Connection connection = Db_Connect.getConnection();
            System.out.println("Connected to the database successfully!");

            // SQL query to check for matching credentials
            String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                // Retrieve user details from the database
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

            // Close the resources
            resultSet.close();
            statement.close();
            Db_Connect.close(connection); // Close the connection using the Db_Connect class
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Database connection failed: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("MySQL JDBC Driver not found: " + ex.getMessage());
        }
        return user;
    }


    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm();
        User user = LoginForm.user;
    }
}