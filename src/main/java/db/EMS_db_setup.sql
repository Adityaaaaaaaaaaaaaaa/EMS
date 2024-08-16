-- Create the database (if not exists)
CREATE DATABASE IF NOT EXISTS Event_Management_System;
USE Event_Management_System;

-- Drop existing tables (optional, for a fresh start)
DROP TABLE IF EXISTS Payments, Bookings, Events, Locations, Users;

-- Create Users table
CREATE TABLE Users
(
    user_id     INT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50) UNIQUE                  NOT NULL,
    email       VARCHAR(100) UNIQUE                 NOT NULL,
    password    VARCHAR(255)                        NOT NULL,
    role        ENUM ('User', 'Organizer', 'Admin') NOT NULL,
    name        VARCHAR(100),
    address     VARCHAR(255),
    phone       VARCHAR(15),
    profile_pic VARCHAR(255)
);

-- Create Locations table
CREATE TABLE Locations
(
    location_id INT AUTO_INCREMENT PRIMARY KEY,
    venue_name  VARCHAR(100)               NOT NULL,
    address     VARCHAR(255)               NOT NULL,
    facilities  TEXT,
    type        ENUM ('Public', 'Private') NOT NULL
);

-- Create Events table
CREATE TABLE Events
(
    event_id     INT AUTO_INCREMENT PRIMARY KEY,
    event_name   VARCHAR(100)                 NOT NULL,
    description  TEXT,
    organizer_id INT,
    location_id  INT,
    date_time    DATETIME                     NOT NULL,
    duration     TIME,
    capacity     INT,
    price        DECIMAL(10, 2),
    status       ENUM ('Available', 'Booked') NOT NULL,
    FOREIGN KEY (organizer_id) REFERENCES Users (user_id),
    FOREIGN KEY (location_id) REFERENCES Locations (location_id)
);

-- Create Bookings table
CREATE TABLE Bookings
(
    booking_id   INT AUTO_INCREMENT PRIMARY KEY,
    event_id     INT,
    user_id      INT,
    booking_date DATETIME                                NOT NULL,
    status       ENUM ('Paid', 'Pending', 'Unconfirmed') NOT NULL,
    FOREIGN KEY (event_id) REFERENCES Events (event_id),
    FOREIGN KEY (user_id) REFERENCES Users (user_id)
);

-- Create Payments table
CREATE TABLE Payments
(
    payment_id     INT AUTO_INCREMENT PRIMARY KEY,
    booking_id     INT,
    amount         DECIMAL(10, 2) NOT NULL,
    payment_date   DATETIME       NOT NULL,
    payment_method VARCHAR(50),
    FOREIGN KEY (booking_id) REFERENCES Bookings (booking_id)
);

-- Insert test data into Users table
-- 5 Admins
INSERT INTO Users (username, email, password, role, name, address, phone)
VALUES ('A-Adi', 'adi@admin.com', 'adi1234', 'Admin', 'Adityaaaaaa', 'Port-louis', '1234567890'),
       ('A-Aqsaa', 'aqsaa@admin.com', 'aqsaa1234', 'Admin', 'Aqsaa', 'Triolet', '1234567891'),
       ('A-Aliya', 'aliya@admin.com', 'aliya1234', 'Admin', 'Aliya', 'Triolet', '1234567892'),
       ('A-Janvi', 'janvi@admin.com', 'janvi1234', 'Admin', 'Janvi', 'Mar D\'albert', '1234567893');

-- 3 Users
INSERT INTO Users (username, email, password, role, name, address, phone)
VALUES ('U-John01', 'john@gmail.com', 'john2024', 'User', 'John Doe', '12, Netflix Avenue, Colombia Pictures', '5673 3673'),
       ('U-Bob75', 'bob@gmail.com', 'bob12345', 'User', 'Bob The Builder', 'Construction st, Helicopter', '5783 3783'),
       ('U-Tom2024', 'tom@gmail.com', 'jerry2001', 'User', 'Tom and Jerry', 'Tom Mansion, idk where', '5783 3723');

-- 10 Organizers
-- Insert test data into Users table (Organizers with more realistic details)
INSERT INTO Users (username, email, password, role, name, address, phone)
VALUES ('O-eventmaster', 'contact@eventmaster.com', 'master1234', 'Organizer', 'Event Master', '123, Main Street, Cityville', '5678 9101'),
       ('O-plannerpros', 'info@plannerpros.com', 'planpros567', 'Organizer', 'Planner Pros', '45, Organizer Blvd, Plannertown', '5678 9102'),
       ('O-partycentral', 'hello@partycentral.com', 'partytime987', 'Organizer', 'Party Central', '789, Fun Street, Festivity City', '5678 9103'),
       ('O-celebrationhub', 'admin@celebrationhub.com', 'celebrate2024', 'Organizer', 'Celebration Hub', '67, Joy Avenue, Happyville', '5678 9104'),
       ('O-eventsquared', 'team@eventsquared.com', 'square9876', 'Organizer', 'Event Squared', '90, Corporate Plaza, Busytown', '5678 9105'),
       ('O-stellarparties', 'book@stellarparties.com', 'stellars123', 'Organizer', 'Stellar Parties', '12, Galaxy Lane, Starcity', '5678 9106'),
       ('O-grandgala', 'support@grandgala.com', 'grandgal123', 'Organizer', 'Grand Gala', '88, Luxurious Blvd, Poshville', '5678 9107'),
       ('O-eventcrafters', 'services@eventcrafters.com', 'craft12345', 'Organizer', 'Event Crafters', '22, Artisan Rd, Craftsville', '5678 9108'),
       ('O-epicvenues', 'venues@epicvenues.com', 'epic7890', 'Organizer', 'Epic Venues', '11, Venue St, Arenatown', '5678 9109'),
       ('O-festivityplus', 'contact@festivityplus.com', 'festplus678', 'Organizer', 'Festivity Plus', '55, Festival Ave, Celebratown', '5678 9110');

-- Insert test data into Locations table
-- Some locations that might be used by multiple organizers or specific ones
INSERT INTO Locations (venue_name, address, facilities, type)
VALUES ('City Event Hall', '123, Event Street, Cityville', 'WiFi, Catering, Parking', 'Public'),
       ('Luxury Banquet Hall', '456, Elite Ave, Poshville', 'Private Parking, In-house Catering, Decoration Services', 'Private'),
       ('Outdoor Park Venue', '789, Nature Road, Greenfield', 'Open Air, Stage Setup, Electrical Outlets', 'Public'),
       ('Corporate Convention Center', '101, Business Plaza, Busytown', 'Conference Rooms, Projectors, WiFi', 'Private'),
       ('Starlight Party Venue', '102, Galaxy Blvd, Starcity', 'Dance Floor, Sound System, Lighting Setup', 'Public'),
       ('Grand Celebration Arena', '103, Festival Road, Happyville', 'Seating for 500, In-house Catering, Decoration Services', 'Private'),
       ('Epic Theater', '104, Venue Road, Arenatown', 'Stage, Seating, AV Setup, VIP Area', 'Public');

-- Insert test data into Events table
-- Each organizer having one or multiple events
INSERT INTO Events (event_name, description, organizer_id, location_id, date_time, duration, capacity, price, status)
VALUES ('Corporate Gala', 'An elegant gala dinner for corporate clients.', 2, 2, '2024-09-15 18:00:00', '04:00:00', 200, 150.00, 'Available'),
       ('Music Festival', 'A day-long music festival with live bands.', 3, 5, '2024-10-05 12:00:00', '08:00:00', 500, 75.00, 'Available'),
       ('Wedding Reception', 'A luxury wedding reception with all amenities.', 4, 2, '2024-08-30 18:00:00', '05:00:00', 150, 300.00, 'Booked'),
       ('Tech Conference', 'Annual technology conference with industry leaders.', 5, 4, '2024-11-20 09:00:00', '08:00:00', 300, 100.00, 'Available'),
       ('Community Picnic', 'An outdoor picnic for local community members.', 6, 3, '2024-09-10 10:00:00', '06:00:00', 200, 0.00, 'Available'),
       ('New Year\'s Eve Party', 'A grand celebration to welcome the new year.', 7, 6, '2024-12-31 20:00:00', '06:00:00', 500, 200.00, 'Available'),
       ('Charity Auction', 'An auction event to raise funds for charity.', 8, 2, '2024-10-25 18:00:00', '03:00:00', 150, 50.00, 'Available'),
       ('Epic Concert', 'A live concert featuring top artists.', 9, 7, '2024-11-10 19:00:00', '05:00:00', 700, 120.00, 'Booked'),
       ('Festival Parade', 'A parade to celebrate the local culture.', 10, 3, '2024-09-25 10:00:00', '04:00:00', 1000, 0.00, 'Available');