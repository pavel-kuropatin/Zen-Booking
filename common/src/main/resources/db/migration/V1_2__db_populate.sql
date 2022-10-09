-- Roles Table
insert into roles (role) values ('ROLE_ADMIN');
insert into roles (role) values ('ROLE_MODER');
insert into roles (role) values ('ROLE_USER');

-- Genders Table
insert into genders (gender) values ('MALE');
insert into genders (gender) values ('FEMALE');
insert into genders (gender) values ('UNDEFINED');

-- Property Types Table
insert into property_types (type) values ('HOUSE');
insert into property_types (type) values ('APARTMENT');
insert into property_types (type) values ('ROOM');
insert into property_types (type) values ('NOT_SPECIFIED');

-- Order Statuses Table
insert into order_statuses (status) values ('NEW');
insert into order_statuses (status) values ('CANCELLED');
insert into order_statuses (status) values ('REJECTED');
insert into order_statuses (status) values ('ACCEPTED');
insert into order_statuses (status) values ('FINISHED');
insert into order_statuses (status) values ('OUTDATED');

-- Admins Table
insert into admins (role, login, password, displayed_name, created, updated) values ('ROLE_ADMIN', 'admin',      '$2a$10$8.SPK5hPMZryFG/bPOYxZO3/F8oOoTfPKo2Em1DQrg2oouLmfBuuK', 'Administrator', timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into admins (role, login, password, displayed_name, created, updated) values ('ROLE_MODER', 'moderator1', '$2a$10$Ft3S7R7jBI51e11q4fRxKeVq5tIqDWXaOzEWVZP.yqNw7vqvttcSW', 'Moderator1',    timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into admins (role, login, password, displayed_name, created, updated) values ('ROLE_MODER', 'moderator2', '$2a$10$gyNYp1.aP5AnGB1DMWaZ0uEvFqoWTWAr0dpASMxkZGiCmJvg7vrIW', 'Moderator2',    timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));

-- Users Table
insert into users (login, password, name, surname, gender, birth_date, email, phone, balance, created, updated) values ('user1',  '$2a$10$0aShmr9YJS9z/g2OLwyh/Or9fTGnFqK58ETLkw5PUZjww.//zqYfW', 'Ivan',    'Ivanov',      'MALE',   '1967-11-01', 'ivan.i@gmail.com',      '+375441234567', 235, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into users (login, password, name, surname, gender, birth_date, email, phone, balance, created, updated) values ('user2',  '$2a$10$vV1NGI1vcor6sFA1kXUvt.1nRkFvCKRa.923uJoX63jb3pVFN8L7y', 'Petr',    'Petrov',      'MALE',   '1988-10-11', 'petr.p@gmail.com',      '+375442837405', 165, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into users (login, password, name, surname, gender, birth_date, email, phone, balance, created, updated) values ('user3',  '$2a$10$Ar8Qx.QCcRS5QhuKucOCkOjhpGxycAVu0JKWJleh/JzbkJhThBTVK', 'Anna',    'Karenina',    'FEMALE', '1987-08-23', 'maslenitsa@gmail.com',  '+375441234875', 320, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into users (login, password, name, surname, gender, birth_date, email, phone, balance, created, updated) values ('user4',  '$2a$10$OMK6g6rTRBpz4FvvUl31L.5yXnlOW0ibAGMl5FnRJL3/Bt4XdXfpi', 'Andrey',  'Luchkouski',  'MALE',   '1988-05-10', 'andrey.l@gmail.com',    '+375444637285',  25, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into users (login, password, name, surname, gender, birth_date, email, phone, balance, created, updated) values ('user5',  '$2a$10$14ms699cYj85fk3fYbFT3OaU0WqXSn8WsuSuQntsWmL1f/dnf.j1C', 'Vlad',    'Batsenka',    'MALE',   '1993-06-22', 'vlad.b@gmail.com',      '+375440182647', 160, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into users (login, password, name, surname, gender, birth_date, email, phone, balance, created, updated) values ('user6',  '$2a$10$3yw00sMZt0aBeNsg2g1lqut4JalbYueMrPYP7bfG5naQ9bLTWF4ZC', 'Dmitriy', 'Bahankou',    'MALE',   '1992-03-18', 'dima.b@gmail.com',      '+375444637285', 200, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into users (login, password, name, surname, gender, birth_date, email, phone, balance, created, updated) values ('user7',  '$2a$10$iPpopzovctpQRycXVEwIv.c68VX2FV0RYXw0B6xWN12ugPYvYcIQ2', 'Natalia', 'Petrova',     'FEMALE', '1978-05-30', 'natusik.p@gmail.com',   '+375333456789', 515, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into users (login, password, name, surname, gender, birth_date, email, phone, balance, created, updated) values ('user8',  '$2a$10$qjmfbSY/Wdk7QFz4SFHc6.W2Er3hCP42N00Bn7npeBeZtcDiQLw6y', 'Oksana',  'Petkitskaya', 'FEMALE', '1985-08-11', 'oksanka.p@gmail.com',   '+375444637285',  35, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into users (login, password, name, surname, gender, birth_date, email, phone, balance, created, updated) values ('user9',  '$2a$10$c8vQ4BxTsvEnPWO78Z48Ce0Ngtq/J102MYogn55X5kJSnjZ7TH.7W', 'Olga',    'Nekrasova',   'FEMALE', '1987-01-20', 'nekrasova.o@gmail.com', '+375293456789', 250, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into users (login, password, name, surname, gender, birth_date, email, phone, balance, created, updated) values ('user10', '$2a$10$aXNF3APsoYlErl3O3M.qn.WmL8fG3tI5tfE73qjVBNG6uitDWGDHa', 'Aleksey', 'Pobedov',     'MALE',   '1983-04-11', 'pobedoff@gmail.com',    '+375293457586', 135, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));

-- Property Table
insert into property (user_id, type, name, description, address, price, guests, rooms, beds, has_kitchen, has_washer, has_tv, has_internet, is_pets_allowed, is_available, created, updated) values (1,  'APARTMENT', 'Apartment',                   'Apartment in the middle of a nowhere', 'Belarus, Mogilevskaya, Mogilev, Fatina st. 1-123',            100, 5, 2, 3, true,  true,  false, true,  false, true, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property (user_id, type, name, description, address, price, guests, rooms, beds, has_kitchen, has_washer, has_tv, has_internet, is_pets_allowed, is_available, created, updated) values (1,  'APARTMENT', 'Just apartment',              'Apartment in the middle of a nowhere', 'Belarus, Mogilevskaya, Mogilev, Fatina st. 1-111',            85,  3, 1, 2, true,  true,  true,  true,  true,  true, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property (user_id, type, name, description, address, price, guests, rooms, beds, has_kitchen, has_washer, has_tv, has_internet, is_pets_allowed, is_available, created, updated) values (3,  'APARTMENT', 'Good choice',                 'Apartment in the middle of a nowhere', 'Belarus, Minskaya, Soligorsk, Zheleznodorozhnaya st. 24-121', 110, 5, 2, 3, true,  true,  true,  true,  false, true, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property (user_id, type, name, description, address, price, guests, rooms, beds, has_kitchen, has_washer, has_tv, has_internet, is_pets_allowed, is_available, created, updated) values (4,  'APARTMENT', 'Apartment with all you need', 'Apartment in the middle of a nowhere', 'Belarus, Brestskaya, Brest, Fatsina st. 1-123',               75,  2, 2, 1, true,  true,  true,  true,  true,  true, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property (user_id, type, name, description, address, price, guests, rooms, beds, has_kitchen, has_washer, has_tv, has_internet, is_pets_allowed, is_available, created, updated) values (6,  'APARTMENT', 'Some apartment',              'Apartment in the middle of a nowhere', 'Belarus, Minskaya, Minsk, Nikulina st. 8-85',                 60,  2, 1, 2, true,  true,  true,  true,  false, true, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property (user_id, type, name, description, address, price, guests, rooms, beds, has_kitchen, has_washer, has_tv, has_internet, is_pets_allowed, is_available, created, updated) values (7,  'APARTMENT', 'Apartment #1',                'Apartment in the middle of a nowhere', 'Belarus, Vitebskaya, Vitebsk, Pobedy sq. st. 101-58',         90,  1, 1, 1, false, false, true,  false, false, true, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property (user_id, type, name, description, address, price, guests, rooms, beds, has_kitchen, has_washer, has_tv, has_internet, is_pets_allowed, is_available, created, updated) values (9,  'ROOM',      'Room in apartment',           'Room in the middle of a nowhere',      'Belarus, Gomelskaya, Gomel, 8 Marta st. st. 12-35',           25,  1, 1, 1, true,  false, true,  false, false, true, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property (user_id, type, name, description, address, price, guests, rooms, beds, has_kitchen, has_washer, has_tv, has_internet, is_pets_allowed, is_available, created, updated) values (9,  'ROOM',      'Room in apartment',           'Room in the middle of a nowhere',      'Belarus, Gomelskaya, Gomel, 8 Marta st. st. 12-35',           25,  1, 1, 1, true,  false, true,  false, false, true, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property (user_id, type, name, description, address, price, guests, rooms, beds, has_kitchen, has_washer, has_tv, has_internet, is_pets_allowed, is_available, created, updated) values (9,  'ROOM',      'Room in apartment',           'Room in the middle of a nowhere',      'Belarus, Gomelskaya, Gomel, 8 Marta st. st. 12-35',           25,  1, 1, 1, true,  false, true,  false, false, true, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property (user_id, type, name, description, address, price, guests, rooms, beds, has_kitchen, has_washer, has_tv, has_internet, is_pets_allowed, is_available, created, updated) values (10, 'HOUSE',     'Lonely House',                'House in the middle of a nowhere',     'Belarus, Grodnenskaya, Grodno, Nezavisimosti st. 12-35',      250, 1, 1, 1, true,  false, true,  false, false, true, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));

-- Property Images Table
insert into property_images (property_id, img_url, created, updated) values (1,  'https://image-hosting/images/1/image1.png',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property_images (property_id, img_url, created, updated) values (1,  'https://image-hosting/images/1/image2.png',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property_images (property_id, img_url, created, updated) values (1,  'https://image-hosting/images/1/image3.png',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property_images (property_id, img_url, created, updated) values (2,  'https://image-hosting/images/2/image1.png',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property_images (property_id, img_url, created, updated) values (2,  'https://image-hosting/images/2/image2.png',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property_images (property_id, img_url, created, updated) values (3,  'https://image-hosting/images/3/image1.png',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property_images (property_id, img_url, created, updated) values (4,  'https://image-hosting/images/4/image1.png',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property_images (property_id, img_url, created, updated) values (5,  'https://image-hosting/images/5/image1.png',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property_images (property_id, img_url, created, updated) values (6,  'https://image-hosting/images/6/image1.png',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property_images (property_id, img_url, created, updated) values (7,  'https://image-hosting/images/7/image1.png',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property_images (property_id, img_url, created, updated) values (8,  'https://image-hosting/images/8/image1.png',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property_images (property_id, img_url, created, updated) values (9,  'https://image-hosting/images/9/image1.png',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into property_images (property_id, img_url, created, updated) values (10, 'https://image-hosting/images/10/image1.png', timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));

-- Orders Table
insert into orders (user_id, property_id, total_price, start_date, end_date, status, created, updated) values (5,  1, 400, '2021-07-01', '2021-07-04', 'CANCELLED', timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into orders (user_id, property_id, total_price, start_date, end_date, status, created, updated) values (5,  2, 255, '2021-07-01', '2021-07-03', 'CANCELLED', timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into orders (user_id, property_id, total_price, start_date, end_date, status, created, updated) values (5,  1, 300, '2021-07-01', '2021-07-03', 'FINISHED',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into orders (user_id, property_id, total_price, start_date, end_date, status, created, updated) values (4,  1, 200, '2021-07-05', '2021-07-06', 'FINISHED',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into orders (user_id, property_id, total_price, start_date, end_date, status, created, updated) values (1,  4,  75, '2021-07-02', '2021-07-02', 'FINISHED',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into orders (user_id, property_id, total_price, start_date, end_date, status, created, updated) values (8,  5, 120, '2021-07-17', '2021-07-18', 'REJECTED',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into orders (user_id, property_id, total_price, start_date, end_date, status, created, updated) values (8, 10, 500, '2021-07-17', '2021-07-18', 'FINISHED',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into orders (user_id, property_id, total_price, start_date, end_date, status, created, updated) values (9,  3, 110, '2021-07-07', '2021-07-07', 'FINISHED',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into orders (user_id, property_id, total_price, start_date, end_date, status, created, updated) values (2,  6, 180, '2021-08-01', '2021-08-02', 'FINISHED',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into orders (user_id, property_id, total_price, start_date, end_date, status, created, updated) values (7,  2, 425, '2021-08-01', '2021-08-05', 'FINISHED',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into orders (user_id, property_id, total_price, start_date, end_date, status, created, updated) values (9,  1, 300, '2021-08-02', '2021-08-04', 'FINISHED',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into orders (user_id, property_id, total_price, start_date, end_date, status, created, updated) values (3,  9, 125, '2021-08-03', '2021-08-07', 'FINISHED',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into orders (user_id, property_id, total_price, start_date, end_date, status, created, updated) values (5, 10, 250, '2021-08-07', '2021-08-07', 'FINISHED',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into orders (user_id, property_id, total_price, start_date, end_date, status, created, updated) values (1,  9, 175, '2021-08-02', '2021-08-08', 'FINISHED',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into orders (user_id, property_id, total_price, start_date, end_date, status, created, updated) values (6,  1, 500, '2021-08-05', '2021-08-09', 'FINISHED',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into orders (user_id, property_id, total_price, start_date, end_date, status, created, updated) values (9,  3, 440, '2021-08-03', '2021-08-06', 'FINISHED',  timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into orders (user_id, property_id, total_price, start_date, end_date, status, created, updated) values (5,  3, 220, '2021-09-20', '2021-09-21', 'NEW',       timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into orders (user_id, property_id, total_price, start_date, end_date, status, created, updated) values (9,  1, 100, '2021-09-07', '2021-09-07', 'NEW',       timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));

-- Reviews Table
insert into reviews (order_id, summary, description, rating, is_deleted, created, updated) values (3, 'Good', 'Good description', 4, false, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into reviews (order_id, summary, description, rating, is_deleted, created, updated) values (4, 'Average', 'Average description', 3, false, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into reviews (order_id, summary, description, rating, is_deleted, created, updated) values (5, 'Very good', 'Very good description', 4, false, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into reviews (order_id, summary, description, rating, is_deleted, created, updated) values (7, 'Bad', 'Bad description', 2, false, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into reviews (order_id, summary, description, rating, is_deleted, created, updated) values (8, 'Good', 'Good description', 4, false, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into reviews (order_id, summary, description, rating, is_deleted, created, updated) values (9, 'Average', 'Average description', 3, false, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into reviews (order_id, summary, description, rating, is_deleted, created, updated) values (10, 'Very good', 'Very good description', 5, false, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into reviews (order_id, summary, description, rating, is_deleted, created, updated) values (11, 'Good', 'Good description', 4, false, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into reviews (order_id, summary, description, rating, is_deleted, created, updated) values (12, 'Bad', 'Bad description', 2, false, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into reviews (order_id, summary, description, rating, is_deleted, created, updated) values (13, 'Good', 'Good description', 4, false, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into reviews (order_id, summary, description, rating, is_deleted, created, updated) values (14, 'Average', 'Average description', 3, false, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into reviews (order_id, summary, description, rating, is_deleted, created, updated) values (15, 'Very good', 'Very good description', 5, false, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
insert into reviews (order_id, summary, description, rating, is_deleted, created, updated) values (16, 'Very bad', 'Very bad description', 1, false, timezone('Europe/Minsk', now()), timezone('Europe/Minsk', now()));
