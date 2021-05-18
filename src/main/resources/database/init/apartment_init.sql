drop table if exists apartment;

create table apartment(
    id           bigserial             not null constraint apartment_pk primary key,
    type         varchar(50)           not null,
    name         varchar(50)           not null,
    description  varchar(255)          not null,
    address      varchar(255)          not null,
    price        integer               not null,
    guests       smallint              not null,
    rooms        smallint              not null,
    beds         smallint              not null,
    kitchen      boolean default false not null,
    washer       boolean default false not null,
    tv           boolean default false not null,
    internet     boolean default false not null,
    pets_allowed boolean default false not null,
    is_available boolean default false not null,
    is_approved  boolean default false not null,
    is_banned    boolean default false not null,
    is_deleted   boolean default false not null,
    created      timestamp(6)          not null,
    updated      timestamp(6)          not null
);

alter table apartment
    owner to username;

create unique index apartment_id_uindex
    on apartment (id);

create index apartment_type_index
    on apartment (type);

create index apartment_name_index
    on apartment (name);

create index apartment_address_index
    on apartment (address);

create index apartment_price_index
    on apartment (price);

create index apartment_guests_index
    on apartment (guests);

create index apartment_rooms_index
    on apartment (rooms);

create index apartment_beds_index
    on apartment (beds);

create index apartment_kitchen_index
    on apartment (kitchen);

create index apartment_washer_index
    on apartment (washer);

create index apartment_tv_index
    on apartment (tv);

create index apartment_internet_index
    on apartment (internet);

create index apartment_pets_allowed_index
    on apartment (pets_allowed);

create index apartment_is_available_index
    on apartment (is_available);

create index apartment_is_approved_index
    on apartment (is_approved);

create index apartment_is_banned_index
    on apartment (is_banned);

create index apartment_is_deleted_index
    on apartment (is_deleted);

create trigger created_trigger
    before insert on apartment
    for each row
execute procedure set_timestamp_created();

create trigger changed_trigger
    before update on apartment
    for each row
execute procedure set_timestamp_updated();