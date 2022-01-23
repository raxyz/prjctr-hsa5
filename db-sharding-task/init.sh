#!/bin/bash

docker exec postgres-b1 psql -U postgres -f "/tmp/pg_b1.sql"
docker exec postgres-b2 psql -U postgres -f "/tmp/pg_b2.sql"
docker exec postgres-b psql -U postgres -f "/tmp/pg_b.sql"

docker exec postgres-sa psql -U postgres -f "/tmp/pg_stand_alone.sql"

docker exec postgres-b psql -U postgres -d task -c "insert into books values (1, 1, 'test', 'test', 1990);"
docker exec postgres-b psql -U postgres -d task -c "insert into books values (1, 2, 'test', 'test', 1990);"

docker exec postgres-b psql -U postgres -d task -c "select * from books;"
docker exec postgres-b1 psql -U postgres -d task -c "select * from books_c_1;"
docker exec postgres-b2 psql -U postgres -d task -c "select * from books_c_2;"