drop table if exists users;

create table users(
    id         bigserial                   not null constraint users_pk primary key,
    role       varchar(5)   default 'USER' not null,
    login      varchar(32)                 not null,
    password   varchar(32)                 not null,
    is_banned  boolean      default false  not null,
    is_deleted boolean      default false  not null,
    created    timestamp(6)                not null,
    updated    timestamp(6)                not null
);

alter table users
    owner to username;

create unique index users_id_uindex
    on users (id);

create index users_role_index
    on users (role);

create unique index users_login_uindex
    on users (login);

create index users_is_banned_index
    on users (is_banned);

create index users_is_deleted_index
    on users (is_deleted);

create trigger created_trigger
    before insert on users
    for each row
    execute procedure set_timestamp_created();

create trigger updated_trigger
    before update on users
    for each row
    execute procedure set_timestamp_updated();

insert into users values (default, 'ADMIN',      'admin',      'admin');
insert into users values (default, 'MODER', 'moderator1', 'moderator1');
insert into users values (default, 'MODER', 'moderator2', 'moderator2');
insert into users values (default, default,      'user1',  'password1');
insert into users values (default, default,      'user2',  'password2');
insert into users values (default, default,      'user3',  'password3');
insert into users values (default, default,      'user4',  'password4');
insert into users values (default, default,      'user5',  'password5');
insert into users values (default, default,      'user6',  'password6');