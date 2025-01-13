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
    bio TEXT,
    image varchar ( 255 ),
    last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE packages
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    price INTEGER DEFAULT 5,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
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
    user_id INTEGER DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    is_in_deck BOOLEAN DEFAULT FALSE
);

CREATE TABLE battle_requests
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE battles_results
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INT NOT NULL ,
    result INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE TABLE battles
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    rounds json,
    number_of_rounds int,
    user1_result_id INTEGER,
    user2_result_id INTEGER,
    battle_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user1_result_id) REFERENCES battles_results(id),
    FOREIGN KEY (user2_result_id) REFERENCES battles_results(id)
);

CREATE TABLE user_stats (
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    wins INT DEFAULT 0,
    losses INT DEFAULT 0,
    draws INT DEFAULT 0,
    total_battles INT DEFAULT 0,
    elo INT DEFAULT 100,
    PRIMARY KEY (user_id)
);

CREATE VIEW scoreboard AS
SELECT u.username, st.elo
FROM user_stats st
JOIN users u ON u.id = st.user_id
ORDER BY st.elo DESC;

CREATE TABLE trading_offers (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    card_id uuid,
    user_id INT,
    required_type VARCHAR(50),
    min_damage INT,
    is_closed BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (card_id) REFERENCES cards(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE trading_deals (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    offer_id uuid,
    recipient_id INT,
    card_id uuid,
    trade_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY  (offer_id) REFERENCES trading_offers(id),
    FOREIGN KEY (recipient_id) REFERENCES users(id),
    FOREIGN KEY (card_id) REFERENCES cards(id)
);

INSERT INTO users (username, password, token)
VALUES
    ('john_doe', 'password123', 'token_john'),
    ('jane_smith', 'securePass456', 'token_jane'),
    ('michael_lee', 'myPassword789', 'token_michael'),
    ('emily_watson', 'passEmily@321', 'token_emily'),
    ('david_jones', 'david!pass987', 'token_david');