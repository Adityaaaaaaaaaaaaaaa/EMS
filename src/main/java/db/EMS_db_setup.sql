-- Create the database (if not exists)
CREATE DATABASE IF NOT EXISTS Event_Management_System;
USE Event_Management_System;

-- Drop existing tables (optional, for a fresh start)
DROP TABLE IF EXISTS Booking, Users;

-- Create Users table
CREATE TABLE Users
(
    user_id     INT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50) UNIQUE                  NOT NULL,
    email       VARCHAR(100)                        NOT NULL,
    password    VARCHAR(255)                        NOT NULL,
    role        ENUM ('User', 'Organizer')          NOT NULL,
    name        VARCHAR(100),
    address     VARCHAR(255),
    phone       VARCHAR(15)
);

-- Create Bookings table
CREATE TABLE Booking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Event VARCHAR(100) NOT NULL,
    reservationDate DATE NOT NULL,
    EventSize INT NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(100) NOT NULL
);

-- Create Location table
CREATE TABLE Location (
    id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(150) NOT NULL,
    Description VARCHAR(250) NOT NULL,
    Address VARCHAR(200) NOT NULL,
    Attendees VARCHAR(100) NOT NULL
);

-- Insert test data into Users table
-- 4 Organisers
INSERT INTO Users (username, email, password, role, name, address, phone)
VALUES ('Adi', 'adi@admin.com', 'adi1234', 'Organizer', 'Adityaaaaaa', 'Port-louis', '1234567890'),
       ('Aqsaa', 'aqsaa@admin.com', 'aqsaa1234', 'Organizer', 'Aqsaa', 'Triolet', '1234567891'),
       ('Aliyah', 'aliyah@admin.com', 'aliyah1234', 'Organizer', 'Aliyah', 'Triolet', '1234567892'),
       ('Janvi', 'janvi@admin.com', 'janvi1234', 'Organizer', 'Janvi', 'Mar D\'albert', '1234567893');

-- Users
INSERT INTO Users (username, email, password, role, name, address, phone)
VALUES ('John01', 'john@gmail.com', 'john2024', 'User', 'John Doe', '12, Netflix Avenue, Colombia Pictures', '5673 3673'),
       ('Bob75', 'bob@gmail.com', 'bob12345', 'User', 'Bob The Builder', 'Construction st, Helicopter', '5783 3783'),
       ('Tom2024', 'tom@gmail.com', 'jerry2001', 'User', 'Tom and Jerry', 'Tom Mansion, idk where', '5783 3723'),
       ('eventmaster', 'contact@eventmaster.com', 'master1234', 'User', 'Event Master', '123, Main Street, Cityville', '5678 9101'),
       ('plannerpros', 'info@plannerpros.com', 'planpros567', 'User', 'Planner Pros', '45, Organizer Blvd, Plannertown', '5678 9102'),
       ('partycentral', 'hello@partycentral.com', 'partytime987', 'User', 'Party Central', '789, Fun Street, Festivity City', '5678 9103'),
       ('celebrationhub', 'admin@celebrationhub.com', 'celebrate2024', 'User', 'Celebration Hub', '67, Joy Avenue, Happyville', '5678 9104'),
       ('eventsquared', 'team@eventsquared.com', 'square9876', 'User', 'Event Squared', '90, Corporate Plaza, Busytown', '5678 9105'),
       ('stellarparties', 'book@stellarparties.com', 'stellars123', 'User', 'Stellar Parties', '12, Galaxy Lane, Starcity', '5678 9106'),
       ('grandgala', 'support@grandgala.com', 'grandgal123', 'User', 'Grand Gala', '88, Luxurious Blvd, Poshville', '5678 9107'),
       ('eventcrafters', 'services@eventcrafters.com', 'craft12345', 'User', 'Event Crafters', '22, Artisan Rd, Craftsville', '5678 9108'),
       ('epicvenues', 'venues@epicvenues.com', 'epic7890', 'User', 'Epic Venues', '11, Venue St, Arenatown', '5678 9109'),
       ('festivityplus', 'contact@festivityplus.com', 'festplus678', 'User', 'Festivity Plus', '55, Festival Ave, Celebratown', '5678 9110');

-- Locations
INSERT INTO Location (Name, Description, Address, Attendees)
VALUES
('Le Chateau Wedding and Birthday Hall (Indoor)', 'A luxurious indoor venue for weddings and birthdays, featuring a grand hall, elegant decor, customizable seating, and modern lighting. Ideal for both intimate and large events with dedicated staff.', 'Chateau Mon Desir, Balaclava, Mauritius', '500'),
('Pamplemousses Event Garden (Outdoor)', 'A scenic outdoor venue in the Pamplemousses Botanical Garden, ideal for weddings and birthday parties. With ample guest space and a picturesque setting among tropical plants, it offers a natural, open-air environment for any celebration.', 'Pamplemousses Botanical Garden, Pamplemousses, Mauritius', '400'),
('Caudan Conference Centre (Indoor)', 'A modern conference hall at Caudan Arts Centre, Port Louis. Perfect for corporate events with seating for 500, advanced AV systems, and a stage for presentations. Support staff and catering available.', 'Caudan Arts Centre, Port Louis, Mauritius', '200');

