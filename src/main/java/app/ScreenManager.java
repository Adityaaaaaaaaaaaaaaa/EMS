package app;

import gui.LoginForm;
import gui.Screen1;
import gui.Screen2;
import gui.Screen3;

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
        switch (name) {
            case "LoginForm":
                return new LoginForm(mainFrame);
            case "Screen1":
                return new Screen1(mainFrame);
            case "Screen2":
                return new Screen2(mainFrame);
            case "Screen3":
                return new Screen3(mainFrame);
            // Add more cases as needed
            default:
                return null; // Handle unknown screens
        }

        /* Enhanced switch case loop
        return switch (name) {
            case "LoginForm" -> new LoginForm(mainFrame);
            case "Screen1" -> new Screen1(mainFrame);
            case "Screen2" -> new Screen2(mainFrame);
            case "Screen3" -> new Screen3(mainFrame);
            // Add more cases as needed
            default -> null; // Handle unknown screens
        };*/
    }
}
