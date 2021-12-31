#!/bin/bash

amount=10000
query="$(<queries.sql)"

docker exec mysql mysql -uroot -p'password' -e "$query; call populate ($amount);"

# if you want to add more records -- you can run the command:
# docker exec mysql mysql -uroot -p'password' -e "use task; call populate (1000);"

