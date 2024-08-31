package gui;

import app.Main;
import session.Session;

import javax.swing.*;
import java.awt.*;

public class Screen1 extends JPanel {
    private JButton doNotClickButton;
    private JPanel screen1;
    private JButton logoutButton;
    private JButton btnOrgPf;
    private JButton btnUsrPf;
    private JLabel testHere;

    private Main mainFrame;

    private static final String DEFAULT_TEXT = "Welcome to the Event Management System"; // Default text for the label

    public Screen1(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        add(screen1);

        testHere.setText(DEFAULT_TEXT);

        if (Session.currentUser != null) {
            System.out.println("Session Status:");
            System.out.println("ID: " + Session.currentUser.getId());
            System.out.println("Role: " + Session.currentUser.getRole());
        } else {
            System.out.println("No user in session.");
        }

        // Action listener for navigating to Screen2
        doNotClickButton.addActionListener(e -> {
            //mainFrame.getScreenManager().showPanel("Screen2");
            // do not remove
            String role = Session.currentUser != null ? Session.currentUser.getRole() : "";
            assert Session.currentUser != null;
            String name = Session.currentUser.getName();

            switch (role) {
                case "Admin" -> {
                    // Show admin-specific components
                    System.out.println("Admin is currently logged in");
                    testHere.setText(String.format("Admin %s is currently logged innnnnnnnnnn", name));
                }
                case "User" -> {
                    // Show user-specific components
                    System.out.println("User is currently logged in");
                    testHere.setText(String.format("User %s is currently logged innnnnnnnnnn", name));
                }
                case "Organizer" -> {
                    // Show organizer-specific components
                    System.out.println("Organizer is currently logged in");
                    testHere.setText(String.format("Organiser %s is currently logged innnnnnnnnnn", name));
                }
                default -> {
                    // Handle unknown or not logged-in case
                    System.out.println("Who are you ???? How did you reach here???");
                }
            }


        });

        // Action listener for logging out
        logoutButton.addActionListener(e -> {
            if (Session.currentUser != null) {
                System.out.println("before pressing Log out user: " + Session.currentUser.getId());
            }
            Session.currentUser = null; // Clear the session
            testHere.setText(DEFAULT_TEXT);
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
