alter table comment modify commentator BIGINT not null;
alter table question modify id BIGINT auto_increment;
alter table question modify creator BIGINT;
alter table user modify id BIGINT auto_increment;
