DROP DATABASE IF EXISTS task;
CREATE DATABASE task;

\c task;

CREATE EXTENSION postgres_fdw;
grant usage on FOREIGN DATA WRAPPER postgres_fdw to postgres;

-- configre first remote db
CREATE SERVER books_1_server 
FOREIGN DATA WRAPPER postgres_fdw 
OPTIONS(host 'postgres-b1', port '5432', dbname 'task' );

GRANT USAGE ON FOREIGN SERVER books_1_server TO postgres;


CREATE USER MAPPING FOR postgres
SERVER books_1_server
OPTIONS (user 'postgres', password 'password');

CREATE FOREIGN TABLE books_1 (
    id bigint not null,
    category_id  int not null,
    author character varying not null,
    title character varying not null,
    year int not null 
)
SERVER books_1_server
OPTIONS (schema_name 'public', table_name 'books_c_1');


-- configre second remote db
CREATE SERVER books_2_server 
FOREIGN DATA WRAPPER postgres_fdw 
OPTIONS(host 'postgres-b2', port '5432', dbname 'task' );

GRANT USAGE ON FOREIGN SERVER books_2_server TO postgres;


CREATE USER MAPPING FOR postgres
SERVER books_2_server
OPTIONS (user 'postgres', password 'password');

CREATE FOREIGN TABLE books_2 (
    id bigint not null,
    category_id  int not null,
    author character varying not null,
    title character varying not null,
    year int not null 
)
SERVER books_2_server
OPTIONS (schema_name 'public', table_name 'books_c_2');

-- create view
CREATE VIEW books AS 
    SELECT * FROM books_1
    UNION ALL
    SELECT * FROM books_2;

-- configure rules
CREATE RULE books_insert AS ON INSERT TO books
DO INSTEAD NOTHING;
CREATE RULE books_update AS ON UPDATE TO books
DO INSTEAD NOTHING;
CREATE RULE books_delete AS ON DELETE TO books
DO INSTEAD NOTHING;

CREATE RULE books_insert_to_1 AS ON INSERT TO books
WHERE (category_id = 1)
DO INSTEAD INSERT INTO books_1 VALUES (NEW.*);

CREATE RULE books_insert_to_2 AS ON INSERT TO books
WHERE (category_id = 2)
DO INSTEAD INSERT INTO books_2 VALUES (NEW.*);

-- usefule procedures 
CREATE OR REPLACE FUNCTION random_between(low INT ,high INT) 
   RETURNS INT AS
$$
BEGIN
   RETURN floor(random()* (high-low + 1) + low);
END;
$$ language 'plpgsql' STRICT;


create or replace procedure populate(
   amount int
)
language plpgsql    
as $$
declare 
   counter integer := 0;
   category integer := 0;
begin
    while counter < amount loop
        category := (counter % 2) + 1;

        insert into books 
        values (counter, category, 'Author ' || counter, 'Title ' || counter, random_between(1900, 2022));

        counter := counter + 1;
    end loop;
    commit;
    
end;
$$;

commit;
