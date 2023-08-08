create database okowan;

create table okowan.users
(
    id           bigint auto_increment primary key,
    username     varchar(50)  not null unique,
    password     varchar(255) not null,
    introduction varchar(100) not null
);

insert into okowan.users(username, password, nickname, introduction, address) VALUES ('user1@email.com', 1234, 'nickname1', '안녕하세요1', 'XX시 XX구');
insert into okowan.users(username, password, nickname, introduction, address) VALUES ('user2@email.com', 1234, 'nickname2', '안녕하세요2', 'XX시 OO구');
insert into okowan.users(username, password, nickname, introduction, address) VALUES ('user3@email.com', 1234, 'nickname3', '안녕하세요3', 'OO시 XX구');
insert into okowan.users(username, password, nickname, introduction, address) VALUES ('user4@email.com', 1234, 'nickname4', '안녕하세요4', 'OO시 OO구');
insert into okowan.users(username, password, nickname, introduction, address) VALUES ('user5@email.com', 1234, 'nickname5', '안녕하세요5', 'QQ시 QQ구');
