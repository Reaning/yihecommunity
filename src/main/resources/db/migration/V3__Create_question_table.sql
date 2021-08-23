create table question
(
    id int,
    title varchar(50),
    description TEXT,
    gmt_create bigint,
    gmt_modified int,
    creator int,
    comment_count int default 0,
    view_count int default 0,
    like_count int default 0,
    tag varchar(256),
    constraint QUESTION_PK
        primary key (id)
);