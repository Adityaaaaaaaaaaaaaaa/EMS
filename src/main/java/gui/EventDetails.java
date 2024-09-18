package gui;

import app.Main;
import utility.Utility;
import utility.MenuInterface;

import javax.swing.*;
import java.awt.*;

public class EventDetails extends JPanel implements MenuInterface {
    private JPanel main;
    private JPanel heading;
    private JPanel subMain;
    private JPanel wedding;
    private JPanel Birthday;
    private JPanel Conference;
    private JPanel Title;
    private JPanel wedDes_outdoor;
    private JPanel wedPackage_ourdoor;
    private JPanel wedCost_outdoor;
    private JPanel wedOutdoor;
    private JPanel wedIndoor;
    private JPanel title2;
    private JPanel birthdayOutdoor;
    private JPanel birthIndoor;
    private JPanel title3;
    private JPanel conferenceIndoor;
    private JPanel wedDes_indoor;
    private JPanel wedPackage_indoor;
    private JPanel wedCost_indoor;
    private JPanel birthdayDes_outdoor;
    private JPanel birthdayPackage_outdoor;
    private JPanel birthdayCost_outdoor;
    private JPanel birthdayDes_indoor;
    private JPanel birthdayPackage_indoor;
    private JPanel birthdayCost_indoor;
    private JPanel confDes_indoor;
    private JPanel confPackage_indoor;
    private JPanel confCost_indoor;
    private JMenuBar menuBar;


    private Main mainFrame;

    public EventDetails(Main mainFrame) {
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

