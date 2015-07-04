INSERT INTO t_contact_mechanism_type(id, name) VALUES(100, 'email');

-- findById
insert into t_party(id, name) values (100, 'Ray Sponsible');

insert into t_user (id, party_fk, login, password, from_date, to_date, status) values (
    101, 100, 'ray.sponsible', 'secret', '1973-12-27 10:30:45', null, 'A');

-- findById_deleted
insert into t_party(id, name) values (300, 'Ray Sponsible');

insert into t_user (id, party_fk, login, password, from_date, to_date, status, deleted) values (
    301, 300, 'ray301.sponsible', 'secret', '1973-12-27 10:30:45', null, 'A', 1);

-- create_duplicate email
insert into t_party(id, name) values (400, 'Ray Sponsible');

insert into t_eaddress(id, address, hash) values(400, 'ray.sponsible@gmail.com', MD5('ray.sponsible@gmail.com'));
insert into t_party_contact_mechanism(id, party_fk, eaddress_fk, type_fk) values(400, 400, 400, 100);

insert into t_user (id, party_fk, login, password, from_date, to_date, status) values (
    400, 400, '400.sponsible', 'secret', '1973-12-27 10:30:45', null, 'A');

-- create
insert into t_party(id, name) values (410, 'Ray Sponsible');

insert into t_eaddress(id, address, hash) values(410, 'ray.sponsible410@gmail.com', MD5('ray.sponsible410@gmail.com'));
insert into t_party_contact_mechanism(id, party_fk, eaddress_fk, type_fk) values(410, 410, 410, 100);

-- create_hasAlreadyAccount
insert into t_party(id, name) values (420, 'Ray Sponsible');

insert into t_eaddress(id, address, hash) values(420, 'ray.sponsible420@gmail.com', MD5('ray.sponsible420@gmail.com'));
insert into t_party_contact_mechanism(id, party_fk, eaddress_fk, type_fk) values(420, 420, 420, 100);

insert into t_user (id, party_fk, login, password, from_date, to_date, status) values (
    420, 420, '420.sponsible', 'secret', '1973-12-27 10:30:45', null, 'A');

-- create_reuse_email
insert into t_party(id, name) values (500, 'Ray Sponsible');

insert into t_eaddress(id, address, hash) values(500, 'ray.sponsible500@gmail.com', MD5('ray.sponsible500@gmail.com'));
insert into t_party_contact_mechanism(id, party_fk, eaddress_fk, type_fk) values(500, 500, 500, 100);

insert into t_user (id, party_fk, login, password, from_date, to_date, status, deleted) values (
    500, 500, '500.sponsible', 'secret', '1973-12-27 10:30:45', null, 'A', 1);

-- create_reuse_email
insert into t_party(id, name) values (510, 'Ray Sponsible');

insert into t_eaddress(id, address, hash) values(510, 'ray.sponsible510@gmail.com', MD5('ray.sponsible510@gmail.com'));
insert into t_party_contact_mechanism(id, party_fk, eaddress_fk, type_fk) values(510, 510, 510, 100);



-- updateLogin
insert into t_party(id, name) values (600, 'Ray Sponsible');

insert into t_user (id, party_fk, login, password, from_date, to_date, status) values (
    600, 600, 'ray600.sponsible', 'secret', '1973-12-27 10:30:45', null, 'A');

-- updatePassword
insert into t_party(id, name) values (700, 'Ray Sponsible');

insert into t_user (id, party_fk, login, password, from_date, to_date, status) values (
    700, 700, 'ray700.sponsible', 'secret', '1973-12-27 10:30:45', null, 'A');

-- delete
insert into t_party(id, name) values (1000, 'Ray Sponsible');

insert into t_user (id, party_fk, login, password, from_date, to_date, status) values (
    1000, 1000, 'ray1000.sponsible', 'secret', '1973-12-27 10:30:45', null, 'A');
