package app;

import session.ScreenManager;
import utility.Utility;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Main extends JFrame {
    private final ScreenManager screenManager;
    private final JMenuBar menuBar; // Centralized menu bar

    public Main() {
        screenManager = new ScreenManager(this);
        Utility.setWindowSize(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setTitle("Evenia Event Management System");
        // Load the image icon using the class loader
        Image icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("image/logo_icon.png"))).getImage();
        setIconImage(icon);

        // Initialize the menu bar and attach to the frame
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);  // Attach the menu bar to the frame

        Utility.setCursorToPointer(menuBar);

        // Start with the Login form
        SwingUtilities.invokeLater(() -> screenManager.showPanel("Login_form"));
    }

    // Getter method for the menuBar, used by MenuInterface and ScreenManager
    public JMenuBar getAppMenuBar() {
        return menuBar;
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
