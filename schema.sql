drop table if exists file;

create table file(
  id bigint auto_increment ,
  name varchar(100) not null ,
  md5 varchar(32) ,
  path varchar(100) not null ,
  upload_time datetime(3) not null ,
  primary key (id)
);
create table t_admin
(
  id       int auto_increment
    primary key,
  username varchar(50)  not null,
  password varchar(100) not null,
  constraint t_admin_password_uindex
    unique (password),
  constraint t_admin_username_uindex
    unique (username)
);
