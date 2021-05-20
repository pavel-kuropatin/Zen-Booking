drop table if exists orders;

create table orders(
    id           bigserial             not null constraint orders_pk primary key,
    user_id      bigint                not null constraint orders_user_id_fk references users,
    property_id  bigint                not null constraint orders_property_id_fk references property,
    total_price  integer               not null,
    is_cancelled boolean default false not null,
    is_finished  boolean default false not null,
    created      timestamp(6)          not null,
    updated      timestamp(6)          not null
);

alter table orders
    owner to username;

create unique index orders_id_uindex
    on orders (id);

create index orders_client_id_index
    on orders (user_id);

create index orders_property_id_index
    on orders (property_id);

create index orders_apartment_total_price_index
    on orders (total_price);

create index orders_apartment_is_cancelled_index
    on orders (is_cancelled);

create index orders_apartment_is_finished_index
    on orders (is_finished);

create trigger created_trigger
    before insert on orders
    for each row
    execute procedure set_timestamp_created();

create trigger updated_trigger
    before update on orders
    for each row
    execute procedure set_timestamp_updated();