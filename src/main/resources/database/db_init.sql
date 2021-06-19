drop table if exists review, orders, property_image, property, users, admins cascade;

create table admins(
    id             bigserial                          not null constraint admins_pk primary key,
    role           varchar(10)  default 'ROLE_MODER'  not null,
    login          varchar(20)                        not null,
    password       varchar(50)                        not null,
    displayed_name varchar(20)                        not null,
    suspended      boolean      default false         not null,
    deleted        boolean      default false         not null,
    created        timestamp(6)                       not null,
    updated        timestamp(6)                       not null
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

create table users(
    id         bigserial                        not null constraint users_pk primary key,
    role       varchar(9)   default 'ROLE_USER' not null,
    login      varchar(20)                      not null,
    password   varchar(50)                      not null,
    name       varchar(20)                      not null,
    surname    varchar(20)                      not null,
    gender     varchar(9)                       not null,
    birth_date date                             not null,
    email      varchar(50)                      not null,
    phone      varchar(20)                      not null,
    balance    bigint       default 0           not null,
    banned     boolean      default false       not null,
    deleted    boolean      default false       not null,
    created    timestamp(6)                     not null,
    updated    timestamp(6)                     not null
);

alter table users
    owner to username;

create unique index users_id_uindex
    on users (id);

create index users_role_index
    on users (role);

create unique index users_login_uindex
    on users (login);

create index users_name_index
    on users (name);

create index users_surname_index
    on users (surname);

create index users_gender_index
    on users (gender);

create index users_birth_date_index
    on users (birth_date);

create unique index users_email_uindex
    on users (email);

create index users_phone_index
    on users (phone);

create index users_balance_index
    on users (balance);

create index users_banned_index
    on users (banned);

create index users_deleted_index
    on users (deleted);

create table property(
    id           bigserial                  not null constraint property_pk primary key,
    user_id      bigint                     not null constraint property_user_id_fk references users,
    type         varchar(9)                 not null,
    name         varchar(20)                not null,
    description  varchar(500)               not null,
    country      varchar(50)                not null,
    region       varchar(50)                not null,
    city         varchar(50)                not null,
    street       varchar(50)                not null,
    building     varchar(10)                not null,
    apartment    varchar(10)  default '',
    price        integer                    not null,
    guests       smallint                   not null,
    rooms        smallint                   not null,
    beds         smallint                   not null,
    kitchen      boolean      default false not null,
    washer       boolean      default false not null,
    tv           boolean      default false not null,
    internet     boolean      default false not null,
    pets_allowed boolean      default false not null,
    available    boolean      default false not null,
    approved     boolean      default false not null,
    banned       boolean      default false not null,
    deleted      boolean      default false not null,
    created      timestamp(6)               not null,
    updated      timestamp(6)               not null
);

alter table property
    owner to username;

create unique index property_id_uindex
    on property (id);

create index property_user_id_index
    on property (user_id);

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

create table property_image(
    id          bigserial                  not null constraint property_image_pk primary key,
    property_id bigint                     not null constraint property_image_property_id_fk references property,
    img_url     varchar(255)               not null,
    approved    boolean      default false not null,
    deleted     boolean      default false not null,
    created     timestamp(6)               not null,
    updated     timestamp(6)               not null
);

alter table property_image
    owner to username;

create unique index property_image_id_uindex
    on property_image (id);

create index property_image_apartment_id_index
    on property_image (property_id);

create index property_image_approved_index
    on property_image (approved);

create index property_image_deleted_index
    on property_image (deleted);

create table orders(
    id               bigserial                  not null constraint orders_pk primary key,
    user_id          bigint                     not null constraint orders_user_id_fk references users,
    property_id      bigint                     not null constraint orders_property_id_fk references property,
    total_price      integer                    not null,
    start_date       date                       not null,
    end_date         date                       not null,
    accepted_by_host boolean      default false not null,
    cancelled        boolean      default false not null,
    finished         boolean      default false not null,
    created          timestamp(6)               not null,
    updated          timestamp(6)               not null
);

alter table orders
    owner to username;

create unique index orders_id_uindex
    on orders (id);

create index orders_user_id_index
    on orders (user_id);

create index orders_property_id_index
    on orders (property_id);

create index orders_apartment_total_price_index
    on orders (total_price);

create index orders_start_date_index
    on orders (start_date);

create index orders_end_date_index
    on orders (end_date);

create index orders_accepted_by_host_index
    on orders (accepted_by_host);

create index orders_apartment_cancelled_index
    on orders (cancelled);

create index orders_apartment_finished_index
    on orders (finished);

create table review(
    id          bigserial                  not null constraint review_pk primary key,
    order_id    bigint                     not null constraint review_order_id_fk references orders,
    summary     varchar(100)               not null,
    description varchar(500)               not null,
    rating      smallint                   not null,
    approved    boolean      default false not null,
    deleted     boolean      default false not null,
    created     timestamp(6)               not null,
    updated     timestamp(6)               not null
);

alter table review
    owner to username;

create unique index review_id_uindex
    on review (id);

create index review_order_id_index
    on review (order_id);

create index review_rating_index
    on review (rating);

create index review_approved_index
    on review (approved);

create index review_deleted_index
    on review (deleted);