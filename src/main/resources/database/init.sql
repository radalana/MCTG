CREATE DATABASE mctg;
--GRANT ALL PRIVILEGES ON DATABASE dist TO swenuser;
\c mctg
CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE TABLE users (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR ( 255 ) NOT NULL UNIQUE ,
    password VARCHAR (255 ) NOT NULL,
    token VARCHAR ( 255 ),
    coins INTEGER DEFAULT 20,
    last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE packages
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    price INTEGER DEFAULT 5
);

CREATE TABLE stacks
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE cards
(
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) not null,
    type VARCHAR(10) not null CHECK ( type IN ('monster', 'spell')),
    subtype VARCHAR(50) CHECK (
        (type = 'monster' AND subtype IN ('elf', 'wizard', 'dragon', 'goblin', 'ork', 'kraken', 'knight'))
            OR (type = 'spell' AND subtype IS NULL)
        ),
    damage DOUBLE PRECISION NOT NULL,
    element varchar(255) CHECK ( element IN ('water', 'fire', 'normal')),
    package_id INTEGER,
    FOREIGN KEY (package_id) REFERENCES packages(id) ON DELETE SET NULL,
    stack_id INTEGER UNIQUE DEFAULT NULL,
    FOREIGN KEY (stack_id) REFERENCES stacks(id) ON DELETE SET NULL
);

INSERT INTO users (username, password, token)
VALUES
    ('john_doe', 'password123', 'token_john'),
    ('jane_smith', 'securePass456', 'token_jane'),
    ('michael_lee', 'myPassword789', 'token_michael'),
    ('emily_watson', 'passEmily@321', 'token_emily'),
    ('david_jones', 'david!pass987', 'token_david');