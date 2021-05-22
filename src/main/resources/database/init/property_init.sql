drop table if exists property;

create table property(
    id           bigserial                    not null constraint property_pk primary key,
    user_id      bigint                       not null constraint orders_user_id_fk references users,
    type         varchar(64)                  not null,
    name         varchar(64)                  not null,
    description  varchar(500)                 not null,
    country      varchar(64)                  not null,
    region       varchar(64)                  not null,
    city         varchar(64)                  not null,
    street       varchar(64)                  not null,
    building     varchar(64)                  not null,
    apartment    varchar(64)    default '',
    price        integer                      not null,
    guests       smallint                     not null,
    rooms        smallint                     not null,
    beds         smallint                     not null,
    kitchen      boolean        default false not null,
    washer       boolean        default false not null,
    tv           boolean        default false not null,
    internet     boolean        default false not null,
    pets_allowed boolean        default false not null,
    available    boolean        default false not null,
    approved     boolean        default false not null,
    banned       boolean        default false not null,
    deleted      boolean        default false not null,
    created      timestamptz(6)               not null,
    updated      timestamptz(6)               not null
);

alter table property
    owner to username;

create unique index property_id_uindex
    on property (id);

create index property_user_id_index
    on orders (user_id);

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

create index property_available_index
    on property (available);

create index property_approved_index
    on property (approved);

create index property_banned_index
    on property (banned);

create index property_deleted_index
    on property (deleted);

create trigger created_trigger
    before insert on property
    for each row
    execute procedure set_timestamp_created();

create trigger updated_trigger
    before update on property
    for each row
    execute procedure set_timestamp_updated();

insert into property (user_id, type, name, description, country, region, city, street, building, apartment, price, guests, rooms, beds, kitchen, washer, tv, internet, pets_allowed, available, approved) values (3, 'apartment', 'big house', 'big house in the middle of a nowhere', 'belarus', 'minskaya oblast', 'soligorsk', 'zheleznodorozhnaya', '24', '121', 100, 5, 2, 3, true, true, true, true, false, true, true);