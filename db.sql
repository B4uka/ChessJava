create table role
(
    id   int auto_increment
primary key,
    name varchar(50) null
)
charset = latin1;

create table user
(
    id         int auto_increment
primary key,
    username   varchar(50) not null,
    password   char(80)    not null,
    first_name varchar(50) not null,
    last_name  varchar(50) not null,
    email      varchar(50) not null
)
charset = latin1;

create table user_without_security
(
    id                    int auto_increment
primary key,
    first_name            varchar(45)                        null,
    last_name             varchar(45)                        null,
    email                 varchar(45)                        null,
    account_creation_date datetime default CURRENT_TIMESTAMP null
)
charset = latin1;

create table users_roles
(
    user_id int not null,
    role_id int not null,
    primary key (user_id, role_id),
    constraint FK_ROLE
foreign key (role_id) references role (id),
    constraint FK_USER_05
foreign key (user_id) references user (id)
)
charset = latin1;

create index FK_ROLE_idx
on users_roles (role_id);