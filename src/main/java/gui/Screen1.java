package gui;

import app.Main;
import session.Session;

import javax.swing.*;
import java.awt.*;

public class Screen1 extends JPanel {
    private JButton button1;
    private JPanel screen1;
    private JButton logoutButton;
    private JButton btnOrgPf;
    private JButton btnUsrPf;

    private Main mainFrame;

    public Screen1(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        add(screen1);

        if (Session.currentUser != null) {
            System.out.println("Session Status:");
            System.out.println("ID: " + Session.currentUser.getId());
            System.out.println("Role: " + Session.currentUser.getRole());
        } else {
            System.out.println("No user in session.");
        }

        // do not remove
        String role = Session.currentUser != null ? Session.currentUser.getRole() : "";
        switch (role) {
            case "Admin" -> {
                // Show admin-specific components
            }
            case "User" -> {
                // Show user-specific components
            }
            case "Organizer" -> {
                // Show organizer-specific components
            }
            default -> {
                // Handle unknown or not logged-in case
            }
        }

        // Action listener for navigating to Screen2
        button1.addActionListener(e -> mainFrame.getScreenManager().showPanel("Screen2"));

        // Action listener for logging out
        logoutButton.addActionListener(e -> {
            if (Session.currentUser != null) {
                System.out.println("before pressing Log out user: " + Session.currentUser.getId());
            }
            Session.currentUser = null; // Clear the session
            mainFrame.getScreenManager().showPanel("Login_form"); // Show login screen

            // Revalidate and repaint to ensure components are updated
            mainFrame.revalidate();
            mainFrame.repaint();
            System.out.println("Session after pressing logout: " + Session.currentUser);
        });

        btnOrgPf.addActionListener(e -> mainFrame.getScreenManager().showPanel("Organizer_profile"));

        btnUsrPf.addActionListener(e -> mainFrame.getScreenManager().showPanel("User_profile"));
    }
}
