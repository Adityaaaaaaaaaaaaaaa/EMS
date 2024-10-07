package gui;

import app.Main;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import db.Db_Connect;
import utility.MenuInterface;
import utility.EventPriceCalculator;
import utility.Utility;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;

public class Booking extends JPanel implements MenuInterface {
	private JPanel bookingPanel;
	private JButton BtnCancel;
	private JButton BtnClear;
	private JButton BtnPay;
	private JTextField clientName;
	private JComboBox<String> EventType;
	private JSlider numGuests;
	private JComboBox<String> EventLocation;
	private JTextField EventDate;
	private JTextField Additional;
	private JComboBox<String> PaymentMethod;
	private JLabel numGuestDisplay;
	private JLabel totalPrice;
	private JMenuBar menuBar;

	private Main mainFrame;
	private final EventPriceCalculator priceCalculator;

	private static final Logger LOGGER = Logger.getLogger(Booking.class.getName()); // Create a logger

	public Booking(Main mainFrame) {
		this.mainFrame = mainFrame;
		this.priceCalculator = new EventPriceCalculator();
		Utility.setWindowSize(mainFrame);
		setLayout(new BorderLayout());
		add(bookingPanel, BorderLayout.CENTER);

		menuBar = new JMenuBar();
		initializeMenu(menuBar, mainFrame, bookingPanel.getBackground(), bookingPanel.getForeground());
		menuBar.setVisible(false);
		add(menuBar, BorderLayout.NORTH);

		Utility.setCursorToPointer(menuBar, EventType, numGuests, EventLocation, PaymentMethod);

		numGuestDisplay.setText(String.valueOf(numGuests.getValue()));

		EventType.addActionListener(e -> updateTotalPrice());
		EventLocation.addActionListener(e -> updateTotalPrice());
		numGuests.addChangeListener(e -> {
			numGuestDisplay.setText(String.valueOf(numGuests.getValue()));
			updateTotalPrice();
		});

		BtnClear.addActionListener(e -> clearForm());

		BtnPay.addActionListener(e -> handlePay());

		BtnCancel.addActionListener(e -> mainFrame.getScreenManager().showPanel("Home"));
	}

	private void updateTotalPrice() {
		String selectedEvent = (String) EventType.getSelectedItem();
		int guests = numGuests.getValue();

		assert selectedEvent != null;
		if (selectedEvent.equals("Choose Event Type")) {
			totalPrice.setText("");
		} else {
			int price = priceCalculator.calculatePrice(selectedEvent, guests);
			totalPrice.setText("Total Price: Rs " + price);
		}
	}

	private void clearForm() {
		Utility.clearTextFields(clientName, EventDate, Additional);
		EventType.setSelectedIndex(0);
		EventLocation.setSelectedIndex(0);
		numGuests.setValue(10);
		totalPrice.setText("");
		PaymentMethod.setSelectedIndex(0);
		numGuestDisplay.setText("10");
	}

	private void handlePay() {
		if (EventType.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(this, "Please select a valid event type.", "Invalid Event", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (PaymentMethod.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(this, "Please select a valid payment method.", "Invalid Payment", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (clientName.getText().isEmpty() || EventDate.getText().isEmpty() || EventLocation.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(this, "Please fill out all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!Utility.isValidDate(EventDate.getText())) {
			JOptionPane.showMessageDialog(this, "Please enter a valid date in DD/MM/YY format.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
			return;
		}

		insertReservationIntoDB();

		try {
			generateReceiptPDF();
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "Failed to generate receipt: " + ex.getMessage(), ex);
			JOptionPane.showMessageDialog(this, "Failed to generate receipt. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
		}

		JOptionPane.showMessageDialog(this, "Receipt generated successfully.\nThank you for booking with us!\nWe will get in touch soon.");
		mainFrame.getScreenManager().showPanel("Home");
	}

	private void insertReservationIntoDB() {
		String client = clientName.getText();
		String eventType = (String) EventType.getSelectedItem();
		int guests = numGuests.getValue();
		String location = (String) EventLocation.getSelectedItem();
		String eventDate = EventDate.getText();
		String additional = Additional.getText();
		String paymentMethod = (String) PaymentMethod.getSelectedItem();
		String price = totalPrice.getText().replace("Total Price: Rs ", "");

		String sql = "INSERT INTO booking (Name, Event, NumGuest, Location, ReservationDate, AdditionalInfo, PaymentMethod, Price) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = Db_Connect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, client);
			pstmt.setString(2, eventType);
			pstmt.setInt(3, guests);
			pstmt.setString(4, location);
			pstmt.setString(5, eventDate);
			pstmt.setString(6, additional);
			pstmt.setString(7, paymentMethod);
			pstmt.setString(8, price);

			int rowsInserted = pstmt.executeUpdate();
			if (rowsInserted > 0) {
				LOGGER.log(Level.INFO, "Reservation saved successfully.");
			}

		} catch (SQLException | ClassNotFoundException ex) {
			LOGGER.log(Level.SEVERE, "Failed to save the reservation: " + ex.getMessage(), ex);
			JOptionPane.showMessageDialog(this, "Failed to save the reservation. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void generateReceiptPDF() throws DocumentException, IOException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("Receipt.pdf"));
		document.open();

		Image logo = Image.getInstance("src/main/resources/image/logo_icon.png"); // Replace with actual logo path
		logo.scaleToFit(50, 50); // Scale logo if needed
		logo.setAlignment(Element.ALIGN_LEFT);
		document.add(logo);

		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
		Paragraph title = new Paragraph("Booking Confirmation Receipt", titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);

		document.add(new Paragraph(" "));

		// Add date and time
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String generatedOn = dateFormat.format(new Date());
		Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY);
		Paragraph dateParagraph = new Paragraph("Generated on: " + generatedOn, dateFont);
		dateParagraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(dateParagraph);

		document.add(new Paragraph(" "));

		Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

		document.add(new Paragraph("Client Name: " + clientName.getText(), normalFont));
		document.add(new Paragraph("Event Type: " + (String) EventType.getSelectedItem(), normalFont));
		document.add(new Paragraph("Number of Guests: " + numGuests.getValue(), normalFont));
		document.add(new Paragraph("Event Location: " + (String) EventLocation.getSelectedItem(), normalFont));
		document.add(new Paragraph("Event Date: " + EventDate.getText(), normalFont));
		document.add(new Paragraph("Payment Method: " + (String) PaymentMethod.getSelectedItem(), normalFont));
		document.add(new Paragraph("Additional Information: " + Additional.getText(), normalFont));
		document.add(new Paragraph("Total Price: Rs " + totalPrice.getText().replace("Total Price: Rs ", ""), normalFont));

		document.add(new Paragraph(" "));

		Font thankYouFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
		Paragraph thankYouMessage = new Paragraph("Thank you for booking with us!", thankYouFont);
		thankYouMessage.setAlignment(Element.ALIGN_CENTER);
		document.add(thankYouMessage);

		Font cancelFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
		Paragraph cancelText = new Paragraph("For any cancellation, please contact us 48 hours in advance at Evenia@gmail.com.", cancelFont);
		cancelText.setAlignment(Element.ALIGN_CENTER);
		document.add(cancelText);

		document.close();
	}

}
