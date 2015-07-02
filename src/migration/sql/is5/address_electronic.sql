INSERT INTO t_contact_mechanism_type(id, name) VALUES(100, 'email');

INSERT INTO t_contact_mechanism_type(id, name) VALUES(200, 'web');

INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(101, 'primary_email');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(102, 'alternate_email');

INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(201, 'website');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(202, 'league_fixture');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(203, 'league_standing');
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(204, 'state_standing');



INSERT INTO pdr.t_eaddress(address, hash)
    SELECT DISTINCT LOWER(pattr.pattr_value), MD5(LOWER(pattr.pattr_value))
      FROM is5.pattr
      WHERE pattr_name IN ('email', 'website_url', 'league_fixture_url', 'league_standing_url', 'state_standing_url');

INSERT INTO pdr.t_party_contact_mechanism(party_fk, eaddress_fk, type_fk, purpose_fk, no_solicitation)
  SELECT DISTINCT pattr_party_fk, id, 100, 101, 0
    FROM is5.pattr JOIN pdr.t_eaddress ON  MD5(LOWER(pattr_value))=hash
    WHERE pattr_name = 'email';

INSERT INTO pdr.t_party_contact_mechanism(party_fk, eaddress_fk, type_fk, purpose_fk, no_solicitation)
  SELECT DISTINCT pattr_party_fk, id, 200, 201, 0
  FROM is5.pattr JOIN pdr.t_eaddress ON  MD5(LOWER(pattr_value))=hash
  WHERE pattr_name = 'website_url';

INSERT INTO pdr.t_party_contact_mechanism(party_fk, eaddress_fk, type_fk, purpose_fk, no_solicitation)
  SELECT DISTINCT pattr_party_fk, id, 200, 202, 0
  FROM is5.pattr JOIN pdr.t_eaddress ON  MD5(LOWER(pattr_value))=hash
  WHERE pattr_name = 'league_fixture_url';

INSERT INTO pdr.t_party_contact_mechanism(party_fk, eaddress_fk, type_fk, purpose_fk, no_solicitation)
  SELECT DISTINCT pattr_party_fk, id, 200, 203, 0
  FROM is5.pattr JOIN pdr.t_eaddress ON  MD5(LOWER(pattr_value))=hash
  WHERE pattr_name = 'league_standing_url';

INSERT INTO pdr.t_party_contact_mechanism(party_fk, eaddress_fk, type_fk, purpose_fk, no_solicitation)
  SELECT DISTINCT pattr_party_fk, id, 200, 204, 0
  FROM is5.pattr JOIN pdr.t_eaddress ON  MD5(LOWER(pattr_value))=hash
  WHERE pattr_name = 'state_standing_url';
