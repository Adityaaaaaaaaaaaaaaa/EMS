package gui;

import app.Main;
import utility.MenuInterface;
import utility.Utility;

import javax.swing.*;
import java.awt.BorderLayout;

public class Home extends JPanel implements MenuInterface {
    private JPanel main;
    private JPanel welcomeMsg;
    private JPanel photos;
    private JPanel aboutUs;
    private JPanel fto1;
    private JPanel fto2;
    private JPanel fto3;
    private JMenuBar menuBar;

    private Main mainFrame;

    public Home(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);
        setLayout(new BorderLayout());
        add(main, BorderLayout.CENTER);

        // Create a menu bar and initialize it with the menu items and listeners
        menuBar = new JMenuBar();
        initializeMenu(menuBar, mainFrame, main.getBackground(), main.getForeground());
        menuBar.setVisible(false);
        add(menuBar, BorderLayout.NORTH);
    }
}
