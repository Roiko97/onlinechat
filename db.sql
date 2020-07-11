DROP database IF EXISTS `onlinechat`;
create database onlinechat;
use onlinechat;
create table userchat(
	message text,
    fromuser varchar(30),
    touser varchar(30)
);
create table user(
	name varchar(30) not null,
    account varchar(30) primary key,
    email varchar(30) default null,
    phone char(11) default null,
    password varchar(30) default 123456 not null
);
insert into user values("张三","zhangsan",null,null,"123456");
insert into user values("李四","lisi",null,null,"123456");