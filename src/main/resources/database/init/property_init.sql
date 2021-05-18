drop table if exists property;

create table property(
    id           bigserial                  not null constraint property_pk primary key,
    type         varchar(64)                not null,
    name         varchar(64)                not null,
    description  varchar(500)               not null,
    country      varchar(64)                not null,
    region       varchar(64)                not null,
    city         varchar(64)                not null,
    street       varchar(64)                not null,
    building     varchar(64)                not null,
    apartment    varchar(64)  default '',
    price        integer                    not null,
    guests       smallint                   not null,
    rooms        smallint                   not null,
    beds         smallint                   not null,
    kitchen      boolean      default false not null,
    washer       boolean      default false not null,
    tv           boolean      default false not null,
    internet     boolean      default false not null,
    pets_allowed boolean      default false not null,
    is_available boolean      default false not null,
    is_approved  boolean      default false not null,
    is_banned    boolean      default false not null,
    is_deleted   boolean      default false not null,
    created      timestamp(6)               not null,
    updated      timestamp(6)               not null
);

alter table property
    owner to username;

create unique index property_id_uindex
    on property (id);

create index property_type_index
    on property (type);

create index property_name_index
    on property (name);

create index property_country_index
    on property (country);

create index property_region_index
    on property (region);

create index property_city_index
    on property (city);

create index property_street_index
    on property (street);

create index property_building_index
    on property (building);

create index property_apartment_index
    on property (apartment);

create index property_price_index
    on property (price);

create index property_guests_index
    on property (guests);

create index property_rooms_index
    on property (rooms);

create index property_beds_index
    on property (beds);

create index property_kitchen_index
    on property (kitchen);

create index property_washer_index
    on property (washer);

create index property_tv_index
    on property (tv);

create index property_internet_index
    on property (internet);

create index property_pets_allowed_index
    on property (pets_allowed);

create index property_is_available_index
    on property (is_available);

create index property_is_approved_index
    on property (is_approved);

create index property_is_banned_index
    on property (is_banned);

create index property_is_deleted_index
    on property (is_deleted);

create trigger created_trigger
    before insert on property
    for each row
    execute procedure set_timestamp_created();

create trigger updated_trigger
    before update on property
    for each row
    execute procedure set_timestamp_updated();