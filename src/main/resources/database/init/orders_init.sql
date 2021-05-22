drop table if exists orders;

create table orders(
    id               bigserial             not null constraint orders_pk primary key,
    user_id          bigint                not null constraint orders_user_id_fk references users,
    property_id      bigint                not null constraint orders_property_id_fk references property,
    total_price      integer               not null,
    start_date       date                  not null,
    end_date         date                  not null,
    accepted_by_host boolean default false not null,
    cancelled        boolean default false not null,
    finished         boolean default false not null,
    created          timestamp(6)          not null,
    updated          timestamp(6)          not null
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

create trigger created_trigger
    before insert on orders
    for each row
    execute procedure set_timestamp_created();

create trigger updated_trigger
    before update on orders
    for each row
    execute procedure set_timestamp_updated();