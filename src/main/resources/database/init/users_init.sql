drop table if exists users;

create table users(
    id               bigserial                                      not null constraint users_pk primary key,
    login            varchar(32)                                    not null,
    password         varchar(32)                                    not null,
    name             varchar(32)                                    not null,
    surname          varchar(32)                                    not null,
    birth_date       date                                           not null,
    email            varchar(64)                                    not null,
    phone            varchar(32)                                    not null,
    personal_account varchar(32)  default random_personal_account() not null,
    banned           boolean      default false                     not null,
    deleted          boolean      default false                     not null,
    created          timestamp(6)                                   not null,
    updated          timestamp(6)                                   not null
);

alter table users
    owner to username;

create unique index users_id_uindex
    on users (id);

create unique index users_login_uindex
    on users (login);

create index users_name_index
    on users (name);

create index users_surname_index
    on users (surname);

create index users_birth_date_index
    on users (birth_date);

create index users_email_index
    on users (email);

create index users_phone_index
    on users (phone);

create index users_personal_account_index
    on users (personal_account);

create index users_banned_index
    on users (banned);

create index users_deleted_index
    on users (deleted);

create trigger created_trigger
    before insert on users
    for each row
    execute procedure set_timestamp_created();

create trigger updated_trigger
    before update on users
    for each row
    execute procedure set_timestamp_updated();

insert into users values (default, 'user1', 'password1',   'Ivan',     'Ivanov', '1967-11-01',    'ivan.i@gmail.com', '+375441234567', default, default, default, default, default);
insert into users values (default, 'user2', 'password2',   'Petr',     'Petrov', '1988-10-11', 'dmitriy.b@gmail.com', '+375442837405', default, default, default, default, default);
insert into users values (default, 'user3', 'password3',   'Anna',   'Karenina', '1987-08-23', 'vasiliy.p@gmail.com', '+375441234875', default, default, default, default, default);
insert into users values (default, 'user4', 'password4', 'Andrey', 'Luchkouski', '1988-05-10',  'andrey.l@gmail.com', '+375444637285', default, default, default, default, default);
insert into users values (default, 'user5', 'password5',   'Vlad',   'Batsenka', '1993-06-22',    'vlad.b@gmail.com', '+375440182647', default, default, default, default, default);
insert into users values (default, 'user6', 'password6', 'Dmitry',   'Bahankou', '1992-03-18',    'dima.b@gmail.com', '+375444637285', default, default, default, default, default);