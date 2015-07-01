INSERT INTO t_contact_mechanism_type(id, name) VALUES(100, 'email');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(101, 'primary');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(102, 'secondary');

INSERT INTO t_contact_mechanism_type(id, name) VALUES(200, 'web');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(201, 'website');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(202, 'facebook');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(203, 'instagram');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(204, 'youtube');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(205, 'linkedin');

-- findById
insert into t_party(id, name, first_name, last_name, prefix, suffix, birth_date, gender, kind, height, weight) values (
    100, 'Ray Sponsible', 'Ray', 'Sponsible', 'Mr', 'PHD', '1973-12-27', 'M', 'P', 182, 250);

insert into t_eaddress(id, address, hash) values(101, 'ray.sponsible@gmail.com', MD5('ray.sponsible@gmail.com'));
insert into t_party_eaddress(id, party_fk, contact_fk, type_fk, purpose_fk) values(101, 100, 101, 100, 101);

insert into t_eaddress(id, address, hash) values(102, 'ray.sponsible@hotmail.com', MD5('ray.sponsible@hotmail.com'));
insert into t_party_eaddress(id, party_fk, contact_fk, type_fk, purpose_fk, privacy) values(102, 100, 102, 100, 102, 'H');

insert into t_eaddress(id, address, hash) values(121, 'http://ray.sponsible.com', MD5('http://ray.sponsible.com'));
insert into t_party_eaddress(id, party_fk, contact_fk, type_fk, purpose_fk) values(121, 100, 121, 200, 201);

insert into t_eaddress(id, address, hash) values(122, 'https://facebook.com/ray_sponsible', MD5('https://facebook.com/ray.sponsible'));
insert into t_party_eaddress(id, party_fk, contact_fk, type_fk, purpose_fk) values(122, 100, 122, 200, 202);

-- findById_deleted
insert into t_party(id, name, kind, deleted) values (
    200, 'Ray Sponsible', 'P', 1);
