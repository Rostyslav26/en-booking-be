CREATE TABLE usr_authority
(
    usr_id       INT,
    authority_id VARCHAR,
    PRIMARY KEY (usr_id, authority_id),
    FOREIGN KEY (usr_id) REFERENCES usr (id),
    FOREIGN KEY (authority_id) REFERENCES authority (id)
);