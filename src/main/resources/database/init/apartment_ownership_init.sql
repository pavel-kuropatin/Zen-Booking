drop table if exists apartment_ownership;

create table apartment_ownership(
    id           bigserial             not null constraint apartment_ownership_pk primary key,
    apartment_id bigint                not null constraint apartment_ownership_apartment_id_fk references apartment,
    host_id      bigint                not null constraint apartment_ownership_host_id_fk references host,
    is_deleted   boolean default false not null,
    created      timestamp(6)          not null,
    updated      timestamp(6)          not null
);

alter table apartment_ownership
    owner to username;

create unique index apartment_ownership_id_uindex
    on apartment_ownership (id);

create unique index apartment_ownership_apartment_id_uindex
    on apartment_ownership (apartment_id);

create index apartment_ownership_host_id_index
    on apartment_ownership (host_id);

create index apartment_ownership_host_is_deleted_index
    on apartment_ownership (is_deleted);

create trigger created_trigger
    before insert on apartment_ownership
    for each row
execute procedure set_timestamp_created();

create trigger changed_trigger
    before update on apartment_ownership
    for each row
execute procedure set_timestamp_updated();