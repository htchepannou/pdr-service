INSERT INTO pdr.t_party(id, kind, name, from_date)
    SELECT party_id, 'O', party_id, party_creation_date FROM is5.party WHERE party_type_fk in (3,4);


UPDATE pdr.t_party P
    JOIN is5.pattr A ON P.id=A.pattr_party_fk
SET P.gender=IF(A.pattr_value='B', 'M', IF(A.pattr_value='G', 'F', A.pattr_value))
WHERE A.pattr_name='gender' AND P.kind='O';

UPDATE pdr.t_party P
    JOIN is5.pattr A ON P.id=A.pattr_party_fk
    SET P.name=A.pattr_value
    WHERE A.pattr_name='name' AND P.kind='O';

-- Party roles
INSERT INTO t_party_role(party_fk, type_fk, from_date)
    SELECT party_id, 100, party_creation_date FROM is5.party WHERE party_type_fk=3;

INSERT INTO t_party_role(party_fk, type_fk, from_date)
    SELECT party_id, 200, party_creation_date FROM is5.party WHERE party_type_fk=4;
