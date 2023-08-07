create database okowan;

create table okowan.users
(
    id           bigint auto_increment primary key,
    username     varchar(50)  not null unique,
    password     varchar(255) not null,
    introduction varchar(100) not null
);