package gui;

import app.Main;
import db.Db_Connect;
import utility.MenuInterface;
import utility.Utility;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Booking_Details extends JPanel implements MenuInterface {
    private JPanel screenInfo; // JPanel for displaying booking table
    private JTable bookingTable; // Table to display booking data
    private JScrollPane tableScrollPane; // Scroll pane for the table
    private JPanel stats; // JPanel to display the statistics
    private JLabel numBooking;
    private JLabel numGuest;
    private JLabel avgGuest;
    private JLabel totalRev;
    private JLabel event;
    private JSplitPane splitPane;
    private JMenuBar menuBar;
    private JPanel main;

    private Main mainFrame;
    private static final Logger LOGGER = Logger.getLogger(Booking_Details.class.getName());

    public Booking_Details(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);

        setLayout(new BorderLayout());

        // Create a menu bar and initialize it with the menu items and listeners
        menuBar = new JMenuBar();
        initializeMenu(menuBar, mainFrame, main.getBackground(), main.getForeground());
        menuBar.setVisible(false);
        add(menuBar, BorderLayout.NORTH);

        setPreferredSize(new Dimension(500, 500));

        // Initialize the screenInfo panel (for the table)
        screenInfo = new JPanel(new BorderLayout());

        // Initialize the table and add it to a scroll pane
        bookingTable = new JTable();
        tableScrollPane = new JScrollPane(bookingTable);
        screenInfo.add(tableScrollPane, BorderLayout.CENTER);


        // Use JSplitPane to divide space between the screenInfo and stats panels
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, screenInfo, stats);
        splitPane.setResizeWeight(0.5); // Split the space equally
        splitPane.setDividerLocation(150); // Initially divide the panels equally (frame is 500px tall)

        // Add the split pane to the layout
        add(splitPane, BorderLayout.CENTER);

        // Fetch and display booking data in the table
        fetchAndDisplayBookingData();

        // Fetch and display statistics in the stats panel
        fetchAndDisplayStatistics();

        // Ensure both panels are revalidated and repainted
        revalidate();
        repaint();
    }

    private void fetchAndDisplayBookingData() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Db_Connect.getConnection();

            String sql = "SELECT Name, Event, NumGuest, Location, ReservationDate, AdditionalInfo, PaymentMethod, Price FROM Booking";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            // Create a non-editable table model by overriding isCellEditable method
            DefaultTableModel tableModel = new DefaultTableModel(
                    new Object[]{"Name", "Event", "Guests", "Location", "Date", "Additional Info", "Payment", "Price"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // All cells are not editable
                    return false;
                }
            };

            // Iterate through the result set and add rows to the table model
            while (rs.next()) {
                String name = rs.getString("Name");
                String event = rs.getString("Event");
                int numGuests = rs.getInt("NumGuest");
                String location = rs.getString("Location");
                String reservationDate = rs.getDate("ReservationDate").toString();
                String additionalInfo = rs.getString("AdditionalInfo");
                String paymentMethod = rs.getString("PaymentMethod");
                int price = rs.getInt("Price");

                tableModel.addRow(new Object[]{name, event, numGuests, location, reservationDate, additionalInfo, paymentMethod, price});
            }

            // Set the non-editable model to the table
            bookingTable.setModel(tableModel);

        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Database error while fetching booking data: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(this, "Database error. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing database resources: " + e.getMessage(), e);
            }
        }
    }


    private void fetchAndDisplayStatistics() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Db_Connect.getConnection();

            // Total bookings
            stmt = conn.prepareStatement("SELECT COUNT(*) AS totalBookings FROM Booking");
            rs = stmt.executeQuery();
            if (rs.next()) {
                numBooking.setText(String.valueOf(rs.getInt("totalBookings")));  // Update numBooking label
            }
            rs.close();
            stmt.close();

            // Total guests
            stmt = conn.prepareStatement("SELECT SUM(NumGuest) AS totalGuests FROM Booking");
            rs = stmt.executeQuery();
            if (rs.next()) {
                numGuest.setText(String.valueOf(rs.getInt("totalGuests")));  // Update numGuest label
            }
            rs.close();
            stmt.close();

            // Average number of guests
            stmt = conn.prepareStatement("SELECT AVG(NumGuest) AS avgGuests FROM Booking");
            rs = stmt.executeQuery();
            if (rs.next()) {
                avgGuest.setText(String.format("%.2f", rs.getDouble("avgGuests")));  // Update avgGuest label
            }
            rs.close();
            stmt.close();

            // Total revenue
            stmt = conn.prepareStatement("SELECT SUM(Price) AS totalRevenue FROM Booking");
            rs = stmt.executeQuery();
            if (rs.next()) {
                totalRev.setText(String.valueOf(rs.getInt("totalRevenue")));  // Update totalRev label
            }
            rs.close();
            stmt.close();

            // Most popular event
            stmt = conn.prepareStatement("SELECT Event, COUNT(*) AS eventCount FROM Booking GROUP BY Event ORDER BY eventCount DESC LIMIT 1");
            rs = stmt.executeQuery();
            if (rs.next()) {
                event.setText(rs.getString("Event"));  // Update event label
            }

        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Database error while fetching statistics: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(this, "Database error. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing database resources: " + e.getMessage(), e);
            }
        }
    }

}
