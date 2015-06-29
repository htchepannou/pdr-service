insert into t_party(
    id,
    name,
    first_name,
    last_name,
    prefix,
    suffix,
    birth_date,
    gender,
    kind,
    height,
    weight
) values (
    1,
    'Ray Sponsible',
    'Ray',
    'Sponsible',
    'Mr',
    'PHD',
    '1973-12-27',
    'M',
    'P',
    182,
    250
);

insert into t_user (
    id,
    party_fk,
    login,
    password,
    from_date,
    to_date,
    status
) values (
    1,
    1,
    'ray.sponsible',
    'secret',
    '1973-12-27 10:30:45',
    null,
    'A'
);
