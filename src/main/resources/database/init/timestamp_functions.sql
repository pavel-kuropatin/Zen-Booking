create or replace function set_timestamp_created()
    returns trigger as $$
begin
    new.created = now();
    new.updated = now();
    return new;
end;
$$ language plpgsql;

create or replace function set_timestamp_updated()
    returns trigger as $$
begin
    new.updated = now();
    return new;
end;
$$ language plpgsql;

-- create or replace function set_timestamp_created()
--     returns trigger as $$
-- begin
--     if row(new.*) is distinct from row(old.*) then
--         new.created = now();
--         return new;
--     else
--         return old;
--     end if;
-- end;
-- $$ language 'plpgsql';

-- create trigger created_trigger
--     before insert on apartment
--     for each row
-- execute procedure set_timestamp_created();
--
-- create trigger changed_trigger
--     before update on apartment
--     for each row
-- execute procedure set_timestamp_changed();