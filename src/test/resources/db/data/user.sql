-- findById
insert into t_party(id, name, first_name, last_name, prefix, suffix, birth_date, gender, kind, height, weight)
    values (100, 'Ray Sponsible', 'Ray', 'Sponsible', 'Mr', 'PHD', '1973-12-27', 'M', 'P', 182, 250);

insert into t_user (id, party_fk, login, password, from_date, to_date, status) values (
    101, 100, 'ray.sponsible', 'secret', '1973-12-27 10:30:45', null, 'A');

-- findById_deletedParty
insert into t_party(id, name, first_name, last_name, prefix, suffix, birth_date, gender, kind, height, weight, deleted)
    values (200, 'Ray Sponsible', 'Ray', 'Sponsible', 'Mr', 'PHD', '1973-12-27', 'M', 'P', 182, 250, 1);

insert into t_user (id, party_fk, login, password, from_date, to_date, status) values (
    201, 200, 'ray201.sponsible', 'secret', '1973-12-27 10:30:45', null, 'A');

-- findById_deleted
insert into t_party(id, name, first_name, last_name, prefix, suffix, birth_date, gender, kind, height, weight)
    values (300, 'Ray Sponsible', 'Ray', 'Sponsible', 'Mr', 'PHD', '1973-12-27', 'M', 'P', 182, 250);

insert into t_user (id, party_fk, login, password, from_date, to_date, status, deleted) values (
    301, 300, 'ray301.sponsible', 'secret', '1973-12-27 10:30:45', null, 'A', 1);

-- update
insert into t_party(id, name, first_name, last_name, prefix, suffix, birth_date, gender, kind, height, weight)
    values (1000, 'Ray Sponsible', 'Ray', 'Sponsible', 'Mr', 'PHD', '1973-12-27', 'M', 'P', 182, 250);

insert into t_user (id, party_fk, login, password, from_date, to_date, status) values (
    1001, 1000, 'ray1001.sponsible', 'secret', '1973-12-27 10:30:45', null, 'A');

-- update_deleted
insert into t_party(id, name, first_name, last_name, prefix, suffix, birth_date, gender, kind, height, weight)
    values (1100, 'Ray Sponsible', 'Ray', 'Sponsible', 'Mr', 'PHD', '1973-12-27', 'M', 'P', 182, 250);

insert into t_user (id, party_fk, login, password, from_date, to_date, status, deleted)
    values (1101, 1100, 'ray1101.sponsible', 'secret', '1973-12-27 10:30:45', null, 'A', 1);

-- update
insert into t_party(id, name, first_name, last_name, prefix, suffix, birth_date, gender, kind, height, weight)
    values (2000, 'Ray Sponsible', 'Ray', 'Sponsible', 'Mr', 'PHD', '1973-12-27', 'M', 'P', 182, 250);

insert into t_user (id, party_fk, login, password, from_date, to_date, status) values (
    2001, 2000, '2001.sponsible', 'secret', '1973-12-27 10:30:45', null, 'A');
