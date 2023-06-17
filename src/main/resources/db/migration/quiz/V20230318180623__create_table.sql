CREATE TABLE quiz
(
    id         SERIAL PRIMARY KEY,
    user_id    INTEGER REFERENCES usr (id),
    status     VARCHAR(20) NOT NULL,
    created_at TIMESTAMP   NOT NULL,
    updated_at TIMESTAMP   NOT NULL
)