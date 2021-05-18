drop table if exists apartment_image;

create table apartment_image(
    id           bigserial             not null constraint apartment_image_pk primary key,
    apartment_id bigint                not null constraint apartment_image_apartment_id_fk references apartment,
    img_url      varchar(255)          not null,
    is_approved  boolean default false not null,
    is_deleted   boolean default false not null,
    created      timestamp(6)          not null,
    updated      timestamp(6)          not null
);

alter table apartment_image
    owner to username;

create unique index apartment_image_id_uindex
    on apartment_image (id);

create index apartment_image_apartment_id_index
    on apartment_image (apartment_id);

create index apartment_image_is_approved_index
    on apartment_image (is_approved);

create index apartment_image_is_deleted_index
    on apartment_image (is_deleted);

create trigger created_trigger
    before insert on apartment_image
    for each row
execute procedure set_timestamp_created();

create trigger changed_trigger
    before update on apartment_image
    for each row
execute procedure set_timestamp_updated();