drop table if exists admins;

create table admins(
    id             bigserial                      not null constraint admins_pk primary key,
    role           varchar(5)     default 'MODER' not null,
    login          varchar(32)                    not null,
    password       varchar(32)                    not null,
    displayed_name varchar(32)                    not null,
    suspended      boolean        default false   not null,
    deleted        boolean        default false   not null,
    created        timestamptz(6)                 not null,
    updated        timestamptz(6)                 not null
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

create index admins_suspended_index
    on admins (suspended);

create index admins_deleted_index
    on admins (deleted);

create trigger created_trigger
    before insert on admins
    for each row
    execute procedure set_timestamp_created();

create trigger updated_trigger
    before update on admins
    for each row
    execute procedure set_timestamp_updated();

insert into public.admins (role, login, password, displayed_name, created, updated) values ('admin',      'admin',      'admin', 'administrator', current_timestamp, current_timestamp);
insert into public.admins (role, login, password, displayed_name, created, updated) values ('moder', 'moderator1', 'moderator1',    'moderator1', current_timestamp, current_timestamp);
insert into public.admins (role, login, password, displayed_name, created, updated) values ('moder', 'moderator2', 'moderator2',    'moderator2', current_timestamp, current_timestamp);