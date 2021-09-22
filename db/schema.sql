CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    email    VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE items
(
    id          SERIAL PRIMARY KEY,
    description VARCHAR(2000) NOT NULL,
    created     TIMESTAMP     NOT NULL,
    done        BOOLEAN       NOT NULL,
    user_id     INT           NOT NULL REFERENCES users (id)
);
