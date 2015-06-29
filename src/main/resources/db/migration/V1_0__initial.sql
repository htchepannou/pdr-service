CREATE TABLE t_domain(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,

    deleted BIT DEFAULT 0,

    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
) engine=InnoDB;

CREATE TABLE t_party(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,

    deleted BIT DEFAULT 0,

    name VARCHAR(100) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    prefix VARCHAR(10),
    suffix VARCHAR(10),
    birth_date date,
    gender CHAR(1),
    kind CHAR(1),
    height INT,
    weight INT
);

CREATE TABLE t_user(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,

    party_fk BIGINT NOT NULL REFERENCES t_party(id),

    deleted BIT DEFAULT 0,

    login VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50),
    from_date DATETIME,
    to_date DATETIME,
    status CHAR(1)
);
