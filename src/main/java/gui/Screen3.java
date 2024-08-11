package gui;

import app.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen3 extends JPanel {
    private JPanel Screen3;
    private JTabbedPane tabbedPane1;
    private JButton goToScreen2Button;
    private JButton goToScreen1Button;

    private Main mainFrame;

    public Screen3(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        add(Screen3);

        // Action listeners for buttons
        goToScreen1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getScreenManager().showPanel("Screen1"); // Show Screen1
            }
        });

        goToScreen2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getScreenManager().showPanel("Screen2"); // Show Screen2
            }
        });

    }
}
