-- Schema initialization
create schema if not exists public;

-- Database clearing
drop table if exists flyway_schema_history, reviews, orders, property_images, property, users, admins, roles, genders, property_types, order_statuses cascade;

-- Roles
create table if not exists roles
(
    role varchar(10) not null
);

create unique index roles_role_uindex
    on roles (role);

-- Genders
create table if not exists genders
(
    gender varchar(9) not null
);

create unique index genders_gender_uindex
    on genders (gender);

-- Property Types
create table if not exists property_types
(
    type varchar(13) not null
);

create unique index property_types_type_uindex
    on property_types (type);

-- Order Statuses
create table if not exists order_statuses
(
    status varchar(9) not null
);

create unique index order_statuses_status_uindex
    on order_statuses (status);

-- Admins
create table if not exists admins
(
    id             bigserial                        not null
        constraint admins_pk primary key,
    role           varchar(10) default 'ROLE_MODER' not null
        constraint admins_roles_role_fk references roles (role),
    login          varchar(20)                      not null,
    password       varchar(60)                      not null,
    displayed_name varchar(20)                      not null,
    is_suspended   boolean     default false        not null,
    is_deleted     boolean     default false        not null,
    last_login     timestamp(3),
    created        timestamp(3)                     not null,
    updated        timestamp(3)                     not null
);

create unique index admins_id_uindex
    on admins (id);

create index admins_role_index
    on admins (role);

create unique index admins_login_uindex
    on admins (login);

create index admins_displayed_name_index
    on admins (displayed_name);

create index admins_is_suspended_index
    on admins (is_suspended);

create index admins_is_deleted_index
    on admins (is_deleted);

-- Users
create table if not exists users
(
    id         bigserial                      not null
        constraint users_pk primary key,
    role       varchar(9) default 'ROLE_USER' not null
        constraint users_roles_role_fk references roles (role),
    login      varchar(20)                    not null,
    password   varchar(60)                    not null,
    name       varchar(20)                    not null,
    surname    varchar(20)                    not null,
    gender     varchar(9)                     not null
        constraint users_genders_gender_fk references genders (gender),
    birth_date date                           not null,
    email      varchar(50)                    not null,
    phone      varchar(20)                    not null,
    balance    bigint     default 0           not null,
    is_banned  boolean    default false       not null,
    last_login timestamp(3),
    created    timestamp(3)                   not null,
    updated    timestamp(3)                   not null
);

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

create index users_is_banned_index
    on users (is_banned);

-- Property
create table if not exists property
(
    id              bigserial             not null
        constraint property_pk primary key,
    user_id         bigint                not null
        constraint property_user_id_fk references users (id),
    type            varchar(13)           not null
        constraint property_property_types_type_fk references property_types (type),
    name            varchar(100)          not null,
    description     varchar(500)          not null,
    address         varchar(500)          not null,
    price           integer               not null,
    guests          smallint              not null,
    rooms           smallint              not null,
    beds            smallint              not null,
    has_kitchen     boolean default false not null,
    has_washer      boolean default false not null,
    has_tv          boolean default false not null,
    has_internet    boolean default false not null,
    is_pets_allowed boolean default false not null,
    is_available    boolean default false not null,
    is_deleted      boolean default false not null,
    created         timestamp(3)          not null,
    updated         timestamp(3)          not null
);

create unique index property_id_uindex
    on property (id);

create index property_user_id_index
    on property (user_id);

create index property_type_index
    on property (type);

create index property_name_index
    on property (name);

create index property_address_index
    on property (address);

create index property_price_index
    on property (price);

create index property_guests_index
    on property (guests);

create index property_rooms_index
    on property (rooms);

create index property_beds_index
    on property (beds);

create index property_has_kitchen_index
    on property (has_kitchen);

create index property_has_washer_index
    on property (has_washer);

create index property_has_tv_index
    on property (has_tv);

create index property_has_internet_index
    on property (has_internet);

create index property_is_pets_allowed_index
    on property (is_pets_allowed);

create index property_is_available_index
    on property (is_available);

create index property_is_deleted_index
    on property (is_deleted);

-- Property Images
create table if not exists property_images
(
    id          bigserial             not null
        constraint property_images_pk primary key,
    property_id bigint                not null
        constraint property_images_property_id_fk references property (id),
    img_url     varchar(250)          not null,
    is_deleted  boolean default false not null,
    created     timestamp(3)          not null,
    updated     timestamp(3)          not null
);

create unique index property_images_id_uindex
    on property_images (id);

create index property_images_apartment_id_index
    on property_images (property_id);

create index property_images_deleted_index
    on property_images (is_deleted);

-- Orders
create table if not exists orders
(
    id          bigserial    not null
        constraint orders_pk primary key,
    user_id     bigint       not null
        constraint orders_user_id_fk references users (id),
    property_id bigint       not null
        constraint orders_property_id_fk references property (id),
    total_price integer      not null,
    start_date  date         not null,
    end_date    date         not null,
    status      varchar(9)   not null
        constraint orders_order_statuses_status_fk references order_statuses (status),
    created     timestamp(3) not null,
    updated     timestamp(3) not null
);

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

create index orders_status_index
    on orders (status);

-- Reviews
create table if not exists reviews
(
    id          bigserial             not null
        constraint review_pk primary key,
    order_id    bigint                not null
        constraint review_order_id_fk references orders (id),
    summary     varchar(100)          not null,
    description varchar(500)          not null,
    rating      smallint              not null,
    is_deleted  boolean default false not null,
    created     timestamp(3)          not null,
    updated     timestamp(3)          not null
);

create unique index reviews_id_uindex
    on reviews (id);

create index reviews_order_id_index
    on reviews (order_id);

create index reviews_rating_index
    on reviews (rating);

create index reviews_is_deleted_index
    on reviews (is_deleted);
