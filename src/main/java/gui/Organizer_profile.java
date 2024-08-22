package gui;

import app.Main;
import utility.Utility;

import javax.swing.*;
import java.awt.*;

public class Organizer_profile extends JPanel {
    private JPanel main_panel;
    private JPanel Menu;

    private Main mainFrame;

    public Organizer_profile(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);
        setLayout(new BorderLayout());
        add(main_panel);
    }

    private JTextField OrgName;
    private JTextField OrgUname;
    private JTextField OrgEmail;
    private JTextField OrgPwd;
    private JTextField OrgAdd;
    private JTextField textField1;
    private JLabel OrgPhoneNum;
}
