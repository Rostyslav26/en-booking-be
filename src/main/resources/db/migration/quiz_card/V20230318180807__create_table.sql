CREATE TABLE quiz_card
(
    quiz_id INT NOT NULL,
    card_id INT NOT NULL,
    PRIMARY KEY (quiz_id, card_id),
    FOREIGN KEY (quiz_id) REFERENCES quiz (id) ON DELETE CASCADE,
    FOREIGN KEY (card_id) REFERENCES card (id) ON DELETE CASCADE
)
