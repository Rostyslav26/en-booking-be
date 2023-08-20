CREATE TABLE card
(
    id         SERIAL PRIMARY KEY,
    question   TEXT                        NOT NULL,
    answer     TEXT                        NOT NULL,
    author_id  INTEGER REFERENCES users (id) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
)