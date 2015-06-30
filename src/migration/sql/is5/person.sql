INSERT INTO pdr.t_party(id, deleted, kind, name)
    SELECT party_id, party_deleted, 'P', party_id FROM is5.party WHERE party_type_fk=1;


UPDATE pdr.t_party P
  JOIN is5.pattr A ON P.id=A.pattr_party_fk
SET P.gender=A.pattr_value
WHERE A.pattr_name='gender';


UPDATE pdr.t_party P
    JOIN is5.pattr A ON P.id=A.pattr_party_fk
    SET P.first_name=A.pattr_value
    WHERE A.pattr_name='first_name';

UPDATE pdr.t_party P
  JOIN is5.pattr A ON P.id=A.pattr_party_fk
SET P.first_name=A.pattr_value
WHERE A.pattr_name='first name';


UPDATE pdr.t_party P
  JOIN is5.pattr A ON P.id=A.pattr_party_fk
  SET P.last_name=A.pattr_value
  WHERE A.pattr_name='last_name';

UPDATE pdr.t_party P
  JOIN is5.pattr A ON P.id=A.pattr_party_fk
SET P.last_name=A.pattr_value
WHERE A.pattr_name='last name';


UPDATE pdr.t_party SET name=CONCAT(IFNULL(first_name, ''), ' ', IFNULL(last_name, ''));
