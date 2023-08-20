CREATE TABLE user_card
(
    card_id INT,
    user_id INT,
    learned BOOLEAN,
    favorite BOOLEAN,
    PRIMARY KEY (card_id, user_id),
    FOREIGN KEY (card_id) REFERENCES card (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);