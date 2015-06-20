create table t_domain(
    id int not null primary key auto_increment,

    name varchar(50) not null,
    description varchar(255)
) engine=InnoDB;

create table t_party(
    id int not null primary key auto_increment,

    name varchar(100) not null,
    first_name varchar(50),
    last_name varchar(50),
    prefix varchar(10),
    suffix varchar(10),
    birth_date date,
    gender char(1),
    kind char(1),
    height int,
    weight int
);
