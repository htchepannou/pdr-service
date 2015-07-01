CREATE TABLE t_domain(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,

    deleted BIT DEFAULT 0,
    from_date DATETIME,
    to_date DATETIME,

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
    from_date DATETIME,
    to_date DATETIME,

    login VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(50),
    status CHAR(1)
);

CREATE TABLE t_role(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE t_permission(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE t_role_permission(
    role_fk BIGINT NOT NULL REFERENCES t_role(id),
    permission_fk BIGINT NOT NULL REFERENCES t_permission(id),

    PRIMARY KEY (role_fk, permission_fk)
);


CREATE TABLE t_domain_user(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,

    domain_fk BIGINT NOT NULL REFERENCES t_domain(id),
    user_fk BIGINT NOT NULL REFERENCES t_user(id),
    role_fk BIGINT NOT NULL REFERENCES t_role(id),

    from_date DATETIME,

    UNIQUE(domain_fk, user_fk, role_fk)
);
    
CREATE TABLE t_access_token(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,

    user_fk BIGINT NOT NULL REFERENCES t_user(id),
    domain_fk BIGINT NOT NULL REFERENCES t_domain(id),

    from_date DATETIME,
    to_date DATETIME,
    expiry_date DATETIME NOT NULL,

    expired BIT(1),
    user_agent VARCHAR(2048),
    remote_ip VARCHAR(50)
);

CREATE TABLE t_contact_mechanism_type(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE t_contact_mechanism_purpose(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE t_eaddress(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,

    hash VARCHAR(32) NOT NULL UNIQUE,
    address TEXT
);

CREATE TABLE t_party_eaddress(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,

    party_fk BIGINT NOT NULL REFERENCES t_party(id),
    contact_fk BIGINT NOT NULL REFERENCES t_eaddress(id),
    type_fk BIGINT NOT NULL REFERENCES t_contact_mechanism_type(id),
    purpose_fk BIGINT NOT NULL REFERENCES t_contact_mechanism_purpose(id),

    no_solicitation BIT(1),
    privacy CHAR(1),
    UNIQUE (party_fk, type_fk, purpose_fk)
);

