package gui;

import app.Main;
import db.Db_Connect;
import utility.MenuInterface;
import utility.Utility;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Event_Location extends JPanel implements MenuInterface {
    private JPanel mainLocation;
    private JPanel locationDetails;
    private JPanel details;
    private JPanel indoor;
    private JPanel outdoor;
    private JPanel conference;
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

        // Create a menu bar and initialize it with the menu items and listeners
        menuBar = new JMenuBar();
        initializeMenu(menuBar, mainFrame, mainLocation.getBackground(), mainLocation.getForeground());

        // Add the menu bar to the panel
        add(menuBar, BorderLayout.NORTH);

        // Create a scroll pane to fit the window width and height
        scroll1 = new JScrollPane(locationDetails);
        scroll1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scroll1, BorderLayout.CENTER);  // Add scroll pane to the center of the window

        // Disable layout for indoor1, outdoor1, and conference1
        indoor1.setLayout(null);
        outdoor1.setLayout(null);
        conference1.setLayout(null);

        // Fetch and display data for indoor, outdoor, and conference venues
        fetchAndDisplayDataForIndoor();
        fetchAndDisplayDataForOutdoor();
        fetchAndDisplayDataForConference();
    }

    // Fetch and display data for indoor venue
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

    // Fetch and display data for outdoor venue
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

    // Fetch and display data for conference venue
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

    // Method to dynamically create and display data in the respective panel using absolute positioning
    private void displayDataInPanel(ResultSet rs, JPanel panel) throws SQLException {
        panel.removeAll();  // Clear the panel before adding new data

        // Fetch the window width from the mainFrame
        int windowWidth = mainFrame.getWidth() - 40; // Keep a margin

        // Create and position JLabels and JTextArea
        JLabel nameLabel = new JLabel("Name: " + rs.getString("Name"));
        JLabel addressLabel = new JLabel("Address: " + rs.getString("Address"));
        JLabel attendeesLabel = new JLabel("Attendees: " + rs.getString("Attendees"));

        // Create JTextArea for wrapping the description
        JTextArea descriptionArea = new JTextArea("Description: " + rs.getString("Description"));
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setOpaque(false);
        descriptionArea.setEditable(false);
        descriptionArea.setFocusable(false);

        // Set bounds (position and size) for each component to ensure left alignment and no overflow
        nameLabel.setBounds(10, 10, windowWidth, 25);
        descriptionArea.setBounds(10, 40, windowWidth, 100);
        addressLabel.setBounds(10, 150, windowWidth, 25);
        attendeesLabel.setBounds(10, 180, windowWidth, 25);

        // Add the components to the panel
        panel.add(nameLabel);
        panel.add(descriptionArea);
        panel.add(addressLabel);
        panel.add(attendeesLabel);

        // Refresh the panel to display the data
        panel.revalidate();
        panel.repaint();
    }
}
