drop table if exists client;

create table client(
    id         bigserial             not null constraint client_pk primary key,
    login      varchar(50)           not null,
    password   varchar(50)           not null,
    name       varchar(50)           not null,
    surname    varchar(50)           not null,
    birth_date date                  not null,
    email      varchar(50)           not null,
    phone      varchar(50)           not null,
    is_banned  boolean default false not null,
    is_deleted boolean default false not null,
    created    timestamp(6)          not null,
    updated    timestamp(6)          not null
);

alter table client
    owner to username;

create unique index client_id_uindex
    on client (id);

create unique index client_login_uindex
    on client (login);

create index client_name_index
    on client (name);

create index client_surname_index
    on client (surname);

create index client_birth_date_index
    on client (birth_date);

create index client_email_index
    on client (email);

create index client_phone_index
    on client (phone);

create index client_is_banned_index
    on client (is_banned);

create index client_is_deleted_index
    on client (is_deleted);

create trigger created_trigger
    before insert on client
    for each row
execute procedure set_timestamp_created();

create trigger changed_trigger
    before update on client
    for each row
execute procedure set_timestamp_updated();