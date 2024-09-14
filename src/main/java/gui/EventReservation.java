package gui;

import app.Main;
import utility.MenuInterface;
import utility.EventPriceCalculator;
import utility.Utility;

import javax.swing.*;
import java.awt.*;

public class EventReservation extends JPanel implements MenuInterface {
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
	private EventPriceCalculator priceCalculator; // Create an instance of the calculator

	public EventReservation(Main mainFrame) {
		this.mainFrame = mainFrame;
		this.priceCalculator = new EventPriceCalculator(); // Initialize price calculator
		Utility.setWindowSize(mainFrame);
		setLayout(new BorderLayout());
		add(bookingPanel, BorderLayout.CENTER);

		// Create a menu bar and initialize it with the menu items and listeners
		menuBar = new JMenuBar();
		initializeMenu(menuBar, mainFrame, bookingPanel.getBackground(), bookingPanel.getForeground());
		add(menuBar, BorderLayout.NORTH);

		// Display the initial slider value
		numGuestDisplay.setText(String.valueOf(numGuests.getValue()));

		// Add listeners for event type, location, and guest number changes to recalculate price
		EventType.addActionListener(e -> updateTotalPrice());
		EventLocation.addActionListener(e -> updateTotalPrice());
		numGuests.addChangeListener(e -> {
			numGuestDisplay.setText(String.valueOf(numGuests.getValue())); // Display selected number of guests
			updateTotalPrice(); // Recalculate price whenever guest number changes
		});

		// Clear button functionality
		BtnClear.addActionListener(e -> clearForm());

		// Pay button functionality
		BtnPay.addActionListener(e -> handlePay());

		// Cancel button functionality (returns to Home)
		BtnCancel.addActionListener(e -> mainFrame.getScreenManager().showPanel("Home"));
	}

	// Method to update total price
	private void updateTotalPrice() {
		String selectedEvent = (String) EventType.getSelectedItem();
		int guests = numGuests.getValue();

		// Call calculatePrice on the instance of EventPriceCalculator
		assert selectedEvent != null;
		int price = priceCalculator.calculatePrice(selectedEvent, guests);
		totalPrice.setText("Total Price: Rs " + price);
	}

	// Method to clear the form fields
	private void clearForm() {
		clientName.setText("");
		EventType.setSelectedIndex(0); // Reset to "Choose Event Type"
		EventLocation.setSelectedIndex(0); // Reset to "Choose Location"
		EventDate.setText("");
		Additional.setText("");
		numGuests.setValue(10); // Reset guest number to minimum
		totalPrice.setText("");
		PaymentMethod.setSelectedIndex(0); // Reset payment method
		numGuestDisplay.setText("10"); // Reset guest display
	}

	// Method to handle payment (for now, just show a message)
	private void handlePay() {
		if (!clientName.getText().isEmpty() && !EventDate.getText().isEmpty() && EventType.getSelectedIndex() != 0) {
			JOptionPane.showMessageDialog(this, "Congrats! We will get in touch soon.");
			System.out.println("Client Name: " + clientName.getText());
			System.out.println("Event Type: " + EventType.getSelectedItem());
			System.out.println("Number of Guests: " + numGuests.getValue());
			System.out.println("Event Location: " + EventLocation.getSelectedItem());
			System.out.println("Event Date: " + EventDate.getText());
			System.out.println("Additional Info: " + Additional.getText());
			System.out.println("Total Price: " + totalPrice.getText());
			mainFrame.getScreenManager().showPanel("Home");
		} else {
			JOptionPane.showMessageDialog(this, "Please fill out all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
