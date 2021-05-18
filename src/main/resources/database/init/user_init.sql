drop table if exists "user";

create table "user"(
    id         bigserial                   not null constraint user_pk primary key,
    role       varchar(5)   default 'USER' not null,
    login      varchar(32)                 not null,
    password   varchar(32)                 not null,
    name       varchar(32)                 not null,
    surname    varchar(32)                 not null,
    birth_date date                        not null,
    email      varchar(64)                 not null,
    phone      varchar(32)                 not null,
    is_banned  boolean      default false  not null,
    is_deleted boolean      default false  not null,
    created    timestamp(6)                not null,
    updated    timestamp(6)                not null
);

alter table "user"
    owner to username;

create unique index user_id_uindex
    on "user" (id);

create index user_role_index
    on "user" (role);

create unique index user_login_uindex
    on "user" (login);

create index user_name_index
    on "user" (name);

create index user_surname_index
    on "user" (surname);

create index user_email_index
    on "user" (email);

create index user_phone_index
    on "user" (phone);

create index user_is_banned_index
    on "user" (is_banned);

create index user_is_deleted_index
    on "user" (is_deleted);

create trigger created_trigger
    before insert on "user"
    for each row
    execute procedure set_timestamp_created();

create trigger updated_trigger
    before update on "user"
    for each row
    execute procedure set_timestamp_updated();

insert into "user" values (default, 'ADMIN',      'admin',      'admin',    'Ivan',     'Ivanov', '1967-11-01',    'ivan.i@gmail.com', '+375441234567', default, default, default, default);
insert into "user" values (default, 'MODER', 'moderator1', 'moderator1', 'Dmitriy', 'Bogatyryov', '1988-10-11', 'dmitriy.b@gmail.com', '+375442837405', default, default, default, default);
insert into "user" values (default, 'MODER', 'moderator2', 'moderator2', 'Vasiliy',    'Pushkin', '1987-08-23', 'vasiliy.p@gmail.com', '+375441234875', default, default, default, default);
insert into "user" values (default, default,      'user1',  'password1',  'Andrey', 'Luchkouski', '1988-05-10',  'andrey.l@gmail.com', '+375444637285', default, default, default, default);
insert into "user" values (default, default,      'user2',  'password2',    'Vlad',   'Batsenka', '1993-06-22',    'vlad.b@gmail.com', '+375440182647', default, default, default, default);
insert into "user" values (default, default,      'user3',  'password3',  'Dmitry',   'Bahankou', '1992-03-18',    'dima.b@gmail.com', '+375444637285', default, default, default, default);