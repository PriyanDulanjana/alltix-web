CREATE DATABASE IF NOT EXISTS alltix_db;
USE alltix_db;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'USER'
);

-- Create movies table
CREATE TABLE IF NOT EXISTS movies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    duration VARCHAR(50),
    language VARCHAR(50),
    description TEXT,
    poster_url VARCHAR(500),
    show_time DATETIME,
    ticket_price DECIMAL(10,2),
    available_seats INT
);

-- Create bookings table
CREATE TABLE IF NOT EXISTS bookings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    movie_id BIGINT,
    number_of_tickets INT,
    total_amount DECIMAL(10,2),
    booking_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'CONFIRMED',
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
);

-- Insert sample data
INSERT INTO users (name, email, password, role) VALUES
('Admin User', 'admin@alltix.com', 'admin123', 'ADMIN'),
('John Doe', 'john@example.com', 'password123', 'USER'),
('Jane Smith', 'jane@example.com', 'password123', 'USER');

INSERT INTO movies (title, genre, duration, language, description, poster_url, show_time, ticket_price, available_seats) VALUES
('Avengers: Endgame', 'Action', '3h 1m', 'English', 'The epic conclusion to the Infinity Saga.', '/images/avengers.jpg', '2024-01-15 19:00:00', 15.99, 100),
('The Batman', 'Action', '2h 56m', 'English', 'The Dark Knight of Gotham City.', '/images/batman.jpg', '2024-01-16 20:30:00', 14.99, 80),
('Spider-Man: No Way Home', 'Action', '2h 28m', 'English', 'The multiverse unleashed.', '/images/spiderman.jpg', '2024-01-17 18:00:00', 13.99, 120),
('Dune', 'Sci-Fi', '2h 35m', 'English', 'A mythic and emotionally charged hero''s journey.', '/images/dune.jpg', '2024-01-18 21:00:00', 16.99, 90);