package utility;

import javax.swing.*;

public class EventPriceCalculator implements PriceCalculator {

	@Override
	public int calculatePrice(String eventType, int numGuests) {
		int basePrice = 0;

		// Define realistic base prices for different events (in Mauritian rupees)
		int WEDDING_BASE_PRICE = 50000; // Base price for a wedding
		int BIRTHDAY_BASE_PRICE = 20000; // Base price for a birthday party
		int CONFERENCE_BASE_PRICE = 80000; // Base price for a conference

		// Calculate guest price modifier based on the number of guests
		int guestPriceModifier = calculateGuestPriceModifier(numGuests);

		// Calculate base price based on event type
		switch (eventType) {
			case "Choose Event Type" -> JOptionPane.showMessageDialog(null, "Please select an Event below ", "Invalid Event", JOptionPane.ERROR_MESSAGE);
			case "Wedding" -> basePrice = WEDDING_BASE_PRICE;
			case "Conference" -> basePrice = CONFERENCE_BASE_PRICE;
			case "Birthday Party" -> basePrice = BIRTHDAY_BASE_PRICE;
			default -> {
				JOptionPane.showMessageDialog(null, "Please select a valid event type.", "Invalid Event", JOptionPane.ERROR_MESSAGE);
				return 0;
			}
		}

		// Return the final price by adding the guest price modifier to the base price
		return basePrice + guestPriceModifier;
	}

	// Method to calculate guest price modifier based on number of guests
	private int calculateGuestPriceModifier(int numGuests) {
		int guestPrice = 0;

		// Define realistic price tiers based on guest ranges
		if (numGuests <= 50) {
			guestPrice = 200 * numGuests; // Small event (less than 50 guests)
		} else if (numGuests <= 100) {
			guestPrice = 175 * numGuests; // Medium event (51 to 100 guests)
		} else if (numGuests <= 150) {
			guestPrice = 150 * numGuests; // Large event (101 to 150 guests)
		} else if (numGuests <= 250) {
			guestPrice = 125 * numGuests; // Very large event (151 to 250 guests)
		} else if (numGuests <= 350) {
			guestPrice = 100 * numGuests;  // Extra large event (251 to 350 guests)
		} else {
			guestPrice = 75 * numGuests;  // Mega event (more than 350 guests)
		}

		return guestPrice;
	}
}
