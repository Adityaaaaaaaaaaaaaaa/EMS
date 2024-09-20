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


    private void displayDataInPanel(ResultSet rs, JPanel panel) throws SQLException {
        panel.removeAll();  // Clear the panel before adding new data

        // Width for each component (considering the image on the left)
        int componentWidth = 220;
        int componentHeight = 20;
        int verticalSpacing = 5;  // Small space between components

        // Color for the text (name, description, address, attendees)
        Color textColor = Color.decode("#34542A");

        JTextArea nameArea;
        JTextArea descriptionArea;
        JTextArea addressArea;
        JLabel attendeesLabel;  // Declare attendeesLabel here

        // If it's indoor1 or outdoor1, allow the name to wrap on two lines and set it to bold
        if (panel == indoor1 || panel == outdoor1) {
            nameArea = new JTextArea("Name: " + rs.getString("Name"));
            nameArea.setWrapStyleWord(true);
            nameArea.setLineWrap(true);
            nameArea.setOpaque(false);
            nameArea.setEditable(false);
            nameArea.setFocusable(false);
            nameArea.setFont(new Font("Arial", Font.BOLD, 14));  // Set bold font
            nameArea.setForeground(textColor);  // Set text color
            nameArea.setBounds(10, 10, componentWidth, 40);  // Allow for 2-line name
        } else {
            // Default JTextArea for other panels, bold font for name
            nameArea = new JTextArea("Name: " + rs.getString("Name"));
            nameArea.setWrapStyleWord(false);
            nameArea.setLineWrap(false);
            nameArea.setOpaque(false);
            nameArea.setEditable(false);
            nameArea.setFocusable(false);
            nameArea.setFont(new Font("Arial", Font.BOLD, 14));  // Set bold font
            nameArea.setForeground(textColor);  // Set text color
            nameArea.setBounds(10, 10, componentWidth, componentHeight);
        }

        // Handle description wrapping in 2 lines for outdoor1 and conference1
        if (panel == outdoor1 || panel == conference1) {
            descriptionArea = new JTextArea("Description: " + rs.getString("Description"));
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setLineWrap(true);
            descriptionArea.setOpaque(false);
            descriptionArea.setEditable(false);
            descriptionArea.setFocusable(false);
            descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));  // Set font
            descriptionArea.setForeground(textColor);  // Set text color
            descriptionArea.setBounds(10, 55, componentWidth, 40);  // Allow for 2-line description
        } else {
            descriptionArea = new JTextArea("Description: " + rs.getString("Description"));
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setLineWrap(true);
            descriptionArea.setOpaque(false);
            descriptionArea.setEditable(false);
            descriptionArea.setFocusable(false);
            descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));  // Set font
            descriptionArea.setForeground(textColor);  // Set text color
            descriptionArea.setBounds(10, 55, componentWidth, 60);  // Regular description height
        }

        // Handle address wrapping in 2 lines for outdoor1 and conference1
        if (panel == outdoor1 || panel == conference1) {
            addressArea = new JTextArea("Address: " + rs.getString("Address"));
            addressArea.setWrapStyleWord(true);
            addressArea.setLineWrap(true);
            addressArea.setOpaque(false);
            addressArea.setEditable(false);
            addressArea.setFocusable(false);
            addressArea.setFont(new Font("Arial", Font.PLAIN, 12));  // Set font
            addressArea.setForeground(textColor);  // Set text color
            addressArea.setBounds(10, 100, componentWidth, 40);  // Allow for 2-line address
        } else {
            addressArea = new JTextArea("Address: " + rs.getString("Address"));
            addressArea.setWrapStyleWord(true);
            addressArea.setLineWrap(true);
            addressArea.setOpaque(false);
            addressArea.setEditable(false);
            addressArea.setFocusable(false);
            addressArea.setFont(new Font("Arial", Font.PLAIN, 12));  // Set font
            addressArea.setForeground(textColor);  // Set text color
            addressArea.setBounds(10, 120, componentWidth, 50);     // Regular height for address
        }

        // Create and position attendees label (non-bold)
        attendeesLabel = new JLabel("Attendees: " + rs.getString("Attendees"));
        attendeesLabel.setFont(new Font("Arial", Font.PLAIN, 12));  // Set non-bold font
        attendeesLabel.setForeground(textColor);  // Set text color

        // Set the bounds for attendeesLabel (remove space between description and attendees for outdoor1 and conference1)
        if (panel == outdoor1 || panel == conference1) {
            attendeesLabel.setBounds(10, 100 + 40 + verticalSpacing, componentWidth, componentHeight);  // Right below the address
        } else {
            attendeesLabel.setBounds(10, 165, componentWidth, componentHeight);  // Default positioning
        }

        // Add components to the panel
        panel.add(nameArea);
        panel.add(descriptionArea);
        panel.add(addressArea);
        panel.add(attendeesLabel);

        // Repaint and revalidate to refresh the UI
        panel.revalidate();
        panel.repaint();
    }


}
