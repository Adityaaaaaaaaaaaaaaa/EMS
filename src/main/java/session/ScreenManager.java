package session;

import app.Main;
import gui.*;
import utility.MenuInterface;

import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ScreenManager {
	private final Main mainFrame;  // Reference to Main
	private final CardLayout cardLayout;
	private final JPanel mainPanel;
	private final Map<String, JPanel> screens;

	public ScreenManager(Main mainFrame) {
		this.mainFrame = mainFrame;
		this.cardLayout = new CardLayout();
		this.mainPanel = new JPanel(cardLayout);
		this.screens = new HashMap<>();

		this.mainFrame.setContentPane(mainPanel);

		// Register initial screens
		registerScreen("Login_form", new Login_form(mainFrame));
		registerScreen("Register_form", new Register_form(mainFrame));
	}

	public User_profile getUserProfile() {
		return (User_profile) screens.get("User_profile");
	}

	public Organizer_profile getOrganizerProfile() {
		return (Organizer_profile) screens.get("Organizer_profile");
	}

	public JPanel getScreen(String name) {
		return screens.get(name);
	}

	public void registerScreen(String name, JPanel panel) {
		screens.put(name, panel);
		mainPanel.add(panel, name);
	}

	public void showPanel(String name) {
		// Validate session before showing certain panels
		if ((name.equals("Organizer_profile") || name.equals("User_profile")) && Session.currentUser == null) {
			JOptionPane.showMessageDialog(mainFrame, "Please log in to access this feature.", "Session Error", JOptionPane.ERROR_MESSAGE);
			name = "Login_form";
		}

		// Create screen lazily if it doesn't exist
		if (!screens.containsKey(name)) {
			JPanel panel = createScreen(name);
			if (panel != null) {
				registerScreen(name, panel);
			} else {
				return; // Prevents showing an invalid or uninitialized panel
			}
		}

		// Handle menu based on the session and panel type
		JPanel panel = screens.get(name);
		if (panel instanceof MenuInterface) {
			// Only if the panel implements MenuInterface, we will update the menu
			JMenuBar menuBar = mainFrame.getAppMenuBar();  // Get the menu bar from the Main class
			((MenuInterface) panel).initializeMenu(menuBar, mainFrame, panel.getBackground(), panel.getForeground());
		}

		// Show the selected panel
		cardLayout.show(mainPanel, name);
	}

	private JPanel createScreen(String name) {
		JPanel panel = null;

		switch (name) {
			case "Organizer_profile" -> {
				if (Session.currentUser != null) {
					panel = new Organizer_profile(mainFrame);
				} else {
					JOptionPane.showMessageDialog(mainFrame, "Please log in first.");
					showPanel("Login_form");
				}
			}
			case "User_profile" -> {
				if (Session.currentUser != null) {
					panel = new User_profile(mainFrame);
				} else {
					JOptionPane.showMessageDialog(mainFrame, "Please log in first.");
					showPanel("Login_form");
				}
			}
			case "Booking" -> panel = new Booking(mainFrame);
			case "Login_form" -> panel = new Login_form(mainFrame);
			case "Register_form" -> panel = new Register_form(mainFrame);
			case "Home" -> panel = new Home(mainFrame);
			case "Event_detail" -> panel = new Event_detail(mainFrame);
			case "Event_Location" -> panel = new Event_Location(mainFrame);
			case "Booking_Details" -> panel = new Booking_Details(mainFrame);
			default -> {
				JOptionPane.showMessageDialog(mainFrame, "Unknown screen requested.");
				showPanel("Login_form");
			}
		}
		return panel;
	}
}
