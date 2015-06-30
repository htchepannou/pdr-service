INSERT INTO t_user(id, party_fk, login, deleted, status, from_date)
  SELECT party_id, party_id, CONCAT('user-', party_id, '-', RAND()), party_deleted, 'A', party_creation_date FROM is5.party WHERE party_type_fk=1;

UPDATE IGNORE pdr.t_user U
  JOIN is5.pattr A ON U.id=A.pattr_party_fk
  JOIN is5.party P ON U.id=P.party_id
SET U.login=A.pattr_value
WHERE A.pattr_name='uname' AND P.party_deleted=false
;

UPDATE pdr.t_user U
  JOIN is5.pattr A ON U.id=A.pattr_party_fk
SET U.password=A.pattr_value
WHERE A.pattr_name='password';

INSERT INTO pdr.t_domain_user(domain_fk, user_fk, role_fk)
  SELECT 1, id, 1 FROM pdr.t_user;

INSERT INTO pdr.t_domain_user(domain_fk, user_fk, role_fk)
  SELECT 2, id, 100 FROM pdr.t_user U JOIN is5.sysadmin A ON U.id=A.sysadmin_id;
