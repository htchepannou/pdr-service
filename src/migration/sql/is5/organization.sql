INSERT INTO pdr.t_party(id, deleted, kind, name, from_date)
    SELECT party_id, party_deleted, 'O', party_id, party_creation_date FROM is5.party WHERE party_type_fk in (3,4);


UPDATE pdr.t_party P
    JOIN is5.pattr A ON P.id=A.pattr_party_fk
    SET P.name=A.pattr_value
    WHERE A.pattr_name='name';
