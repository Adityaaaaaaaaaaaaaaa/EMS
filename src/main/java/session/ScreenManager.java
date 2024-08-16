package session;

import app.Main;
import gui.Login_form;
import xxxgui.Screen1;

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
            case "Login_form":
                return new Login_form(mainFrame);
            case "Screen1":
                return new Screen1(mainFrame);
            // Add more cases as needed
            default:
                return null; // Handle unknown screens
        }
    }
}
