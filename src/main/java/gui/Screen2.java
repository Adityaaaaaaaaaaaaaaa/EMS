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
    private JButton button3;

    private Main mainFrame;

    public Screen2(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        add(screen2);

        // Action listener for the button
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getScreenManager().showPanel("Screen1"); // Show Screen2
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getScreenManager().showPanel("Screen3");
            }
        });
    }
}

