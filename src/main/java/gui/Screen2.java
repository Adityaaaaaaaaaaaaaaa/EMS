package gui;

import app.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen2 extends JPanel {
    private JCheckBox checkBox1;
    private JRadioButton radioButton1;
    private JComboBox comboBox1;
    private JTable table1;
    private JList list1;
    private JButton button2;
    private JPanel screen2;

    private Main mainFrame; // Declare the mainFrame variable

    public Screen2(Main mainFrame) {
        this.mainFrame = mainFrame; // Assign the passed Main instance to the mainFrame variable
        setLayout(new BorderLayout());
        add(screen2);

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("Screen1"); // Show Screen2
            }
        });
    }
}

