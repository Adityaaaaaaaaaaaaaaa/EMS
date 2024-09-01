package gui;

import app.Main;
import utility.Utility;

import javax.swing.*;
import java.awt.*;

public class EventReservation extends JPanel{

    private JComboBox comboBox1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox comboBox2;
    private JPanel panel;
    private Main mainFrame;
 public EventReservation(Main mainFrame){
     this.mainFrame = mainFrame;
     Utility.setWindowSize(mainFrame);
     setLayout(new BorderLayout());
     add(panel);
 }
}
