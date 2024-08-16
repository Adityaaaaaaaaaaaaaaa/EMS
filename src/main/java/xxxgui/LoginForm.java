package xxxgui;

import app.Main;
import db.Db_Connect;
import utility.Utility;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginForm extends JPanel {
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private JPanel mainPane;

    private static final Logger LOGGER = Logger.getLogger(LoginForm.class.getName());

    public static User user;
    private Main mainFrame;

    public LoginForm(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);
        setLayout(new BorderLayout());
        add(mainPane);

        // Action listeners
        btnLogin.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            User user = authenticateUser(email, password);
            if (user != null) {
                clearFields();
                mainFrame.getScreenManager().showPanel("Screen1");
            } else {
                JOptionPane.showMessageDialog(LoginForm.this, "Invalid email or password",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> System.exit(0));
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

    public void clearFields() {
        txtEmail.setText("");
        txtPassword.setText("");
    }
}
