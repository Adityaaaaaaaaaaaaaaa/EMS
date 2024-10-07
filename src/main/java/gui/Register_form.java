package gui;

import app.Main;
import db.Db_Connect;
import session.Session;
import session.User;
import utility.Utility;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Register_form extends JPanel {
	private JPanel Register_Panel;
	private JTextField regName;
	private JTextField regEmail;
	private JTextField regUserName;
	private JTextField regAddress;
	private JTextField regPhone;
	private JPasswordField regPwd;
	private JButton btnBack;
	private JButton btnRegister;
	private JLabel errorMsg;
	private JCheckBox showPwd;

	private static final Logger LOGGER = Logger.getLogger(Register_form.class.getName());

	private Main mainFrame;

	public Register_form(Main mainFrame) {
		this.mainFrame = mainFrame;
		Utility.setWindowSize(mainFrame);
		setLayout(new BorderLayout());
		add(Register_Panel);

		Utility.addFieldListeners(errorMsg, regName, regEmail, regUserName, regAddress, regPhone, regPwd);
		Utility.setCursorToPointer(btnBack, btnRegister);
		Utility.addShowPasswordListener(regPwd, showPwd);

		btnRegister.addActionListener(e -> {
			if (validateInput()) {
				registerUser();
				Utility.clearForm(new JTextField[]{regName, regEmail, regUserName, regAddress, regPhone}, regPwd, errorMsg);
			}
		});

		btnBack.addActionListener(e -> {
			mainFrame.getScreenManager().showPanel("Login_form");
			Utility.clearForm(new JTextField[]{regName, regEmail, regUserName, regAddress, regPhone}, regPwd, errorMsg);
		});
	}

	private boolean validateInput() {
		if (regName.getText().trim().isEmpty() || regEmail.getText().trim().isEmpty() ||
				regUserName.getText().trim().isEmpty() || regAddress.getText().trim().isEmpty() ||
				regPhone.getText().trim().isEmpty() || new String(regPwd.getPassword()).trim().isEmpty()) {
			errorMsg.setText("All fields are required.");
			return false;
		}
		return true;
	}

	private void registerUser() {
		String role = "User";

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = Db_Connect.getConnection();
			connection.setAutoCommit(false);  // Disable auto-commit for manual transaction control

			statement = connection.prepareStatement(
					"INSERT INTO Users (name, email, username, address, phone, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)");

			String name = regName.getText().trim();
			String email = regEmail.getText().trim();
			String username = regUserName.getText().trim();
			String address = regAddress.getText().trim();
			String phone = regPhone.getText().trim();
			String password = new String(regPwd.getPassword()).trim();

			statement.setString(1, name);
			statement.setString(2, email);
			statement.setString(3, username);
			statement.setString(4, address);
			statement.setString(5, phone);
			statement.setString(6, password);
			statement.setString(7, role);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				connection.commit();

				Session.currentUser = new User(username, role, name, email, phone);

				User_profile userProfile = mainFrame.getScreenManager().getUserProfile();
				Organizer_profile organizerProfile = mainFrame.getScreenManager().getOrganizerProfile();

				if (userProfile != null) {
					userProfile.fetchAndDisplayUserData(Session.currentUser.getId());
				}

				if (organizerProfile != null) {
					organizerProfile.fetchAndDisplayUserData(Session.currentUser.getId());
				}

				mainFrame.getScreenManager().showPanel("Home");
				mainFrame.revalidate();
				mainFrame.repaint();

			} else {
				connection.rollback();
				JOptionPane.showMessageDialog(this, "Registration failed!", "Error", JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (SQLException ex) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException rollbackEx) {
					LOGGER.log(Level.SEVERE, "Failed to rollback transaction", rollbackEx);
				}
			}
			errorMsg.setText("Database error. Please try again later.");
			LOGGER.log(Level.SEVERE, "Database error during registration: " + ex.getMessage(), ex);

		} catch (ClassNotFoundException ex) {
			errorMsg.setText("Internal error. Please contact support.");
			LOGGER.log(Level.SEVERE, "JDBC Driver not found: " + ex.getMessage(), ex);
		}
	}
}
