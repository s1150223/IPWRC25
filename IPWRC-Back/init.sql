-- Create a table for cars
CREATE TABLE IF NOT EXISTS product (
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    year INT NOT NULL,
    type VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    image TEXT NOT NULL
    );

-- Insert initial data into the cars table
INSERT INTO product (name, description, year, type, price, image) VALUES
                                                            ('Tesla Model 3', '2025 • Electric • Automatic', 2025, 'Electric', 45900, ''),
                                                            ('BMW iX', '2025 • Electric • Automatic', 2025, 'Electric', 89900, ''),
                                                            ('Porsche Taycan', '2025 • Electric • Automatic', 2025, 'Electric', 109900, ''),
                                                            ('Mercedes EQS', '2025 • Electric • Automatic', 2025, 'Electric', 125900, '');
