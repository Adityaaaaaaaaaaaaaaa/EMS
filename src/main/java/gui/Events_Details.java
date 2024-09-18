package gui;

import app.Main;
import utility.MenuInterface;
import utility.Utility;

import javax.swing.*;
import java.awt.*;

public class Events_Details extends JPanel implements MenuInterface {
    private JPanel main;
    private JPanel subMain;
    private JPanel events;
    private JLabel Wedding;
    private JPanel wedding_outdoor;
    private JPanel wedding_indoor;
    private JLabel birthday;
    private JPanel birthday_outdoor;
    private JPanel birthday_indoor;
    private JPanel conference_inddor;
    private JPanel wedOutdoorInfo;
    private JPanel wedIndoorInfo;
    private JPanel birthdayOutdoorInfo;
    private JPanel birthdayIndoorInfo;
    private JPanel conferenceIndoorInfo;
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