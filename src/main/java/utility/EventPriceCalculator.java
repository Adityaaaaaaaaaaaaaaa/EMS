package utility;

import javax.swing.JOptionPane;

public class EventPriceCalculator implements PriceCalculator {

	@Override
	public int calculatePrice(String eventType, int numGuests) {
		int basePrice = 0;

		int O_WEDDING_BASE_PRICE = 50000;
		int I_WEDDING_BASE_PRICE = 40000;
		int O_BIRTHDAY_BASE_PRICE = 20000;
		int I_BIRTHDAY_BASE_PRICE = 15000;
		int CONFERENCE_BASE_PRICE = 80000;

		int guestPriceModifier = calculateGuestPriceModifier(numGuests);

		if (eventType.equals("Choose Event Type")) {
			JOptionPane.showMessageDialog(null, "Please select a valid event type.", "Invalid Event", JOptionPane.ERROR_MESSAGE);
			return 0;
		}

		switch (eventType) {
			case "Wedding (Outdoor)" -> basePrice = O_WEDDING_BASE_PRICE;
			case "Wedding (Indoor)" -> basePrice = I_WEDDING_BASE_PRICE;
			case "Birthday Party (Outdoor)" -> basePrice = O_BIRTHDAY_BASE_PRICE;
			case "Birthday Party (Indoor)" -> basePrice = I_BIRTHDAY_BASE_PRICE;
			case "Conference (Indoor)" -> basePrice = CONFERENCE_BASE_PRICE;
			default -> {
				JOptionPane.showMessageDialog(null, "Please select a valid event type.", "Invalid Event", JOptionPane.ERROR_MESSAGE);
				return 0;
			}
		}

		return basePrice + guestPriceModifier;
	}

	private int calculateGuestPriceModifier(int numGuests) {
		int guestPrice = 0;

		if (numGuests <= 50) {
			guestPrice = 200 * numGuests;
		} else if (numGuests <= 100) {
			guestPrice = 175 * numGuests;
		} else if (numGuests <= 150) {
			guestPrice = 150 * numGuests;
		} else if (numGuests <= 250) {
			guestPrice = 125 * numGuests;
		} else if (numGuests <= 350) {
			guestPrice = 100 * numGuests;
		} else {
			guestPrice = 75 * numGuests;
		}

		return guestPrice;
	}
}
