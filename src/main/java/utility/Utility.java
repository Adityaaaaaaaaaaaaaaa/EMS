package utility;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Utility {
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 600;

    public static void setWindowSize(JFrame frame) {
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null); // Center the window
    }

    // Clear form fields and reset to default state
    public static void clearForm(JTextField[] textFields,
                                 JPasswordField passwordField,
                                 JRadioButton defaultRadioButton,
                                 JLabel errorMsg) {
        for (JTextField field : textFields) {
            field.setText("");
        }
        if (passwordField != null) {
            passwordField.setText("");
        }
        if (defaultRadioButton != null) {
            defaultRadioButton.setSelected(true);
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

    public static void clearTextFields(JTextField... textFields) {
        for (JTextField field : textFields) {
            field.setText("");
        }
    }

}
