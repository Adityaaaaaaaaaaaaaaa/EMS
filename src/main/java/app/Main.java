package app;

import session.ScreenManager;
import utility.Utility;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main extends JFrame {
    private final ScreenManager screenManager;

    public Main() {
        screenManager = new ScreenManager(this);
        Utility.setWindowSize(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        SwingUtilities.invokeLater(() -> screenManager.showPanel("Login_form"));
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
