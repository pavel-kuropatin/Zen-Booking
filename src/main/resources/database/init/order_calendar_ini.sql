drop table if exists order_calendar;

create table order_calendar(
    id           bigserial             not null constraint order_calendar_pk primary key,
    order_id     bigint                not null constraint order_calendar_order_id_fk references "order",
    apartment_id bigint                not null constraint order_calendar_apartment_id_fk references apartment,
    order_date   date                  not null,
    is_deleted   boolean default false not null,
    created      timestamp(6)          not null,
    updated      timestamp(6)          not null
);

alter table order_calendar
    owner to username;

create unique index order_calendar_id_uindex
    on order_calendar (id);

create unique index order_calendar_order_id_index
    on order_calendar (order_id);

create unique index order_calendar_apartment_id_index
    on order_calendar (apartment_id);

create unique index order_calendar_order_date_index
    on order_calendar (order_date);

create unique index order_calendar_is_deleted_index
    on order_calendar (is_deleted);

create trigger created_trigger
    before insert on order_calendar
    for each row
execute procedure set_timestamp_created();

create trigger changed_trigger
    before update on order_calendar
    for each row
execute procedure set_timestamp_updated();