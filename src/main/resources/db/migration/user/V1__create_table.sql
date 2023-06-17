CREATE TABLE usr
(
    id                 SERIAL PRIMARY KEY,
    first_name         VARCHAR(50)  NOT NULL,
    last_name          VARCHAR(50)  NOT NULL,
    email              VARCHAR(256) NOT NULL,
    password           VARCHAR(256) NOT NULL,
    image_url          VARCHAR(256),
    activated          BOOLEAN               DEFAULT FALSE,
    activation_key     VARCHAR,
    reset_key          VARCHAR,
    reset_date         TIMESTAMP,
    lang_key           VARCHAR(5)            DEFAULT 'en',
    created_by         VARCHAR(50),
    created_date       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   VARCHAR(50),
    last_modified_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX user_email_idx ON usr (email);
CREATE INDEX user_activation_key_idx ON usr (activation_key);