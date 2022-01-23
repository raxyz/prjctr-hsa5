DROP DATABASE IF EXISTS task;
CREATE DATABASE task;

\c task;

CREATE TABLE books (
    id bigint not null,
    category_id  int not null,
    author character varying not null,
    title character varying not null,
    year int not null 
);

CREATE INDEX books_category_id_idx ON books USING btree(category_id);


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
      --   insert into books 
      --   values (counter, (case when random() > 0.5 then 2 else 1 end), 'Author ' || counter, 'Title ' || counter, random_between(1900, 2022));

        counter := counter + 1;
    end loop;
    commit;
end;
$$;

commit;
