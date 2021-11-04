alter table COMMENT modify COMMENTATOR BIGINT not null;
alter table QUESTION modify ID BIGINT auto_increment;
alter table QUESTION modify CREATOR BIGINT;
alter table USER modify ID BIGINT auto_increment;
