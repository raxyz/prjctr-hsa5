#!/bin/bash

echo "Remove old data"
docker exec postgres-b psql -U postgres -d task -c "delete from books_1;"
docker exec postgres-b psql -U postgres -d task -c "delete from books_2;"
docker exec postgres-sa psql -U postgres -d task -c "delete from books;"

time docker exec postgres-b psql -U postgres -d task -c "call populate(10000);"

docker exec postgres-b psql -U postgres -d task -c "select count(*) from books;"

time docker exec postgres-sa psql -U postgres -d task -c "call populate(10000);"

docker exec postgres-sa psql -U postgres -d task -c "select count(*) from books;"