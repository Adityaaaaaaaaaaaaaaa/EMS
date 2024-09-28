package utility;

import javax.swing.JOptionPane;

public class EventPriceCalculator implements PriceCalculator {

	@Override
	public int calculatePrice(String eventType, int numGuests) {
		int basePrice = 0;

		// Define realistic base prices for different events (in Mauritian rupees)
		int O_WEDDING_BASE_PRICE = 50000; // Base price for a wedding
		int I_WEDDING_BASE_PRICE = 40000; // Base price for a wedding
		int O_BIRTHDAY_BASE_PRICE = 20000; // Base price for a birthday party
		int I_BIRTHDAY_BASE_PRICE = 15000; // Base price for a birthday party
		int CONFERENCE_BASE_PRICE = 80000; // Base price for a conference

		// Calculate guest price modifier based on the number of guests
		int guestPriceModifier = calculateGuestPriceModifier(numGuests);

		// Check if the event type is the default "Choose Event Type"
		if (eventType.equals("Choose Event Type")) {
			JOptionPane.showMessageDialog(null, "Please select a valid event type.", "Invalid Event", JOptionPane.ERROR_MESSAGE);
			return 0;
		}

		// Calculate base price based on event type
		switch (eventType) {
			case "Wedding (Outdoor)" -> basePrice = O_WEDDING_BASE_PRICE;
			case "Wedding (Indoor)" -> basePrice = I_WEDDING_BASE_PRICE;
			case "Birthday Party (Outdoor)" -> basePrice = O_BIRTHDAY_BASE_PRICE;
			case "Birthday Party (Indoor)" -> basePrice = I_BIRTHDAY_BASE_PRICE;
			case "Conference (Indoor)" -> basePrice = CONFERENCE_BASE_PRICE;
			default -> {
				// Just a safeguard in case of an invalid event type
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
