package xxxgui;

import app.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen1 extends JPanel {
    private JButton button1;
    private JPanel screen1;
    private JButton logoutButton;

    private Main mainFrame;

    public Screen1(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        add(screen1);

        // Action listener for the button
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getScreenManager().showPanel("Screen2"); // Show Screen2
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getScreenManager().showPanel("LoginForm");
            }
        });
    }
}
