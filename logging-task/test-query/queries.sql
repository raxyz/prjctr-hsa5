DROP DATABASE IF EXISTS task;
CREATE DATABASE IF NOT EXISTS task;
USE task;

CREATE TABLE names (
    name VARCHAR(255) NOT NULL
) ENGINE=INNODB;
insert into names
values 
('Dayanara'),('Brooke'),('Cheyanne'),('Logan'),('Kellen'),('Andrew'),('Cory'),
('Serena'),('Reagan'),('Keon'),('Amy'),('Dahlia'),('Karli'),('Miranda'),
('Kiera'),('Armani'),('Roselyn'),('Davion'),('Makaila'),('Larissa'),('Ruby'),
('Destinee'),('Fernanda'),('Lorelai'),('Tripp'),('Emily'),('Ryan'),('Kamren'),
('Charlotte'),('Keshawn'),('Desirae'),('Jayden'),('Elle'),('Jaidyn'),('Sidney'),
('Declan'),('Kenley'),('Esperanza'),('Gregory'),('Lucas'),('Nicole'),('Rudy'),
('Aiden'),('Essence'),('Elisha'),('Annika'),('Mira'),('Harrison'),('Jaime'),
('Darien'),('Charlee'),('Addison'),('Nevaeh'),('Ansley'),('Lucas'),('Sarah'),
('Semaj'),('Zachary'),('Rylan'),('Rhianna'),('Jamar'),('Cadence'),('Brayden'),
('Adrienne'),('Cameron'),('Byron'),('Ramon'),('Jaelynn'),('Itzel'),('Karlie'),
('Levi'),('Ada'),('Ryan'),('Jerry'),('Ivy'),('Aldo'),('Kody'),('Tori'),
('Hannah'),('Luciana'),('Jaeden'),('Ariella'),('Yasmine'),('Rafael'),('Jenny'),
('Kamron'),('Jaslene'),('Heaven'),('Sandra'),('Nathaly'),('Jaron'),('London'),
('Michaela'),('Elvis'),('Lola'),('Deacon'),('Esteban'),('Layla'),('Nadia');

CREATE TABLE users (
    name VARCHAR(255) NOT NULL,
    birthday DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)  ENGINE=INNODB;

delimiter //
create procedure populate (in num int)
begin
declare i int default 0;
declare min datetime default '1920-01-01 00:00:00';
declare max datetime default '2021-01-01 00:00:00';

while i < num do
insert into users (name,birthday) 
SELECT
(SELECT name FROM names ORDER BY RAND() LIMIT 1) as name,
(SELECT DATE(TIMESTAMPADD(SECOND, FLOOR(RAND() * TIMESTAMPDIFF(SECOND, min, max)), min))) as birthday;
set i = i + 1;
end while;
end //
delimiter ;

commit;