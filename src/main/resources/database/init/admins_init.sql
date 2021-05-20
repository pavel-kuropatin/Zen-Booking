drop table if exists admins;

create table admins(
    id             bigserial                    not null constraint admins_pk primary key,
    role           varchar(5)   default 'MODER' not null,
    login          varchar(32)                  not null,
    password       varchar(32)                  not null,
    displayed_name varchar(32)                  not null,
    is_banned      boolean      default false   not null,
    is_deleted     boolean      default false   not null,
    created        timestamp(6)                 not null,
    updated        timestamp(6)                 not null
);

alter table admins
    owner to username;

create unique index admins_id_uindex
    on admins (id);

create index admins_role_index
    on admins (role);

create unique index admins_login_uindex
    on admins (login);

create index admins_displayed_name_index
    on admins (displayed_name);

create index admins_is_banned_index
    on admins (is_banned);

create index admins_is_deleted_index
    on admins (is_deleted);

create trigger created_trigger
    before insert on admins
    for each row
    execute procedure set_timestamp_created();

create trigger updated_trigger
    before update on admins
    for each row
    execute procedure set_timestamp_updated();

insert into admins values (default, 'ADMIN',      'admin',      'admin', 'Administrator', default, default, default, default);
insert into admins values (default, 'MODER', 'moderator1', 'moderator1',    'Moderator1', default, default, default, default);
insert into admins values (default, 'MODER', 'moderator2', 'moderator2',    'Moderator2', default, default, default, default);