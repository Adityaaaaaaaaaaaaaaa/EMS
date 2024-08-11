package gui;

import app.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen1 extends JPanel { // Extending JPanel instead of JFrame
    private JButton button1;
    private JPanel screen1;

    private Main mainFrame; // Declare the mainFrame variable

    public Screen1(Main mainFrame) {
        this.mainFrame = mainFrame; // Assign the passed Main instance to the mainFrame variable
        setLayout(new BorderLayout());
        add(screen1);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("Screen2"); // Show Screen2
            }
        });
    }
}
