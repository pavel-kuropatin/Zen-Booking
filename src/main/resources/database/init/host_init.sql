drop table if exists host;

create table host(
    id           bigserial                             not null constraint host_pk primary key,
    login        varchar(50)                           not null,
    password     varchar(50)                           not null,
    name         varchar(50)                           not null,
    surname      varchar(50)                           not null,
    birth_date   date                                  not null,
    email        varchar(50)                           not null,
    phone        varchar(50)                           not null,
    bank_account varchar(50)                           not null,
    vatin        varchar(50) default gen_random_uuid() not null,
    is_banned    boolean     default false             not null,
    is_deleted   boolean     default false             not null,
    created      timestamp(6)                          not null,
    updated      timestamp(6)                          not null
);

alter table host
    owner to username;

create unique index host_id_uindex
    on host (id);

create unique index host_login_uindex
    on host (login);

create index host_name_index
    on host (name);

create index host_surname_index
    on host (surname);

create index host_birth_date_index
    on host (birth_date);

create index host_email_index
    on host (email);

create index host_phone_index
    on host (phone);

create unique index host_bank_account_uindex
    on host (bank_account);

create unique index host_vatin_uindex
    on host (vatin);

create index host_is_banned_index
    on host (is_banned);

create index host_is_deleted_index
    on host (is_deleted);

create trigger created_trigger
    before insert on host
    for each row
execute procedure set_timestamp_created();

create trigger changed_trigger
    before update on host
    for each row
execute procedure set_timestamp_updated();