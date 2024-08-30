package gui;

import app.Main;
import db.Db_Connect; // Ensure you have this import for your database connection class
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

public class Organizer_profile extends JPanel {
    private JPanel main_panel;
    private JPanel Menu;
    private JTextField OrgName;
    private JTextField OrgUname;
    private JTextField OrgEmail;
    private JTextField OrgAdd;
    private JTextField OrgPhoneNum;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnCancel;
    private JLabel phoneNumber;

    private static final Logger LOGGER = Logger.getLogger(Organizer_profile.class.getName());

    private Main mainFrame;
    private final String username = "Aliya";

    public Organizer_profile(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);
        setLayout(new BorderLayout());
        add(main_panel);

        // Fetch user data and populate fields
        fetchAndDisplayUserData(username);
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.getScreenManager().showPanel("Screen1"); // Replace with the actual previous screen
                mainFrame.revalidate();
                mainFrame.repaint();
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
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                OrgName.setText(rs.getString("name"));
                OrgUname.setText(rs.getString("username"));
                OrgEmail.setText(rs.getString("email"));
                OrgAdd.setText(rs.getString("address"));
                OrgPhoneNum.setText(rs.getString("phone"));

                // Make fields non-editable
                OrgName.setEditable(false);
                OrgUname.setEditable(false);
                OrgEmail.setEditable(false);
                OrgAdd.setEditable(false);
                OrgPhoneNum.setEnabled(false); // Since it's a JLabel, not a JTextField, disable it
            } else {
                JOptionPane.showMessageDialog(this, "User not found!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Database error while fetching user data: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(this, "Database error. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
