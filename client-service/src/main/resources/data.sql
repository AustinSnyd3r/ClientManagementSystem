-- Ensure the 'patient' table exists
CREATE TABLE IF NOT EXISTS users
(
    id              UUID PRIMARY KEY,
    first_name            VARCHAR(255)        NOT NULL,
    last_name            VARCHAR(255)        NOT NULL,
    email           VARCHAR(255) UNIQUE NOT NULL,
    address         VARCHAR(255)        NOT NULL,
    date_of_birth   DATE                NOT NULL,
    registration_date DATE                NOT NULL
    );

-- Insert well-known UUIDs for specific patients
INSERT INTO users (id, first_name, last_name, email, address, date_of_birth, registration_date)
SELECT '123e4567-e89b-12d3-a456-426614174000',
       'Larry',
       'David',
       'larry.david@example.com',
       '123 Main St, Springfield',
       '1985-06-15',
       '2024-01-10'
    WHERE NOT EXISTS (SELECT 1
                  FROM users
                  WHERE id = '123e4567-e89b-12d3-a456-426614174000');
