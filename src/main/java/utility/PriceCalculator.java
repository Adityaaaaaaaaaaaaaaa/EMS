package utility;

import javax.swing.JLabel;

public interface PriceCalculator {
	int calculatePrice(String eventType, int numGuests);

	default void updatePriceLabel(JLabel priceLabel, int totalPrice) {
		priceLabel.setText("Total Price: Rs" + totalPrice);
	}
}

