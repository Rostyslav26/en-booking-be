CREATE TABLE user_authority
(
    user_id       INT,
    authority_id VARCHAR,
    PRIMARY KEY (user_id, authority_id),
    FOREIGN KEY (user_id) REFERENCES "user" (id),
    FOREIGN KEY (authority_id) REFERENCES authority (id)
);