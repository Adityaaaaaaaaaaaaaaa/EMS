package gui;

import app.Main;
import db.Db_Connect;
import utility.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class xEventReservation extends JPanel {

    private JComboBox<String> eventCombo;
    private JTextField dateField;
    private JTextField nameField;
    private JTextField sizeField;
    private JTextField priceField;
    private JComboBox<String> paymentCombo;
    private JPanel panel;
    private JLabel Name;
    private JLabel SelectEvent;
    private JLabel date;
    private JLabel EventSize;
    private JLabel price;
    private JLabel paymentMethod;
    private JButton Submitbtn;
    private Main mainFrame;

    public xEventReservation(Main mainFrame) {
        this.mainFrame = mainFrame;
        Utility.setWindowSize(mainFrame);
        setLayout(new BorderLayout());
        add(panel);


        eventCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedEvent = eventCombo.getSelectedItem().toString();
				switch (selectedEvent) {
					case "Wedding", "Engagement" -> {
						try {
							int size = Integer.parseInt(sizeField.getText());
							int pricePerPerson = 1000;
							int totalPrice = pricePerPerson * size;
							priceField.setText(String.valueOf(totalPrice));
						} catch (NumberFormatException ex) {
							//JOptionPane.showMessageDialog(mainFrame, "Please enter a valid number for size then choose event.");
						}
					}
					case "Conference" -> {
						try {
							int sizeOfEvent = Integer.parseInt(sizeField.getText());
							int pricePerPerson = 600;
							int totalPrice = pricePerPerson * sizeOfEvent;
							priceField.setText(String.valueOf(totalPrice));
						} catch (NumberFormatException ex) {
							//JOptionPane.showMessageDialog(mainFrame, "Please enter a valid number for size then choose event.");
						}
					}
					case "Birthday Party" -> {
						try {
							int sizeOfEvent = Integer.parseInt(sizeField.getText());
							int pricePerPerson = 800;
							int totalPrice = pricePerPerson * sizeOfEvent;
							priceField.setText(String.valueOf(totalPrice));
						} catch (NumberFormatException ex) {
							//JOptionPane.showMessageDialog(mainFrame, "Please enter a valid number for size then choose event.");
						}
					}
					default -> priceField.setText("");
				}
            }
        });
        
        Submitbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String event = eventCombo.getSelectedItem().toString();
                String date = dateField.getText();
                String size = sizeField.getText();
                String price = priceField.getText();
                String paymentMthd = paymentCombo.getSelectedItem().toString();

                if (!name.isEmpty() && !event.isEmpty() && !date.isEmpty() && !size.isEmpty() && !price.isEmpty()) {
                    insertValuesIntoDatabase(name, event, date, size, price, paymentMthd);
                    JOptionPane.showMessageDialog(mainFrame, "Values have been inserted successfully");

                    // Clear the fields after successful submission
                    nameField.setText("");
                    eventCombo.setSelectedIndex(0);  // Reset to the first item
                    dateField.setText("");
                    sizeField.setText("");
                    priceField.setText("");
                    paymentCombo.setSelectedIndex(0);  // Reset to the first item
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Please fill all the fields");
                }
            }
        });
    }

    private void insertValuesIntoDatabase(String name, String event, String date, String size, String price, String paymentMthd) {

        String insertIntoTable = "INSERT INTO Booking (Name, Event, reservationDate, EventSize, Price, payment_method) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Db_Connect.getConnection();
             PreparedStatement statement = conn.prepareStatement(insertIntoTable)) {

            statement.setString(1, name);
            statement.setString(2, event);
            statement.setString(3, date);
            statement.setString(4, size);
            statement.setString(5, price);
            statement.setString(6, paymentMthd);

            int rowsInserted = statement.executeUpdate();  // Correct method to use for INSERT, UPDATE, DELETE statements

            if (rowsInserted > 0) {
                System.out.println("Insert successful. " + rowsInserted + " row(s) added.");
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error inserting data into the database.");
        }
    }
}
