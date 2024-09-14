package utility;

import app.Main;
import gui.Organizer_profile;
import gui.User_profile;
import session.Session;

import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Color;

public interface MenuInterface {

    // Method to initialize the menu and add listeners
    default void initializeMenu(JMenuBar menuBar, Main mainFrame, Color backgroundColor, Color foregroundColor) {

        JMenu menu = new JMenu("Menu");

        // Create menu items
        JMenuItem home = new JMenuItem("Home");
        JMenuItem evtDetail = new JMenuItem("Event Details");
        JMenuItem evtLocation = new JMenuItem("Event Locations");
        JMenuItem evtBooking = new JMenuItem("Booking");
        JMenuItem evtRrsv = new JMenuItem("Reservation Details");
        JMenuItem profile = new JMenuItem("Profile");
        JMenuItem logout = new JMenuItem("Log out");
        JMenuItem screen1 = new JMenuItem("Screen1");

        // Add items to the menu
        menu.add(home);
        menu.add(evtDetail);
        menu.add(evtLocation);
        menu.add(evtBooking);
        menu.add(evtRrsv);
        menu.add(profile);
        menu.add(logout);
        menu.add(screen1);

        menuBar.add(menu);

        // Set background and foreground colors for the menu and menu items
        menuBar.setBackground(backgroundColor);
        menuBar.setForeground(foregroundColor);
        menu.setBackground(backgroundColor);
        menu.setForeground(foregroundColor);

        // Set background and foreground for all menu items
        evtDetail.setBackground(backgroundColor);
        evtDetail.setForeground(foregroundColor);
        evtLocation.setBackground(backgroundColor);
        evtLocation.setForeground(foregroundColor);
        evtBooking.setBackground(backgroundColor);
        evtBooking.setForeground(foregroundColor);
        evtRrsv.setBackground(backgroundColor);
        evtRrsv.setForeground(foregroundColor);
        profile.setBackground(backgroundColor);
        profile.setForeground(foregroundColor);
        logout.setBackground(backgroundColor);
        logout.setForeground(foregroundColor);
        screen1.setBackground(backgroundColor);
        screen1.setForeground(foregroundColor);

        // Add action listeners for each menu item
        home.addActionListener(e -> mainFrame.getScreenManager().showPanel("Home"));
        evtDetail.addActionListener(e -> mainFrame.getScreenManager().showPanel("Events_Details"));
        evtLocation.addActionListener(e -> mainFrame.getScreenManager().showPanel("xxx"));
        evtBooking.addActionListener(e -> mainFrame.getScreenManager().showPanel("EventReservation"));
        evtRrsv.addActionListener(e -> mainFrame.getScreenManager().showPanel("xxx"));
        profile.addActionListener(e -> handleProfileAccess(mainFrame));
        logout.addActionListener(e -> handleLogout(mainFrame));
        screen1.addActionListener(e -> mainFrame.getScreenManager().showPanel("Screen1"));
    }

    // Method to handle profile access
    private void handleProfileAccess(Main mainFrame) {
        if (Session.currentUser != null) {
            String role = Session.currentUser.getRole();

            switch (role) {
                case "User" -> mainFrame.getScreenManager().showPanel("User_profile");
                case "Organizer" -> mainFrame.getScreenManager().showPanel("Organizer_profile");
                default -> {
                    JOptionPane.showMessageDialog(mainFrame, "Invalid role. Please log in again.", "Role Error", JOptionPane.ERROR_MESSAGE);
                    mainFrame.getScreenManager().showPanel("Login_form");
                }
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Please log in to access your profile.", "Session Error", JOptionPane.ERROR_MESSAGE);
            mainFrame.getScreenManager().showPanel("Login_form");
        }

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    // Method to handle logout
    private void handleLogout(Main mainFrame) {
        if (Session.currentUser != null) {
            System.out.println("Logging out user: " + Session.currentUser.getId());
        }

        // Clear session and reset profile fields
        Session.currentUser = null;

        User_profile userProfile = mainFrame.getScreenManager().getUserProfile();
        Organizer_profile organizerProfile = mainFrame.getScreenManager().getOrganizerProfile();

        if (userProfile != null) {
            userProfile.clearFields();
        }

        if (organizerProfile != null) {
            organizerProfile.clearFields();
        }

        mainFrame.getScreenManager().showPanel("Login_form");

        // Revalidate and repaint to ensure UI is updated
        mainFrame.revalidate();
        mainFrame.repaint();
        System.out.println("Session after pressing logout: " + Session.currentUser);
    }
}
