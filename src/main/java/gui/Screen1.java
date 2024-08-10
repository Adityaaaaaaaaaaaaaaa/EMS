package gui;

import utility.Utility;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen1 extends JFrame {
    private JButton button1;
    private JPanel screen1;

    public Screen1() {
        setTitle("Main Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Utility.setWindowSize(this);
        setContentPane(screen1);
        pack();
        setLocationRelativeTo(null); // Center the window

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Screen2 and close Screen1
                Screen2 screen2 = new Screen2(); // Create Screen2 instance
                Utility.setWindowSize(screen2); // Set window size for Screen2
                dispose();
            }
        });

        setVisible(true);
    }
}
