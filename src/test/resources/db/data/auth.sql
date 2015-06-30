insert into t_role(id, name) values(100, 'admin');

insert into t_party(id, name, first_name, last_name, prefix, suffix, birth_date, gender, kind, height, weight)
values (100, 'Ray Sponsible', 'Ray', 'Sponsible', 'Mr', 'PHD', '1973-12-27', 'M', 'P', 182, 250);

insert into t_domain(id, name, description) values(100, 'admin.moralab.com', 'Admin site');

insert into t_user (id, party_fk, login, password, from_date, to_date, status) values (
  100, 100, 'ray.sponsible', 'SCHublZJUN03uJ576K868m63mM0Sa/83', '1973-12-27 10:30:45', null, 'A');

insert into t_domain_user(id, domain_fk, user_fk, role_fk, from_date) values(
  100, 100, 100, 100, '2014-12-27 10:30:45');

-- findById / login
insert into t_access_token(id, user_fk, domain_fk, from_date, expiry_date, expired) values (
    100, 100, 100, '1973-12-27 10:30:45', '2020-12-27 10:30:45', 0);

-- findById_expired
insert into t_access_token(id, user_fk, domain_fk, from_date, expiry_date, expired) values (
  200, 100, 100, '2015-01-30 10:30:45', '2015-01-01 10:30:45', 0);

-- logout
insert into t_access_token(id, user_fk, domain_fk, from_date, expiry_date, expired) values (
  300, 100, 100, '2015-01-30 10:30:45', '2020-01-01 10:30:45', 0);

-- logout_expired
insert into t_access_token(id, user_fk, domain_fk, from_date, expiry_date, expired) values (
  400, 100, 100, '2015-01-30 10:30:45', '2015-01-01 10:30:45', 0);

-- login_user_deleted
insert into t_user (id, party_fk, login, password, from_date, to_date, status, deleted) values (
  500, 100, 'ray500.sponsible', 'SCHublZJUN03uJ576K868m63mM0Sa/83', '1973-12-27 10:30:45', null, 'A', 1);
insert into t_domain_user(id, domain_fk, user_fk, role_fk, from_date) values(
  500, 100, 500, 100, '2014-12-27 10:30:45');

-- login_access_denied
insert into t_user (id, party_fk, login, password, from_date, to_date, status) values (
  600, 100, 'ray600.sponsible', 'SCHublZJUN03uJ576K868m63mM0Sa/83', '1973-12-27 10:30:45', null, 'A');
