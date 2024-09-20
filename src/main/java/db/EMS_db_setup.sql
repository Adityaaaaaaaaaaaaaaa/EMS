-- Create the database (if not exists)
CREATE DATABASE IF NOT EXISTS Event_Management_System;
USE Event_Management_System;

-- Drop existing tables (optional, for a fresh start)
DROP TABLE IF EXISTS Booking, Users, EventDetails, Location;

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

-- Create Bookings table with improved naming conventions
CREATE TABLE Booking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255)               NOT NULL,
    Event VARCHAR(100)              NOT NULL,
    NumGuest INT                    NOT NULL,
    Location VARCHAR(100)           NOT NULL,
    ReservationDate DATE            NOT NULL,
    AdditionalInfo VARCHAR(255),
    PaymentMethod VARCHAR(100)      NOT NULL,
    Price INT                       NOT NULL
);

-- Create EventDetails table
CREATE TABLE EventDetails (
    id INT AUTO_INCREMENT PRIMARY KEY,
    Title VARCHAR(255) NOT NULL,
    Description VARCHAR(500) NOT NULL,
    Pricing INT NOT NULL
);

-- Create Location table
CREATE TABLE Location (
    id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(150)                  NOT NULL,
    Description VARCHAR(250)           NOT NULL,
    Address VARCHAR(200)               NOT NULL,
    Attendees VARCHAR(100)             NOT NULL
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

-- Insert 5 sample bookings
INSERT INTO Booking (Name, Event, NumGuest, Location, ReservationDate, AdditionalInfo, PaymentMethod, Price)
VALUES  ('John Doe', 'Wedding', 150, 'Beach Resort', '2024-09-20', 'Vegan Menu Required', 'Credit Card', 120000.50),
        ('Alice Smith', 'Conference', 75, 'City Hall', '2024-10-01', 'Projector Needed', 'Direct Debit', 56000.00),
        ('Bob Johnson', 'Birthday Party', 50, 'Private Villa', '2024-09-30', 'Surprise Cake', 'My.t Money', 35000.00),
        ('Samantha Lee', 'Engagement', 100, 'Hotel Ballroom', '2024-11-15', 'Special Decoration Required', 'MCB Juice', 90000.75),
        ('Michael Brown', 'Conference', 200, 'Convention Center', '2024-12-05', 'Extra Audio Equipment', 'Credit Card', 145000.00);

INSERT INTO EventDetails (Title, Description, Pricing)
VALUES  ('Wedding','Our wedding event package includes full-service planning, venue setup, catering, decoration, photography, and music. Choose from a variety of themes to create your dream wedding. Additional services include floral arrangements, custom menus, and guest management.',125000),
        ('Birthday','Our birthday event package provides venue decoration, catering, custom cakes, entertainment, and photography. We offer a variety of party themes for children and adults, including balloon decorations, game setups, and personalized birthday experiences.',1500),
        ('Conference','Our conference event package includes venue rental, audiovisual equipment, stage setup, catering, and seating arrangements. We also offer support for registration management, guest speakers, and workshop facilitation. High-speed internet access and tech support are included.',3000);


-- Locations

INSERT INTO Location (Name, Description, Address, Attendees)
VALUES
    ('Le Chateau Wedding and Birthday Hall', 'Luxurious indoor venue with elegant decor, customizable seating, and modern lighting.', 'Chateau Mon Desir, Balaclava, Mauritius', 500),
    ('Pamplemousses Event Garden', 'Scenic outdoor venue with tropical plants, perfect for weddings and parties.', 'Pamplemousses Botanical Garden, Pamplemousses, Mauritius', 400),
    ('Caudan Art Centre', 'Modern conference hall with AV systems, ideal for corporate events.', 'Caudan Arts Centre, Port Louis, Mauritius', 200);
