DELETE FROM user_meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO user_meals (id, user_id, dateTime, description, calories)
VALUES (1, 100000,'2021-01-01 08:00', 'Завтрак', 500),
       (2, 100000,'2021-01-01 14:00', 'Обед', 1000),
       (3, 100000,'2021-01-01 20:00', 'Ужин', 500),
       (4, 100001,'2021-01-02 08:00', 'Завтрак', 500),
       (5, 100001,'2021-01-02 14:00', 'Обед', 1100),
       (6, 100001,'2021-01-02 20:00', 'Ужин', 500);