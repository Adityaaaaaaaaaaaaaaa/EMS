package gui;

import utility.Utility;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen2 extends JFrame {
    private JCheckBox checkBox1;
    private JRadioButton radioButton1;
    private JComboBox comboBox1;
    private JTable table1;
    private JList list1;
    private JButton button2;
    private JPanel screen2;

    public Screen2() {
        setTitle("Screen 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Utility.setWindowSize(this); // Set window size using the utility
        setContentPane(screen2);
        pack();
        setLocationRelativeTo(null); // Center the window

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Screen1 screen1 = new Screen1(); // Create Screen1 instance
                Utility.setWindowSize(screen1); // Set window size for Screen1
                dispose();
            }
        });

        setVisible(true);
    }
}
