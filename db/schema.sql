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
