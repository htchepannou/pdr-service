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
    from_date DATETIME,
    to_date DATETIME,

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
    name VARCHAR(100) NOT NULL UNIQUE
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
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE t_contact_mechanism_purpose(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE t_eaddress(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,

    hash VARCHAR(32) NOT NULL UNIQUE,
    address TEXT NOT NULL
);

CREATE TABLE t_paddress(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,

    hash VARCHAR(32) NOT NULL UNIQUE,
    street1 VARCHAR(100),
    street2 VARCHAR(100),
    city VARCHAR(50),
    zip_code VARCHAR(30),
    state_code VARCHAR(30),
    country_code VARCHAR(3)
);

CREATE TABLE t_phone(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,

    hash VARCHAR(32) NOT NULL UNIQUE,
    country_code VARCHAR(3),
    number VARCHAR(20),
    extension VARCHAR(10)
);


CREATE TABLE t_party_contact_mechanism(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,

    party_fk BIGINT NOT NULL REFERENCES t_party(id),
    type_fk BIGINT NOT NULL REFERENCES t_contact_mechanism_type(id),
    purpose_fk BIGINT REFERENCES t_contact_mechanism_purpose(id),
    paddress_fk BIGINT REFERENCES t_paddress(id),
    eaddress_fk BIGINT REFERENCES t_eaddress(id),
    phone_fk BIGINT REFERENCES t_phone(id),

    no_solicitation BIT(1),
    privacy CHAR(1)
);

CREATE TABLE t_party_role_type(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE t_party_relationship_type(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,

    from_fk BIGINT NOT NULL REFERENCES t_party_role_type(id),
    to_fk BIGINT NOT NULL REFERENCES t_party_role_type(id),

    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE t_party_role(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,

    type_fk BIGINT NOT NULL REFERENCES t_party_role_type(id),
    party_fk BIGINT NOT NULL REFERENCES t_party(id),

    from_date DATETIME,
    to_date DATETIME
);





