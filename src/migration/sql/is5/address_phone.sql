-- Contact type
INSERT INTO t_contact_mechanism_type(id, name) VALUES(400, 'phone');

-- Contact purpose
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(401, 'primary_phone');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(402, 'home_phone');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(403, 'work_phone');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(404, 'mobile_phone');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(405, 'fax_phone');

-- Create the addresses
INSERT INTO pdr.t_phone(number, hash)
  SELECT DISTINCT LOWER(pattr.pattr_value), MD5(LOWER(CONCAT("-", pattr.pattr_value, "-")))
  FROM is5.pattr
  WHERE pattr_name IN ('work_phone', 'mobile_phone', 'fax_phone', 'phone', 'home_phone')
        AND LENGTH(pattr_value)<=20;

INSERT INTO pdr.t_party_contact_mechanism(party_fk, phone_fk, type_fk, purpose_fk, no_solicitation)
  SELECT DISTINCT pattr_party_fk, id, 400, 401, 0
  FROM is5.pattr JOIN pdr.t_phone ON  MD5(LOWER(CONCAT("-", pattr.pattr_value, "-")))=hash
  WHERE pattr_name = 'phone';

INSERT INTO pdr.t_party_contact_mechanism(party_fk, phone_fk, type_fk, purpose_fk, no_solicitation)
  SELECT DISTINCT pattr_party_fk, id, 400, 402, 0
  FROM is5.pattr JOIN pdr.t_phone ON  MD5(LOWER(CONCAT("-", pattr.pattr_value, "-")))=hash
  WHERE pattr_name = 'home_phone';

INSERT INTO pdr.t_party_contact_mechanism(party_fk, phone_fk, type_fk, purpose_fk, no_solicitation)
  SELECT DISTINCT pattr_party_fk, id, 400, 403, 0
  FROM is5.pattr JOIN pdr.t_phone ON  MD5(LOWER(CONCAT("-", pattr.pattr_value, "-")))=hash
  WHERE pattr_name = 'work_phone';

INSERT INTO pdr.t_party_contact_mechanism(party_fk, phone_fk, type_fk, purpose_fk, no_solicitation)
  SELECT DISTINCT pattr_party_fk, id, 400, 404, 0
  FROM is5.pattr JOIN pdr.t_phone ON  MD5(LOWER(CONCAT("-", pattr.pattr_value, "-")))=hash
  WHERE pattr_name = 'mobile_phone';

INSERT INTO pdr.t_party_contact_mechanism(party_fk, phone_fk, type_fk, purpose_fk, no_solicitation)
  SELECT DISTINCT pattr_party_fk, id, 400, 405, 0
  FROM is5.pattr JOIN pdr.t_phone ON  MD5(LOWER(CONCAT("-", pattr.pattr_value, "-")))=hash
  WHERE pattr_name = 'fax_phone';


