package gui;

import app.Main;
import db.Db_Connect;
import utility.MenuInterface;
import utility.Utility;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Event_Location extends JPanel implements MenuInterface {
    private JPanel mainLocation;
    private JPanel locationDetails;
    private JPanel indoor1;
    private JPanel conference1;
    private JPanel outdoor1;
    private JScrollPane scroll1;
    private JMenuBar menuBar;

    private Main mainFrame;
    private static final Logger LOGGER = Logger.getLogger(Event_Location.class.getName());

    public Event_Location(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);

        setLayout(new BorderLayout());

        menuBar = new JMenuBar();
        initializeMenu(menuBar, mainFrame, mainLocation.getBackground(), mainLocation.getForeground());
        menuBar.setVisible(false);
        add(menuBar, BorderLayout.NORTH);

        scroll1 = new JScrollPane(locationDetails);
        scroll1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scroll1, BorderLayout.CENTER);

        indoor1.setLayout(null);
        outdoor1.setLayout(null);
        conference1.setLayout(null);

        fetchAndDisplayDataForIndoor();
        fetchAndDisplayDataForOutdoor();
        fetchAndDisplayDataForConference();
    }

    private void fetchAndDisplayDataForIndoor() {
        String query = "SELECT * FROM Location WHERE Address = ?";
        String address = "Chateau Mon Desir, Balaclava, Mauritius";

        try (Connection conn = Db_Connect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, address);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                displayDataInPanel(rs, indoor1);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Database error while fetching indoor data: " + ex.getMessage(), ex);
        }
    }

    private void fetchAndDisplayDataForOutdoor() {
        String query = "SELECT * FROM Location WHERE Address = ?";
        String address = "Pamplemousses Botanical Garden, Pamplemousses, Mauritius";

        try (Connection conn = Db_Connect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, address);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                displayDataInPanel(rs, outdoor1);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Database error while fetching outdoor data: " + ex.getMessage(), ex);
        }
    }

    private void fetchAndDisplayDataForConference() {
        String query = "SELECT * FROM Location WHERE Address = ?";
        String address = "Caudan Arts Centre, Port Louis, Mauritius";

        try (Connection conn = Db_Connect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, address);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                displayDataInPanel(rs, conference1);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Database error while fetching conference data: " + ex.getMessage(), ex);
        }
    }


    private void displayDataInPanel(ResultSet rs, JPanel panel) throws SQLException {
        panel.removeAll();

        int componentWidth = 220;
        int componentHeight = 20;
        int verticalSpacing = 5;

        Color textColor = Color.decode("#34542A");

        JTextArea nameArea;
        JTextArea descriptionArea;
        JTextArea addressArea;
        JLabel attendeesLabel;

        if (panel == indoor1 || panel == outdoor1) {
            nameArea = new JTextArea("Name: " + rs.getString("Name"));
            nameArea.setWrapStyleWord(true);
            nameArea.setLineWrap(true);
            nameArea.setOpaque(false);
            nameArea.setEditable(false);
            nameArea.setFocusable(false);
            nameArea.setFont(new Font("Arial", Font.BOLD, 14));
            nameArea.setForeground(textColor);
            nameArea.setBounds(10, 10, componentWidth, 40);
        } else {
            nameArea = new JTextArea("Name: " + rs.getString("Name"));
            nameArea.setWrapStyleWord(false);
            nameArea.setLineWrap(false);
            nameArea.setOpaque(false);
            nameArea.setEditable(false);
            nameArea.setFocusable(false);
            nameArea.setFont(new Font("Arial", Font.BOLD, 14));
            nameArea.setForeground(textColor);
            nameArea.setBounds(10, 10, componentWidth, componentHeight);
        }

        if (panel == outdoor1 || panel == conference1) {
            descriptionArea = new JTextArea("Description: " + rs.getString("Description"));
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setLineWrap(true);
            descriptionArea.setOpaque(false);
            descriptionArea.setEditable(false);
            descriptionArea.setFocusable(false);
            descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));
            descriptionArea.setForeground(textColor);
            descriptionArea.setBounds(10, 55, componentWidth, 40);
        } else {
            descriptionArea = new JTextArea("Description: " + rs.getString("Description"));
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setLineWrap(true);
            descriptionArea.setOpaque(false);
            descriptionArea.setEditable(false);
            descriptionArea.setFocusable(false);
            descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));
            descriptionArea.setForeground(textColor);
            descriptionArea.setBounds(10, 55, componentWidth, 60);
        }

        if (panel == outdoor1 || panel == conference1) {
            addressArea = new JTextArea("Address: " + rs.getString("Address"));
            addressArea.setWrapStyleWord(true);
            addressArea.setLineWrap(true);
            addressArea.setOpaque(false);
            addressArea.setEditable(false);
            addressArea.setFocusable(false);
            addressArea.setFont(new Font("Arial", Font.PLAIN, 12));
            addressArea.setForeground(textColor);
            addressArea.setBounds(10, 100, componentWidth, 40);
        } else {
            addressArea = new JTextArea("Address: " + rs.getString("Address"));
            addressArea.setWrapStyleWord(true);
            addressArea.setLineWrap(true);
            addressArea.setOpaque(false);
            addressArea.setEditable(false);
            addressArea.setFocusable(false);
            addressArea.setFont(new Font("Arial", Font.PLAIN, 12));
            addressArea.setForeground(textColor);
            addressArea.setBounds(10, 120, componentWidth, 50);
        }

        attendeesLabel = new JLabel("Attendees: " + rs.getString("Attendees"));
        attendeesLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        attendeesLabel.setForeground(textColor);

        if (panel == outdoor1 || panel == conference1) {
            attendeesLabel.setBounds(10, 100 + 40 + verticalSpacing, componentWidth, componentHeight);
        } else {
            attendeesLabel.setBounds(10, 165, componentWidth, componentHeight);
        }

        panel.add(nameArea);
        panel.add(descriptionArea);
        panel.add(addressArea);
        panel.add(attendeesLabel);

        panel.revalidate();
        panel.repaint();
    }


}
