package utility;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    private static final int WINDOW_WIDTH = 550;
    private static final int WINDOW_HEIGHT = 550;

    // Set window size and center it
    public static void setWindowSize(JFrame frame) {
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null); // Center the window
    }

    // Clear form fields and reset to default state
    public static void clearForm(JTextField[] textFields,
                                 JPasswordField passwordField,
                                 JLabel errorMsg) {
        for (JTextField field : textFields) {
            field.setText("");
        }
        if (passwordField != null) {
            passwordField.setText("");
        }
        if (errorMsg != null) {
            errorMsg.setText("");
        }
    }

    // Add field listeners to clear the error message
    public static void addFieldListeners(JLabel errorMsg, JTextField... textFields) {
        DocumentListener clearErrorListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                clearErrorMessage(errorMsg);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                clearErrorMessage(errorMsg);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                clearErrorMessage(errorMsg);
            }
        };

        for (JTextField field : textFields) {
            field.getDocument().addDocumentListener(clearErrorListener);
        }
    }

    // Clear error message
    public static void clearErrorMessage(JLabel errorMsg) {
        if (errorMsg != null) {
            errorMsg.setText("");
        }
    }

    // Clear multiple text fields
    public static void clearTextFields(JTextField... textFields) {
        for (JTextField field : textFields) {
            field.setText("");
        }
    }

    // Validate date format (DD/MM/YY)
    public static boolean isValidDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        dateFormat.setLenient(false); // Disable lenient parsing
        try {
            Date parsedDate = dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static void setCursorToPointer(JComponent... components) {
        for (JComponent component : components) {
            component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

}
