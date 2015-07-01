-- Contact type
INSERT INTO t_contact_mechanism_type(id, name) VALUES(300, 'postal');

-- Contact purpose
INSERT INTO t_contact_mechanism_purpose(id, name) VALUES(301, 'postal_address');

-- Create the addresses
INSERT INTO pdr.t_paddress(id, hash)
    SELECT DISTINCT pattr_party_fk, pattr_party_fk
    FROM is5.pattr
    WHERE pattr_name in ("street", "city", "state", "zip_code", "country");

UPDATE pdr.t_paddress P
  JOIN is5.pattr A ON P.id=A.pattr_party_fk
SET P.street1=A.pattr_value
WHERE A.pattr_name='street';

UPDATE pdr.t_paddress P
  JOIN is5.pattr A ON P.id=A.pattr_party_fk
SET P.city=A.pattr_value
WHERE A.pattr_name='city';

UPDATE pdr.t_paddress P
  JOIN is5.pattr A ON P.id=A.pattr_party_fk
SET P.state_code=A.pattr_value
WHERE A.pattr_name='state';

UPDATE pdr.t_paddress P
  JOIN is5.pattr A ON P.id=A.pattr_party_fk
SET P.zip_code=A.pattr_value
WHERE A.pattr_name='zip_code';

UPDATE pdr.t_paddress P
  JOIN is5.pattr A ON P.id=A.pattr_party_fk
SET P.country_code=A.pattr_value
WHERE A.pattr_name='country' AND LENGTH(A.pattr_value)=3;

UPDATE pdr.t_paddress SET state_code='CT' WHERE LOWER(state_code)='connecticut';
UPDATE pdr.t_paddress SET state_code='NY' WHERE LOWER(state_code)='new york';

UPDATE pdr.t_paddress SET state_code='AB' WHERE LOWER(state_code)='alberta';
UPDATE pdr.t_paddress SET state_code='BC' WHERE LOWER(state_code)='british columbia';
UPDATE pdr.t_paddress SET state_code='QC' WHERE LOWER(state_code)='quebec';
UPDATE pdr.t_paddress SET state_code='MB' WHERE LOWER(state_code)='manitoba';
UPDATE pdr.t_paddress SET state_code='NS' WHERE LOWER(state_code)='nova scotia';
UPDATE pdr.t_paddress SET state_code='NB' WHERE LOWER(state_code)='new brunswick';
UPDATE pdr.t_paddress SET state_code='ON' WHERE LOWER(state_code)='ontario';

-- Compute hash in temporary table
DROP TABLE IF EXISTS  tmp_hash;
CREATE TABLE tmp_hash(id BIGINT, hash VARCHAR(32));
INSERT INTO tmp_hash(id) SELECT id FROM pdr.t_paddress;

UPDATE tmp_hash H
    JOIN pdr.t_paddress A ON H.id=A.id
    SET H.hash=MD5(
        LOWER(
            CONCAT(
                IFNULL(street1, ""), "-",
                IFNULL(street2, ""), "-",
                IFNULL(city, ""), "-",
                IFNULL(state_code, ""), "-",
                IFNULL(zip_code, ""), "-",
                IFNULL(country_code, "")
            )
        )
    );

UPDATE IGNORE pdr.t_paddress A
  JOIN tmp_hash H ON H.id=A.id
  SET A.hash=H.hash;

DELETE FROM pdr.t_paddress WHERE LENGTH(hash) < 32;

-- create party addresses
INSERT INTO pdr.t_party_contact_mechanism(party_fk, paddress_fk, type_fk, purpose_fk, no_solicitation)
  SELECT DISTINCT H.id, P.id, 300, 301, 0
  FROM tmp_hash H JOIN pdr.t_paddress P ON  H.hash=P.hash;

-- cleanup
DROP TABLE IF EXISTS  tmp_hash;
