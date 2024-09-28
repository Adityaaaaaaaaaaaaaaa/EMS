package utility;

import app.Main;
import session.Session;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.Color;

public interface MenuInterface {

    // Method to initialize the menu and add listeners
    default void initializeMenu(JMenuBar menuBar, Main mainFrame, Color backgroundColor, Color foregroundColor) {
        // Clear the existing menu items
        menuBar.removeAll();

        JMenu menu = new JMenu("Menu");

        // Create menu items
        JMenuItem home = new JMenuItem("Home");
        JMenuItem evtDetail = new JMenuItem("Event Details");
        JMenuItem evtLocation = new JMenuItem("Event Locations");
        JMenuItem evtBooking = new JMenuItem("Booking");
        JMenuItem evtRrsv = new JMenuItem("Booking Details");
        JMenuItem profile = new JMenuItem("Profile");
        JMenuItem logout = new JMenuItem("Log out");

        // Add items to the menu
        menu.add(home);
        menu.add(evtDetail);
        menu.add(evtLocation);
        //menu.add(evtBooking);

        // Conditionally add "Booking Details" only for Organizer role
        if (Session.currentUser != null && "User".equals(Session.currentUser.getRole())) {
            menu.add(evtBooking);
        }

        // Conditionally add "Booking Details" only for Organizer role
        if (Session.currentUser != null && "Organizer".equals(Session.currentUser.getRole())) {
            menu.add(evtRrsv);
        }

        menu.add(profile);
        menu.add(logout);

        menuBar.add(menu);

        // Set background and foreground colors for the menu and menu items
        menuBar.setBackground(backgroundColor);
        menuBar.setForeground(foregroundColor);
        menu.setBackground(backgroundColor);
        menu.setForeground(foregroundColor);

        // Add action listeners for each menu item
        home.addActionListener(e -> mainFrame.getScreenManager().showPanel("Home"));
        evtDetail.addActionListener(e -> mainFrame.getScreenManager().showPanel("Event_detail"));
        evtLocation.addActionListener(e -> mainFrame.getScreenManager().showPanel("Event_Location"));
        //evtBooking.addActionListener(e -> mainFrame.getScreenManager().showPanel("Booking"));

        // Add action listener for booking details only if it was added
        if (evtBooking.getParent() != null) {
            evtBooking.addActionListener(e -> mainFrame.getScreenManager().showPanel("Booking"));
        }

        // Add action listener for booking details only if it was added
        if (evtRrsv.getParent() != null) {
            evtRrsv.addActionListener(e -> mainFrame.getScreenManager().showPanel("Booking_Details"));
        }

        profile.addActionListener(e -> handleProfileAccess(mainFrame));
        logout.addActionListener(e -> handleLogout(mainFrame));

        // Revalidate and repaint the menu to ensure it's refreshed properly
        menuBar.revalidate();
        menuBar.repaint();
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

        mainFrame.getScreenManager().showPanel("Login_form");

        // Revalidate and repaint to ensure UI is updated
        mainFrame.revalidate();
        mainFrame.repaint();
        System.out.println("Session after pressing logout: " + Session.currentUser);
    }
}
