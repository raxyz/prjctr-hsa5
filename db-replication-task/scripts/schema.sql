DROP DATABASE IF EXISTS task;
CREATE DATABASE task;
USE task;

CREATE TABLE names (
    name VARCHAR(255) NOT NULL
) ENGINE=INNODB;

insert into names
values
("name 1");

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    nickname VARCHAR(255) NOT NULL
) ENGINE=INNODB;

insert into users (first_name, last_name, nickname) values ("name 1", "surname 1", "nick 1");