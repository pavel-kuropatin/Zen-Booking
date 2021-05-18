drop table if exists property_image;

create table property_image(
    id          bigserial                  not null constraint property_image_pk primary key,
    property_id bigint                     not null constraint property_image_property_id_fk references property,
    img_url     varchar(255)               not null,
    is_approved boolean      default false not null,
    is_deleted  boolean      default false not null,
    created     timestamp(6)               not null,
    updated     timestamp(6)               not null
);

alter table property_image
    owner to username;

create unique index property_image_id_uindex
    on property_image (id);

create index property_image_apartment_id_index
    on property_image (property_id);

create index property_image_is_approved_index
    on property_image (is_approved);

create index property_image_is_deleted_index
    on property_image (is_deleted);

create trigger created_trigger
    before insert on property_image
    for each row
    execute procedure set_timestamp_created();

create trigger updated_trigger
    before update on property_image
    for each row
    execute procedure set_timestamp_updated();