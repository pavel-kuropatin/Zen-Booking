drop table if exists review;

create table review(
    id          bigserial                  not null constraint review_pk primary key,
    user_id     bigint                     not null constraint review_user_id_fk references users,
    property_id bigint                     not null constraint review_property_id_fk references property,
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

create index review_user_id_index
    on review (user_id);

create index review_property_id_index
    on review (property_id);

create index review_rating_index
    on review (rating);

create index review_approved_index
    on review (approved);

create index review_deleted_index
    on review (deleted);

create trigger created_trigger
    before insert on review
    for each row
    execute procedure set_timestamp_created();

create trigger updated_trigger
    before update on review
    for each row
    execute procedure set_timestamp_updated();