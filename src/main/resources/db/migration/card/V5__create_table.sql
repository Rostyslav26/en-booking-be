CREATE TABLE card
(
    id         SERIAL PRIMARY KEY,
    question   TEXT                           NOT NULL,
    answer     TEXT                           NOT NULL,
    author_id  INTEGER REFERENCES "user" (id) NOT NULL,
    created_at TIMESTAMP                      NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP                      NOT NULL DEFAULT NOW()
)