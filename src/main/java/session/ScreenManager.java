package session;

import app.Main;
import gui.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ScreenManager {
    private Main mainFrame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Map<String, JPanel> screens;

    public ScreenManager(Main mainFrame) {
        this.mainFrame = mainFrame;
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
        this.screens = new HashMap<>();

        this.mainFrame.setContentPane(mainPanel);

        // Register initial screens
        registerScreen("Login_form", new Login_form(mainFrame));
        registerScreen("Screen1", new Screen1(mainFrame));
        registerScreen("Register_form", new Register_form(mainFrame));
        // Other screens will be created lazily
    }

    public User_profile getUserProfile() {
        return (User_profile) screens.get("User_profile");
    }

    public Organizer_profile getOrganizerProfile() {
        return (Organizer_profile) screens.get("Organizer_profile");
    }


    public JPanel getScreen(String name) {
        return screens.get(name);
    }

    public void registerScreen(String name, JPanel panel) {
        screens.put(name, panel);
        mainPanel.add(panel, name);
    }

    public void showPanel(String name) {
        // Validate session before showing certain panels
        if ((name.equals("Organizer_profile") || name.equals("User_profile")) && Session.currentUser == null) {
            JOptionPane.showMessageDialog(mainFrame, "Please log in to access this feature.", "Session Error", JOptionPane.ERROR_MESSAGE);
            name = "Login_form"; // Redirect to login form
        }

        // Create screen lazily if it doesn't exist
        if (!screens.containsKey(name)) {
            JPanel panel = createScreen(name);
            if (panel != null) {
                registerScreen(name, panel);
            } else {
                return; // Prevents showing an invalid or uninitialized panel
            }
        }
        cardLayout.show(mainPanel, name);
    }

    private JPanel createScreen(String name) {
        switch (name) {
            case "Organizer_profile" -> {
                if (Session.currentUser != null) {
                    return new Organizer_profile(mainFrame);
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Please log in first.");
                    return null;
                }
            }
            case "User_profile" -> {
                if (Session.currentUser != null) {
                    return new User_profile(mainFrame);
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Please log in first.");
                    return null;
                }
            }
            case "EventReservation" -> {
                return new EventReservation(mainFrame);
            }
            case "Login_form" -> {
                return new Login_form(mainFrame);
            }
            case "Screen1" -> {
                return new Screen1(mainFrame);
            }
            case "Register_form" -> {
                return new Register_form(mainFrame);
            }
            case "Home" -> {
                return new Home(mainFrame);
            }
            default -> {
                return null; // Handle unknown screens
            }
        }
    }
}
