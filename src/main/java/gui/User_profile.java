package gui;

import app.Main;
import utility.Utility;

import javax.swing.*;
import java.awt.*;

public class User_profile extends JPanel {
    private JPanel mainJpanel;
    private JPanel menuBar;
    private JLabel menuBarspace;
    private JPanel userProfile;
    private JLabel userDetails;
    private JPanel details;
    private JTextField nameField;
    private JTextField usernameField;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField phoneNumberField;
    private JButton updateBtn;
    private JButton cancelBtn;
    private JPanel buttons;

    private Main mainFrame;

    public User_profile(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);
        setLayout(new BorderLayout());
        add(mainJpanel);

    }

}
