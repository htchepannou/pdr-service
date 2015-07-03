INSERT INTO t_contact_mechanism_type(id, name) VALUES(100, 'email');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(101, 'primary');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(102, 'secondary');

INSERT INTO t_contact_mechanism_type(id, name) VALUES(200, 'web');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(201, 'website');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(202, 'facebook');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(203, 'instagram');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(204, 'youtube');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(205, 'linkedin');

INSERT INTO t_contact_mechanism_type(id, name) VALUES(300, 'postal');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(301, 'postal');

INSERT INTO t_contact_mechanism_type(id, name) VALUES(400, 'phone');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(401, 'primary_phone');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(402, 'work_phone');

-- findById
insert into t_party(id, name, first_name, last_name, prefix, suffix, birth_date, gender, kind, height, weight) values (
    100, 'Ray Sponsible', 'Ray', 'Sponsible', 'Mr', 'PHD', '1973-12-27', 'M', 'P', 182, 250);

-- contacts
insert into t_eaddress(id, address, hash) values(101, 'ray.sponsible@gmail.com', MD5('ray.sponsible@gmail.com'));
insert into t_party_contact_mechanism(id, party_fk, eaddress_fk, type_fk, purpose_fk) values(101, 100, 101, 100, 101);

insert into t_eaddress(id, address, hash) values(102, 'ray.sponsible@hotmail.com', MD5('ray.sponsible@hotmail.com'));
insert into t_party_contact_mechanism(id, party_fk, eaddress_fk, type_fk, purpose_fk, no_solicitation, privacy) values(102, 100, 102, 100, 102, 1, 'H');

insert into t_eaddress(id, address, hash) values(121, 'http://ray.sponsible.com', MD5('http://ray.sponsible.com'));
insert into t_party_contact_mechanism(id, party_fk, eaddress_fk, type_fk, purpose_fk) values(121, 100, 121, 200, 201);

insert into t_eaddress(id, address, hash) values(122, 'https://facebook.com/ray_sponsible', MD5('https://facebook.com/ray.sponsible'));
insert into t_party_contact_mechanism(id, party_fk, eaddress_fk, type_fk, purpose_fk) values(122, 100, 122, 200, 202);

insert into t_paddress(id, street1, city, state_code, zip_code, country_code, hash) values (131, '3030 Linton', 'Montreal', 'QC', 'H1K 1H3', 'CAN', '4304039');
insert into t_party_contact_mechanism(id, party_fk, paddress_fk, type_fk, purpose_fk) values(131, 100, 131, 300, 301);

insert into t_phone(id, country_code, number, extension, hash) values (141, 'CAN', '5147580101', null, '5147580101');
insert into t_party_contact_mechanism(id, party_fk, phone_fk, type_fk, purpose_fk) values(141, 100, 141, 400, 401);

insert into t_phone(id, country_code, number, extension, hash) values (142, 'USA', '5147580102', '123', '5147580102');
insert into t_party_contact_mechanism(id, party_fk, phone_fk, type_fk, purpose_fk, no_solicitation, privacy) values(142, 100, 142, 400, 402, 1, 'H');
