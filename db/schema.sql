CREATE TABLE item
(
    id          SERIAL PRIMARY KEY,
    description VARCHAR(2000) NOT NULL,
    created     TIMESTAMP     NOT NULL,
    done        BOOLEAN       NOT NULL
);
