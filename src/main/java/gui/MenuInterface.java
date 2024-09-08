package gui;

import app.Main;
import session.Session;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public interface MenuInterface {

    default void initializeMenu(JMenuBar menuBar, Main mainFrame) {

        JMenu menu = new JMenu("Menu");

        JMenuItem evtDetail = new JMenuItem("Event Details");
        JMenuItem evtLocation = new JMenuItem("Event Locations");
        JMenuItem evtBooking = new JMenuItem("Booking");
        JMenuItem evtRrsv = new JMenuItem("Reservation Details");
        JMenuItem profile = new JMenuItem("Profile");
        JMenuItem logout = new JMenuItem("Log out");
        JMenuItem screen1 = new JMenuItem("Screen1");

        menu.add(evtDetail);
        menu.add(evtLocation);
        menu.add(evtBooking);
        menu.add(evtRrsv);
        menu.add(profile);
        menu.add(logout);
        menu.add(screen1);

        menuBar.add(menu);

        evtDetail.addActionListener(e -> mainFrame.getScreenManager().showPanel("xxx"));
        evtLocation.addActionListener(e -> mainFrame.getScreenManager().showPanel("xxx"));
        evtBooking.addActionListener(e -> mainFrame.getScreenManager().showPanel("EventReservation"));
        evtRrsv.addActionListener(e -> mainFrame.getScreenManager().showPanel("xxx"));
        profile.addActionListener(e -> navigateBasedOnRole(mainFrame));
        logout.addActionListener(e -> handleLogout(mainFrame));
        screen1.addActionListener(e -> mainFrame.getScreenManager().showPanel("Screen1"));
    }

    default void navigateBasedOnRole(Main mainFrame) {
        if (Session.currentUser != null) {
            String role = Session.currentUser.getRole();
            String name = Session.currentUser.getName();

            System.out.println("Navigating based on role - Role: " + role + ", Name: " + name);

            switch (role) {
                case "User" -> mainFrame.getScreenManager().showPanel("User_profile");
                case "Organizer" -> mainFrame.getScreenManager().showPanel("Organizer_profile");
                default -> mainFrame.getScreenManager().showPanel("Login_form");
            }
        } else {
            System.out.println("No user session found.");
        }

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    default void handleLogout(Main mainFrame) {
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

        mainFrame.revalidate();
        mainFrame.repaint();
        System.out.println("Session after pressing logout: " + Session.currentUser);
    }
}
