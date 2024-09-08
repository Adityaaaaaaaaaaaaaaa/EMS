package gui;


import app.Main;
import utility.Utility;

import javax.swing.JPanel;
import javax.swing.JMenuBar;
import java.awt.BorderLayout;

public class Home extends JPanel implements MenuInterface{

    private JPanel homePanel;
    private JMenuBar menuBar;

    private Main mainFrame;

    public Home(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);
        setLayout(new BorderLayout());
        add(homePanel, BorderLayout.CENTER);

        // Create a menu bar and initialize it with the menu items and listeners
        menuBar = new JMenuBar();
        initializeMenu(menuBar, mainFrame, homePanel.getBackground(), homePanel.getForeground());

        // Add the menu bar to the panel
        add(menuBar, BorderLayout.NORTH);
    }
}



