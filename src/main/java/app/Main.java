package app;

import session.ScreenManager;
import utility.Utility;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.util.Objects;

public class Main extends JFrame {
    private final ScreenManager screenManager;
    private final JMenuBar menuBar;

    public Main() {
        screenManager = new ScreenManager(this);
        Utility.setWindowSize(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setTitle("Evenia Event Management System");
        Image icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("image/logo_icon.png"))).getImage();
        setIconImage(icon);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        Utility.setCursorToPointer(menuBar);

        SwingUtilities.invokeLater(() -> screenManager.showPanel("Login_form"));
    }

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
