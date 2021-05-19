drop table if exists property_ownership;

create table property_ownership(
    id          bigserial                  not null constraint property_ownership_pk primary key,
    property_id bigint                     not null constraint property_ownership_property_id_fk references property,
    user_id     bigint                     not null constraint property_ownership_user_id_fk references users,
    is_deleted  boolean      default false not null,
    created     timestamp(6)               not null,
    updated     timestamp(6)               not null
);

alter table property_ownership
    owner to username;

create unique index property_ownership_id_uindex
    on property_ownership (id);

create unique index property_ownership_apartment_id_uindex
    on property_ownership (property_id);

create index property_ownership_user_id_index
    on property_ownership (user_id);

create index property_ownership_is_deleted_index
    on property_ownership (is_deleted);

create trigger created_trigger
    before insert on property_ownership
    for each row
    execute procedure set_timestamp_created();

create trigger updated_trigger
    before update on property_ownership
    for each row
    execute procedure set_timestamp_updated();