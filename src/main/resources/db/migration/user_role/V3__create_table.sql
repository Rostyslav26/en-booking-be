CREATE TABLE user_role
(
    user_id INT,
    role_id VARCHAR,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES usr (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);