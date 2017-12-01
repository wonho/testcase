create schema happyhouse AUTHORIZATION DBA;
set schema happyhouse;
SET DATABASE DEFAULT INITIAL SCHEMA happyhouse;
 
drop table tbl_user if exists;

drop table tbl_mon_packet if exists;
 
CREATE TABLE tbl_user (
  id varchar(40) NOT NULL,
  username varchar(45) NOT NULL,
  password varchar(45) NOT NULL
);

CREATE TABLE tbl_mon_packet (
	id varchar(45) NOT NULL,       
	header varchar(45) NOT NULL,      
	data varchar(45) NOT NULL,       
	tail varchar(45) NOT NULL      
);

