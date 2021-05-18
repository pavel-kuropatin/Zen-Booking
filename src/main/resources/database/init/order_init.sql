drop table if exists "order";

create table "order"(
    id           bigserial             not null constraint order_pk primary key,
    user_id      bigint                not null constraint order_client_id_fk references "user",
    property_id  bigint                not null constraint order_property_id_fk references property,
    total_price  integer               not null,
    is_cancelled boolean default false not null,
    is_finished  boolean default false not null,
    created      timestamp(6)          not null,
    updated      timestamp(6)          not null
);

alter table "order"
    owner to username;

create unique index order_id_uindex
    on "order" (id);

create index order_client_id_index
    on "order" (user_id);

create index order_property_id_index
    on "order" (property_id);

create index order_apartment_total_price_index
    on "order" (total_price);

create index order_apartment_is_cancelled_index
    on "order" (is_cancelled);

create index order_apartment_is_finished_index
    on "order" (is_finished);

create trigger created_trigger
    before insert on "order"
    for each row
    execute procedure set_timestamp_created();

create trigger updated_trigger
    before update on "order"
    for each row
    execute procedure set_timestamp_updated();