show databases;
create database IF NOT EXISTS mailclient DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
use mailclient;
show tables;
drop table IF EXISTS user_mail;
drop table IF EXISTS mails;
drop table IF EXISTS users;


create table users(
	uid int(10) not null primary key  AUTO_INCREMENT,
	username varchar(64) not null,
	password varchar(64) not null,
	smtp varchar(128) not null,
	pop3 varchar(128) not null,
	isvalid int(2) default 1
	)ENGINE=InnoDB AUTO_INCREMENT=2;

insert into users(uid, username,password,smtp,pop3,isvalid)values(1,'13197389627','Wc123456789','smtp.163.com','pop.163.com',1);

select * from users;

create table mails(
	mid int(10) not null primary key AUTO_INCREMENT,
	umid varchar(32) not null,
	toaddr varchar(128) not null,
	fromaddr varchar(128) not null,
	subject text not null,
	content longtext not null,
	stime datetime not null,
	unique(umid)
	)ENGINE=InnoDB AUTO_INCREMENT=2;

insert into mails(mid,umid,toaddr,fromaddr,subject,content,stime)values(1,'uniquecode','13197389627@163.com','sendtest@163.com','testsubject','testcontent','2019-04-16 10:00:17');

select * from mails;

create table user_mail(
	mid int(10) not null,
	uid int(10) not null,
	isdel int(2) default 0,
	isreceive int(2) default 0,
	isread int(2) default 0,
	sendcond int(2) default 2,
	primary key(mid,uid),
	foreign key(mid)references mails(mid),
	foreign key(uid)references users(uid));

insert into user_mail(mid,uid,isdel,isreceive,isread,sendcond)values(1,1,0,1,0,-1);

select * from user_mail;

