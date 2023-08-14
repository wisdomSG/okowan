create database okowan;

create table okowan.users
(
    id           bigint auto_increment primary key,
    username     varchar(50)  not null unique,
    password     varchar(255) not null,
    nickname     varchar(50)  not null,
    introduction varchar(255) not null,
    address      varchar(255)
);

insert into okowan.users(username, password, nickname, introduction, address) VALUES ('user1@email.com', 1234, 'nickname1', '안녕하세요1', 'XX시 XX구');
insert into okowan.users(username, password, nickname, introduction, address) VALUES ('user2@email.com', 1234, 'nickname2', '안녕하세요2', 'XX시 OO구');
insert into okowan.users(username, password, nickname, introduction, address) VALUES ('user3@email.com', 1234, 'nickname3', '안녕하세요3', 'OO시 XX구');
insert into okowan.users(username, password, nickname, introduction, address) VALUES ('user4@email.com', 1234, 'nickname4', '안녕하세요4', 'OO시 OO구');
insert into okowan.users(username, password, nickname, introduction, address) VALUES ('user5@email.com', 1234, 'nickname5', '안녕하세요5', 'QQ시 QQ구');

insert into okowan.users(username, password, nickname, introduction, address)
VALUES
    ('okowan1@gmail.com', 1234, '소원', '안녕하세요1', 'XX시 XX구'),
    ('okowan2@gmail.com', 1234, '슬기', '안녕하세요1', 'XX시 XX구'),
    ('okowan3@gmail.com', 1234, '창민', '안녕하세요1', 'XX시 XX구'),
    ('okowan4@gmail.com', 1234, '인서', '안녕하세요1', 'XX시 XX구');

insert into okowan.users(username, password, nickname, introduction, address,o_auth_provider)
VALUES
    ('okowan11@gmail.com', 1234, '소원', '안녕하세요1', 'XX시 XX구', 'ORIGIN'),
    ('okowan22@gmail.com', 1234, '슬기', '안녕하세요1', 'XX시 XX구', 'ORIGIN'),
    ('okowan33@gmail.com', 1234, '창민', '안녕하세요1', 'XX시 XX구', 'ORIGIN'),
    ('okowan44@gmail.com', 1234, '인서', '안녕하세요1', 'XX시 XX구', 'ORIGIN'),
    ('okowan55@gmail.com', 1234, '정훈', '안녕하세요1', 'XX시 XX구', 'ORIGIN'),
    ('okowan66@gmail.com', 1234, '승범', '안녕하세요1', 'XX시 XX구', 'ORIGIN'),
    ('okowan77@gmail.com', 1234, '승철', '안녕하세요1', 'XX시 XX구', 'ORIGIN'),
    ('okowan88@gmail.com', 1234, '정은', '안녕하세요1', 'XX시 XX구', 'ORIGIN'),
    ('okowan99@gmail.com', 1234, '영민', '안녕하세요1', 'XX시 XX구', 'ORIGIN'),
    ('okowan12@gmail.com', 1234, '의성', '안녕하세요1', 'XX시 XX구', 'ORIGIN'),
    ('okowan23@gmail.com', 1234, '성민', '안녕하세요1', 'XX시 XX구', 'ORIGIN'),
    ('okowan34@gmail.com', 1234, '정은', '안녕하세요1', 'XX시 XX구', 'ORIGIN'),
    ('okowan45@gmail.com', 1234, '영민', '안녕하세요1', 'XX시 XX구', 'ORIGIN'),
    ('okowan56@gmail.com', 1234, '의성', '안녕하세요1', 'XX시 XX구', 'ORIGIN'),
    ('okowan78@gmail.com', 1234, '성민', '안녕하세요1', 'XX시 XX구', 'ORIGIN'),
    ('okowan89@gmail.com', 1234, '민지', '안녕하세요1', 'XX시 XX구', 'ORIGIN');





INSERT INTO okowan.user_boards (role, board_id, user_id)
VALUES
    ('OWNER', 1, 1),
    ('EDITOR', 1, 2),
    ('EDITOR', 1, 3),
    ('VIEWER', 1, 4),
    ('OWNER', 2, 5),
    ('EDITOR', 2, 6),
    ('EDITOR', 2, 7),
    ('VIEWER', 2, 8),
    ('OWNER', 3, 1),
    ('EDITOR', 3, 2),
    ('EDITOR', 3, 3),
    ('VIEWER', 3, 4),
    ('OWNER', 4, 1),
    ('EDITOR', 4, 6),
    ('EDITOR', 4, 7),
    ('VIEWER', 4, 8),
    ('OWNER', 5, 1),
    ('EDITOR', 5, 2),
    ('EDITOR', 5, 3),
    ('VIEWER', 5, 4);


INSERT INTO okowan.user_boards (role, board_id, user_id)
VALUES
    ('OWNER', 18, 10),
    ('OWNER', 19, 10),
    ('OWNER', 20, 10),
    ('OWNER', 21, 10),
    ('OWNER', 22, 10),
    ('OWNER', 23, 10),
    ('OWNER', 24, 10),
    ('OWNER', 25, 10),
    ('OWNER', 26, 10),
    ('OWNER', 27, 10),
    ('OWNER', 28, 10),
    ('OWNER', 29, 10);









