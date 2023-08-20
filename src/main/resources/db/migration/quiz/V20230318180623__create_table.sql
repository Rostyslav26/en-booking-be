CREATE TABLE quiz
(
    id         SERIAL PRIMARY KEY,
    user_id    INTEGER REFERENCES users (id),
    status     VARCHAR(20) NOT NULL,
    created_at TIMESTAMP   DEFAULT NOW(),
    updated_at TIMESTAMP   DEFAULT NOW()
)