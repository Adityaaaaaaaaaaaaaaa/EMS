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

        // Register screens
        registerScreen("Login_form", new Login_form(mainFrame));
        registerScreen("Screen1", new Screen1(mainFrame)); //test do not delete yet
        registerScreen("Register_form", new Register_form(mainFrame));
        registerScreen("Organizer_profile", new Organizer_profile(mainFrame));
        registerScreen("User_profile", new User_profile(mainFrame));
        registerScreen("EventReservation", new EventReservation(mainFrame));
        // Add more screens as needed
    }

    public JPanel getScreen(String name) {
        return screens.get(name);
    }

    public void registerScreen(String name, JPanel panel) {
        screens.put(name, panel);
        mainPanel.add(panel, name);
    }

    public void showPanel(String name) {
        if (!screens.containsKey(name)) {
            JPanel panel = createScreen(name); // Create screen lazily
            if (panel != null) {
                registerScreen(name, panel);
            }
        }
        cardLayout.show(mainPanel, name);
    }


    private JPanel createScreen(String name) {
        return switch (name) {
            case "Login_form" -> new Login_form(mainFrame);
            case "Screen1" -> new Screen1(mainFrame);
            case "Register_form" -> new Register_form(mainFrame);
            case "Organizer_profile" -> new Organizer_profile(mainFrame);
            case "User_profile" -> new User_profile(mainFrame);
            case "EventReservation" -> new EventReservation(mainFrame);
            // Add more cases as needed
            default -> null;// Handle unknown screens
        };
    }
}
