package gui;

import app.Main;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import db.Db_Connect;
import session.Session;
import utility.MenuInterface;
import utility.Utility;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.io.IOException;

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
    private JButton printReport;

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

        Utility.setCursorToPointer(printReport, screenInfo);

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

        // Component listener to refresh the table and statistics whenever the panel is shown
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                // Refresh data when the panel is shown
                fetchAndDisplayBookingData();
                fetchAndDisplayStatistics();
            }
        });

        // Initial fetch and display of booking data and statistics
        fetchAndDisplayBookingData();
        fetchAndDisplayStatistics();

        // Ensure both panels are revalidated and repainted
        revalidate();
        repaint();

        // Add action listener to the printReport button
        printReport.addActionListener(e -> {
            try {
                saveReportToPDF();
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Error generating PDF report: " + ex.getMessage(), ex);
                JOptionPane.showMessageDialog(this, "Error generating PDF. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    // Fetch and display booking data from the database
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

            // Date formatter to format the SQL date into dd/MM/yyyy
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            // Iterate through the result set and add rows to the table model
            while (rs.next()) {
                String name = rs.getString("Name");
                String event = rs.getString("Event");
                int numGuests = rs.getInt("NumGuest");
                String location = rs.getString("Location");

                // Retrieve and format the date properly
                Date reservationDate = rs.getDate("ReservationDate");
                String formattedDate = dateFormat.format(reservationDate);

                String additionalInfo = rs.getString("AdditionalInfo");
                String paymentMethod = rs.getString("PaymentMethod");
                int price = rs.getInt("Price");

                // Add row to the table with formatted date
                tableModel.addRow(new Object[]{name, event, numGuests, location, formattedDate, additionalInfo, paymentMethod, price});
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

    // Fetch and display statistics
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

    private void saveReportToPDF() throws DocumentException, IOException {
        // Get the logged-in user's name from the session
        String generatedBy = Session.currentUser.getName(); // Assuming Session class has this method

        // Set up the document with A4 size and landscape orientation
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, new FileOutputStream("Report.pdf"));
        document.open();

        // Add the logo to the document (adjust the file path to your logo image)
        Image logo = Image.getInstance("src/main/resources/image/logo_icon.png"); // Replace with actual path to your logo
        logo.scaleToFit(50, 50); // Resize the logo if needed (width, height)
        logo.setAlignment(Element.ALIGN_CENTER); // Align the logo to the center
        document.add(logo);

        document.add(new Paragraph(" ")); // Blank line after the logo

        // Add a title with styling
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Paragraph title = new Paragraph("Booking Details Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph(" ")); // Blank line

        // Add the date, time, and user who generated the report
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String generatedOn = dateFormat.format(new Date());
        Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY);
        Paragraph generatedInfo = new Paragraph("Generated by: " + generatedBy + " on: " + generatedOn, dateFont);
        generatedInfo.setAlignment(Element.ALIGN_RIGHT);
        document.add(generatedInfo);

        document.add(new Paragraph(" ")); // Blank line

        // Add an acknowledgment section
        Font ackFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
        Paragraph acknowledgment = new Paragraph("This report is the property of Evenia Event Management System. Unauthorized sharing or distribution is prohibited.", ackFont);
        acknowledgment.setAlignment(Element.ALIGN_CENTER);
        document.add(acknowledgment);

        document.add(new Paragraph(" ")); // Blank line

        // Add the booking data (from JTable)
        PdfPTable pdfTable = new PdfPTable(bookingTable.getColumnCount());
        pdfTable.setWidthPercentage(100); // Set the table to take full width of the page
        pdfTable.setSpacingBefore(10f);   // Space before the table
        pdfTable.setSpacingAfter(10f);    // Space after the table

        // Add table headers with custom styling
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
        for (int i = 0; i < bookingTable.getColumnCount(); i++) {
            PdfPCell headerCell = new PdfPCell(new Phrase(bookingTable.getColumnName(i), headerFont));
            headerCell.setBackgroundColor(BaseColor.DARK_GRAY); // Background color for headers
            headerCell.setPadding(8); // Add some padding
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(headerCell);
        }

        // Add table rows with data
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLACK);
        for (int rows = 0; rows < bookingTable.getRowCount(); rows++) {
            for (int cols = 0; cols < bookingTable.getColumnCount(); cols++) {
                PdfPCell cell = new PdfPCell(new Phrase(bookingTable.getValueAt(rows, cols).toString(), cellFont));
                cell.setPadding(5); // Add some padding to the cell
                cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Center align the text
                pdfTable.addCell(cell);
            }
        }

        document.add(pdfTable); // Add the table to the document
        document.add(new Paragraph(" ")); // Blank line

        // Add the statistics section with some styling
        Font statFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
        document.add(new Paragraph("Statistics", statFont));

        Font statDataFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
        document.add(new Paragraph("Total Bookings: " + numBooking.getText(), statDataFont));
        document.add(new Paragraph("Total Guests: " + numGuest.getText(), statDataFont));
        document.add(new Paragraph("Average Guests: " + avgGuest.getText(), statDataFont));
        document.add(new Paragraph("Most Popular Event: " + event.getText(), statDataFont));
        document.add(new Paragraph("Total Revenue: Rs " + totalRev.getText(), statDataFont));

        // Close the document
        document.close();

        JOptionPane.showMessageDialog(this, "PDF report saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

}
