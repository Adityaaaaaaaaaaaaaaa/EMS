package gui;

import app.Main;
import utility.MenuInterface;
import utility.Utility;

import javax.swing.*;
import java.awt.*;

public class Event_detail extends JPanel implements MenuInterface {
    private JPanel main;
    private JMenuBar menuBar;


    private Main mainFrame;
    private JPanel panel1;
    private JPanel details;
    private JPanel conference;
    private JPanel confImg;
    private JPanel conferenceInfo;
    private JPanel wedding;
    private JPanel indoorWedding;
    private JPanel indoorwedImg;
    private JPanel outdoorBirthday;
    private JPanel outdoorBirthdayImg;
    private JPanel outdoorBirthdayDetails;
    private JPanel subJpanel;
    private JPanel indoorBirthday;

    public Event_detail(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);
        setLayout(new BorderLayout());
        add(panel1, BorderLayout.CENTER);

        // Create a menu bar and initialize it with the menu items and listeners
        menuBar = new JMenuBar();
        initializeMenu(menuBar, mainFrame, panel1.getBackground(), panel1.getForeground());
        menuBar.setVisible(false);
        add(menuBar, BorderLayout.NORTH);

    }
}