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

create or replace function random_personal_account()
returns varchar as
$$
begin
return concat_ws('-', cast(random_between(1000, 9999) as varchar), cast(random_between(1000, 9999) as varchar), cast(random_between(1000, 9999) as varchar), cast(random_between(1000, 9999) as varchar));
end;
$$ language plpgsql;

create or replace function random_between(min int, max int)
returns int as $$
begin
    return floor(random()* (max - min + 1) + min);
end;
$$ language plpgsql;

-- select *, date_part('year', age(birth_date)) as age
-- from users
-- where date_part('year', age(birth_date)) between (18 + 1) and (25 - 1)
-- order by id;