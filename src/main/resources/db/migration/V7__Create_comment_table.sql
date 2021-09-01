create table comment
(
    id BIGINT,
    parent_id BIGINT not null,
    type int,
    commentator int not null,
    gmt_create BIGINT not null,
    gmt_modified bigint not null,
    like_count bigint default 0,
    constraint COMMENT_PK
        primary key (id)
);