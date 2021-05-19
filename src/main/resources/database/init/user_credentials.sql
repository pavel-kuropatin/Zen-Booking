drop table if exists user_credentials;

create table user_credentials(
    id         bigserial                   not null constraint user_credentials_pk primary key,
    user_id    bigint                      not null constraint user_credentials_user_id_fk references users,
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

alter table user_credentials
    owner to username;

create unique index user_credentials_id_uindex
    on user_credentials (id);

create unique index user_credentials_user_id_uindex
    on user_credentials (user_id);

create index user_credentials_name_index
    on user_credentials (name);

create index user_credentials_surname_index
    on user_credentials (surname);

create index user_credentials_birth_date_index
    on user_credentials (birth_date);

create index user_credentials_email_index
    on user_credentials (email);

create index user_credentials_phone_index
    on user_credentials (phone);

create index user_credentials_is_banned_index
    on user_credentials (is_banned);

create index user_credentials_is_deleted_index
    on user_credentials (is_deleted);

create trigger created_trigger
    before insert on user_credentials
    for each row
    execute procedure set_timestamp_created();

create trigger updated_trigger
    before update on user_credentials
    for each row
    execute procedure set_timestamp_updated();

insert into user_credentials values (default, 4,    'Ivan',     'Ivanov', '1967-11-01',    'ivan.i@gmail.com', '+375441234567', default, default, default, default);
insert into user_credentials values (default, 5, 'Dmitriy', 'Bogatyryov', '1988-10-11', 'dmitriy.b@gmail.com', '+375442837405', default, default, default, default);
insert into user_credentials values (default, 6, 'Vasiliy',    'Pushkin', '1987-08-23', 'vasiliy.p@gmail.com', '+375441234875', default, default, default, default);
insert into user_credentials values (default, 7,  'Andrey', 'Luchkouski', '1988-05-10',  'andrey.l@gmail.com', '+375444637285', default, default, default, default);
insert into user_credentials values (default, 8,    'Vlad',   'Batsenka', '1993-06-22',    'vlad.b@gmail.com', '+375440182647', default, default, default, default);
insert into user_credentials values (default, 9,  'Dmitry',   'Bahankou', '1992-03-18',    'dima.b@gmail.com', '+375444637285', default, default, default, default);