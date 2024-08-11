package app;

import gui.LoginForm;
import gui.Screen1;
import gui.Screen2;
import utility.Utility;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public Main() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add panels to the CardLayout
        mainPanel.add(new LoginForm(this), "LoginForm");
        mainPanel.add(new Screen1(this), "Screen1");
        mainPanel.add(new Screen2(this), "Screen2");

        // Set up the main frame
        add(mainPanel);
        Utility.setWindowSize(this); // Use your Utility method to set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public static void main(String[] args) {
        new Main();
    }
}
