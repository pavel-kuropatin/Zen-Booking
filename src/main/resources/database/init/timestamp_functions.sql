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