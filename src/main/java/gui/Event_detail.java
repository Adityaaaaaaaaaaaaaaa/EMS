package gui;

import app.Main;
import utility.MenuInterface;
import utility.Utility;

import javax.swing.JPanel;
import javax.swing.JMenuBar;
import java.awt.BorderLayout;

public class Event_detail extends JPanel implements MenuInterface {
    private JMenuBar menuBar;
    private Main mainFrame;
    private JPanel panel1;

    public Event_detail(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);
        setLayout(new BorderLayout());
        add(panel1, BorderLayout.CENTER);

        menuBar = new JMenuBar();
        initializeMenu(menuBar, mainFrame, panel1.getBackground(), panel1.getForeground());
        menuBar.setVisible(false);
        add(menuBar, BorderLayout.NORTH);

    }
}