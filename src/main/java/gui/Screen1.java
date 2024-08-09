package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen1 extends JFrame {
    private JButton button1;
    private JPanel screen1;

    public Screen1() {
        setTitle("Main Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(screen1);
        pack();
        setLocationRelativeTo(null); // Center the window

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Screen1.this, "Button Clicked!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        setVisible(true);
    }
}
