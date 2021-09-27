CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    email    VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE categories
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

INSERT INTO categories (name)
VALUES ('Дом'),
       ('Работа'),
       ('Учёба'),
       ('Отдых'),
       ('Здоровье');

CREATE TABLE items
(
    id          SERIAL PRIMARY KEY,
    description VARCHAR(2000) NOT NULL,
    created     TIMESTAMP     NOT NULL,
    done        BOOLEAN       NOT NULL,
    user_id     INT           NOT NULL REFERENCES users (id)
);

CREATE TABLE items_categories
(
    item_id     INT NOT NULL REFERENCES items (id),
    category_id INT NOT NULL REFERENCES categories (id),
    PRIMARY KEY (item_id, category_id)
);
