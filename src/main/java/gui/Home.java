package gui;

import app.Main;
import utility.MenuInterface;
import utility.Utility;

import javax.swing.*;
import java.awt.BorderLayout;

public class Home extends JPanel implements MenuInterface {
    private JPanel main;
    private JMenuBar menuBar;

    private Main mainFrame;

    public Home(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);
        setLayout(new BorderLayout());
        add(main, BorderLayout.CENTER);

        menuBar = new JMenuBar();
        initializeMenu(menuBar, mainFrame, main.getBackground(), main.getForeground());
        menuBar.setVisible(false);
        add(menuBar, BorderLayout.NORTH);
    }
}
