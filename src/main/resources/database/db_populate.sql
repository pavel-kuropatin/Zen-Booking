insert into public.admins (id, role, login, password, displayed_name, created, updated) values (1, 'ROLE_ADMIN', 'admin',      '12345678', 'Administrator', timezone('UTC', now()), timezone('UTC', now()));
insert into public.admins (id, role, login, password, displayed_name, created, updated) values (2, 'ROLE_MODER', 'moderator1', '12345678', 'Moderator1',    timezone('UTC', now()), timezone('UTC', now()));
insert into public.admins (id, role, login, password, displayed_name, created, updated) values (3, 'ROLE_MODER', 'moderator2', '12345678', 'Moderator2',    timezone('UTC', now()), timezone('UTC', now()));

insert into public.users (role, login, password, name, surname, gender, birth_date, email, phone, created, updated) values ('ROLE_USER', 'user1', '12345678', 'Ivan',    'Ivanov',     'MALE',   '1967-11-01', 'ivan.i@gmail.com',     '+375441234567', timezone('UTC', now()), timezone('UTC', now()));
insert into public.users (role, login, password, name, surname, gender, birth_date, email, phone, created, updated) values ('ROLE_USER', 'user2', '12345678', 'Petr',    'Petrov',     'MALE',   '1988-10-11', 'dmitriy.b@gmail.com',  '+375442837405', timezone('UTC', now()), timezone('UTC', now()));
insert into public.users (role, login, password, name, surname, gender, birth_date, email, phone, created, updated) values ('ROLE_USER', 'user3', '12345678', 'Anna',    'Karenina',   'FEMALE', '1987-08-23', 'maslenitsa@gmail.com', '+375441234875', timezone('UTC', now()), timezone('UTC', now()));
insert into public.users (role, login, password, name, surname, gender, birth_date, email, phone, created, updated) values ('ROLE_USER', 'user4', '12345678', 'Andrey',  'Luchkouski', 'MALE',   '1988-05-10', 'andrey.l@gmail.com',   '+375444637285', timezone('UTC', now()), timezone('UTC', now()));
insert into public.users (role, login, password, name, surname, gender, birth_date, email, phone, created, updated) values ('ROLE_USER', 'user5', '12345678', 'Vlad',    'Batsenka',   'MALE',   '1993-06-22', 'vlad.b@gmail.com',     '+375440182647', timezone('UTC', now()), timezone('UTC', now()));
insert into public.users (role, login, password, name, surname, gender, birth_date, email, phone, created, updated) values ('ROLE_USER', 'user6', '12345678', 'Dmitriy', 'Bahankou',   'MALE',   '1992-03-18', 'dima.b@gmail.com',     '+375444637285', timezone('UTC', now()), timezone('UTC', now()));
insert into public.users (role, login, password, name, surname, gender, birth_date, email, phone, created, updated) values ('ROLE_USER', 'user7', '12345678', 'Natalia', 'Petrova',    'FEMALE', '1978-05-30', 'natusik@gmail.com',    '+375123456789', timezone('UTC', now()), timezone('UTC', now()));

insert into public.property (id, user_id, type, name, description, country, region, city, address, price, guests, rooms, beds, has_kitchen, has_washer, has_tv, has_internet, is_pets_allowed, is_available, created, updated) VALUES (1, 1, 'APARTMENT', 'Apartment', 'Apartment in the middle of a nowhere', 'Belarus', 'Mogilevskaya oblast', 'Mogilev',   'Fatsina 1-123',             100, 5, 2, 3, true, true, true, true, false, true, timezone('UTC', now()), timezone('UTC', now()));
insert into public.property (id, user_id, type, name, description, country, region, city, address, price, guests, rooms, beds, has_kitchen, has_washer, has_tv, has_internet, is_pets_allowed, is_available, created, updated) VALUES (2, 1, 'APARTMENT', 'Apartment', 'Apartment in the middle of a nowhere', 'Belarus', 'Mogilevskaya oblast', 'Mogilev',   'Fatsina 1-111',             85,  3, 1, 2, true, true, true, true, false, true, timezone('UTC', now()), timezone('UTC', now()));
insert into public.property (id, user_id, type, name, description, country, region, city, address, price, guests, rooms, beds, has_kitchen, has_washer, has_tv, has_internet, is_pets_allowed, is_available, created, updated) VALUES (3, 3, 'APARTMENT', 'Apartment', 'Apartment in the middle of a nowhere', 'Belarus', 'Minskaya oblast',     'Soligorsk', 'Zheleznodorozhnaya 24-121', 100, 5, 2, 3, true, true, true, true, false, true, timezone('UTC', now()), timezone('UTC', now()));

insert into public.property_image (property_id, img_url, created, updated) values (1, 'https://image-hosting/images/1/image1.png', timezone('UTC', now()), timezone('UTC', now()));
insert into public.property_image (property_id, img_url, created, updated) values (1, 'https://image-hosting/images/1/image2.png', timezone('UTC', now()), timezone('UTC', now()));
insert into public.property_image (property_id, img_url, created, updated) values (1, 'https://image-hosting/images/1/image3.png', timezone('UTC', now()), timezone('UTC', now()));
insert into public.property_image (property_id, img_url, created, updated) values (2, 'https://image-hosting/images/2/image1.png', timezone('UTC', now()), timezone('UTC', now()));
insert into public.property_image (property_id, img_url, created, updated) values (2, 'https://image-hosting/images/2/image2.png', timezone('UTC', now()), timezone('UTC', now()));
insert into public.property_image (property_id, img_url, created, updated) values (3, 'https://image-hosting/images/3/image1.png', timezone('UTC', now()), timezone('UTC', now()));

-- orders

-- review