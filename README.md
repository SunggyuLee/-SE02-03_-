# 소프트웨어공학 텀 프로젝트
3조 김성제, 박종국, 임동근, 이성규
고생하셨음
* 데이터 베이스 구축
```mysql
create database se_term_1;

use se_term_1;
```
* user 테이블 생성
```mysql
create table user(
  userId varchar(20) primary key not null ,
  pwd varchar(20) not null ,
  name varchar(20) not null ,
  birth varchar(20) not null ,
  addr varchar(20) not null ,
  phoneNum varchar(20) not null ,
  avgGrade float(5) not null default 0
);
```
* allsubject 테이블 생성
```mysql
create table allSubject(
  subjectName varchar(20) primary key not null ,
  profName varchar(20) not null ,
  credit integer(1) not null 
);
```
* subject 테이블 생성
```mysql
create table subject(
  classIdNum varchar(20) primary key not null ,
  subjectName varchar(20) not null ,
  classNum varchar(2) not null ,
  classTime varchar(40) not null ,
  classRoom varchar(40) not null ,
  syllabus varchar(40) not null ,
  availNum integer(2) not null ,
  foreign key (subjectName) references allSubject(subjectName) on delete
    cascade on UPDATE cascade 
);
```
* grade 테이블 생성
```mysql
create table grade(
  classIdNum varchar(20) not null ,
  userId varchar(20) not null ,
  grade float(5) default 0 not null ,
  foreign key (classIdNum) references subject(classIdNum) on delete
    cascade on update cascade,
  foreign key (userId) references user(userId) on delete 
    cascade on update cascade ,
  primary key (classIdNum, userId)
);
```

