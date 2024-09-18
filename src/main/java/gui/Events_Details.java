package gui;

import app.Main;
import utility.MenuInterface;
import utility.Utility;

import javax.swing.*;
import java.awt.*;

public class Events_Details extends JPanel implements MenuInterface {
    private JPanel main;
    private JMenuBar menuBar;


    private Main mainFrame;

    public Events_Details(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);
        setLayout(new BorderLayout());
        add(main, BorderLayout.CENTER);

        // Create a menu bar and initialize it with the menu items and listeners
        menuBar = new JMenuBar();
        initializeMenu(menuBar, mainFrame, main.getBackground(), main.getForeground());

        // Add the menu bar to the panel
        add(menuBar, BorderLayout.NORTH);
    }
}
