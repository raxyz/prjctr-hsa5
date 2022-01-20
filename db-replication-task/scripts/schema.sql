DROP DATABASE IF EXISTS task;
CREATE DATABASE task;
USE task;

CREATE TABLE names (
    name VARCHAR(255) NOT NULL
) ENGINE=INNODB;

insert into names
values
("name 1");
