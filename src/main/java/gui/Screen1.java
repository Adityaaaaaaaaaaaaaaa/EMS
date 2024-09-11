package gui;

import app.Main;
import session.Session;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import java.awt.BorderLayout;

public class Screen1 extends JPanel implements MenuInterface {
    private JButton doNotClickButton;
    private JPanel screen1;
    private JButton logoutButton;
    private JButton btnOrgPf;
    private JButton btnUsrPf;
    private JLabel testHere;
    private JButton btnPayment;
    private JMenuBar menuBar;


    private Main mainFrame;

    private static final String DEFAULT_TEXT = "Welcome to the Event Management System"; // Default text for the label

    public Screen1(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        add(screen1, BorderLayout.CENTER);


        // Set default label text
        testHere.setText(DEFAULT_TEXT);

        // Hide profile buttons initially
        btnUsrPf.setVisible(false);
        btnOrgPf.setVisible(false);

        // Create a menu bar and initialize it with the menu items and listeners
        menuBar = new JMenuBar();
        initializeMenu(menuBar, mainFrame, screen1.getBackground(), screen1.getForeground());

        // Add the menu bar to the panel
        add(menuBar, BorderLayout.NORTH);

        // Action listener for "doNotClickButton" to navigate based on role
        doNotClickButton.addActionListener(e -> navigateBasedOnRole());

        // Action listener for logging out
        logoutButton.addActionListener(e -> handleLogout());

        // Action listener for payment button
        btnPayment.addActionListener(e -> mainFrame.getScreenManager().showPanel("EventReservation"));
    }

    // Method to handle navigation based on role when "doNotClickButton" is clicked
    private void navigateBasedOnRole() {
        if (Session.currentUser != null) {
            String role = Session.currentUser.getRole();
            String name = Session.currentUser.getName();

            System.out.println("Navigating based on role - Role: " + role + ", Name: " + name);

            switch (role) {
                case "User" -> {
                    testHere.setText(String.format("User %s is currently logged in", name));
                    mainFrame.getScreenManager().showPanel("User_profile");
                }
                case "Organizer" -> {
                    testHere.setText(String.format("Organizer %s is currently logged in", name));
                    mainFrame.getScreenManager().showPanel("Organizer_profile");
                }
                default -> {
                    testHere.setText(DEFAULT_TEXT);
                    System.out.println("No valid role found.");
                }
            }
        } else {
            System.out.println("No user session found.");
            testHere.setText(DEFAULT_TEXT);
        }

        // Force UI refresh to reflect any changes
        this.revalidate();
        this.repaint();
    }

    // Method to handle logout
    private void handleLogout() {
        if (Session.currentUser != null) {
            System.out.println("Logging out user: " + Session.currentUser.getId());
        }

        // Clear session and reset profile fields
        Session.currentUser = null;

        User_profile userProfile = mainFrame.getScreenManager().getUserProfile();
        Organizer_profile organizerProfile = mainFrame.getScreenManager().getOrganizerProfile();

        if (userProfile != null) {
            userProfile.clearFields();
        }

        if (organizerProfile != null) {
            organizerProfile.clearFields();
        }

        testHere.setText(DEFAULT_TEXT); // Reset label text
        mainFrame.getScreenManager().showPanel("Login_form");

        // Revalidate and repaint to ensure UI is updated
        mainFrame.revalidate();
        mainFrame.repaint();
        System.out.println("Session after pressing logout: " + Session.currentUser);
    }
}
