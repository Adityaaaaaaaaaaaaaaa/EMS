package utility;

import javax.swing.JLabel;

public interface PriceCalculator {
	// Calculate total price based on event type, location, and number of guests
	int calculatePrice(String eventType, int numGuests);

	// Display the calculated price in the relevant label
	default void updatePriceLabel(JLabel priceLabel, int totalPrice) {
		priceLabel.setText("Total Price: Rs" + totalPrice);
	}
}

