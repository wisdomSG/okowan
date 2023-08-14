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



INSERT INTO okowan.boards (created_at, modified_at, color, description, title)
VALUES
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'RED', '보드 설명1', '보드 리스트 1'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'RED', '보드 설명2', '보드 리스트 2'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'RED', '보드 설명3', '보드 리스트 3'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'RED', '보드 설명4', '보드 리스트 4'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'RED', '보드 설명5', '보드 리스트 5'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'RED', '보드 설명6', '보드 리스트 6'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'RED', '보드 설명7', '보드 리스트 7'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'RED', '보드 설명8', '보드 리스트 8'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'RED', '보드 설명9', '보드 리스트 9'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'RED', '보드 설명10', '보드 리스트 10'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'RED', '보드 설명11', '보드 리스트 11'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'RED', '보드 설명12', '보드 리스트 12'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'RED', '보드 설명13', '보드 리스트 13'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'RED', '보드 설명14', '보드 리스트 14'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'RED', '보드 설명15', '보드 리스트 15');


INSERT INTO okowan.categories (created_at, modified_at, order_stand, title, board_id)
VALUES
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 'To do', 1),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 'Done', 1),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, '카테고리1', 1),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '카테고리1', 2),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '카테고리2', 2),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, '카테고리3', 2),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, '카테고리3', 2);

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


INSERT INTO okowan.cards (color, title, board_id, category_id, user_id)
VALUES
    ('RED', '카드 제목1', '')




