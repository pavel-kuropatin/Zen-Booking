drop table if exists review;

create table review(
    id           bigserial             not null constraint review_pk primary key,
    client_id    bigint                not null constraint review_client_id_fk references client,
    apartment_id bigint                not null constraint review_apartment_id_fk references apartment,
    summary      varchar(100)          not null,
    description  varchar(255)          not null,
    rating       smallint              not null,
    is_approved  boolean default false not null,
    is_deleted   boolean default false not null,
    created      timestamp(6)          not null,
    updated      timestamp(6)          not null
);

alter table review
    owner to username;

create unique index review_id_uindex
    on review (id);

create index review_client_id_index
    on review (client_id);

create index review_apartment_id_index
    on review (apartment_id);

create index review_rating_index
    on review (rating);

create index review_is_approved_index
    on review (is_approved);

create index review_is_deleted_index
    on review (is_deleted);

create trigger created_trigger
    before insert on review
    for each row
execute procedure set_timestamp_created();

create trigger changed_trigger
    before update on review
    for each row
execute procedure set_timestamp_updated();