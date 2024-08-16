package gui;

import app.Main;
import session.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen1 extends JPanel {
    private JButton button1;
    private JPanel screen1;
    private JButton logoutButton;

    private Main mainFrame;

    public Screen1(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        add(screen1);

        // Check user role before accessing it
        String role = (Session.currentUser != null) ? Session.currentUser.getRole() : "Unknown";

        // Example of showing/hiding components based on user role
        if (role.equals("Admin")) {
            // Show admin-specific components
        } else if (role.equals("User")) {
            // Show user-specific components
        } else if (role.equals("Organizer")) {
            // Show organizer-specific components
        } else {
            // Handle unknown or not logged-in case
        }

        // Action listener for the button
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getScreenManager().showPanel("Screen2"); // Show Screen2
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getScreenManager().showPanel("Login_form"); // Show Login_form
            }
        });
    }
}
