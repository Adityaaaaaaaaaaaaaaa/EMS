package app;

import session.ScreenManager;
import utility.Utility;

import javax.swing.*;

public class Main extends JFrame {
    private ScreenManager screenManager;

    public Main() {
        screenManager = new ScreenManager(this);
        Utility.setWindowSize(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Defer showing the panel until after the GUI is fully initialized
        SwingUtilities.invokeLater(() -> screenManager.showPanel("Login_form"));
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
