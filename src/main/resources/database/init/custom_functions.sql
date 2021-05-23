create or replace function set_timestamp_created()
returns trigger as
$$
begin
    new.created = now();
    new.updated = now();
    return new;
end;
$$ language plpgsql;

create or replace function set_timestamp_updated()
returns trigger as
$$
begin
    new.updated = now();
    return new;
end;
$$ language plpgsql;

-- create or replace function random_between(min int, max int)
-- returns int as $$
-- begin
--     return floor(random()* (max - min + 1) + min);
-- end;
-- $$ language plpgsql;

-- select *, date_part('year', age(birth_date)) as age
-- from users
-- where date_part('year', age(birth_date)) between (18 + 1) and (25 - 1)
-- order by id;