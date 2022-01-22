#!/bin/bash

docker exec mysql-m mysql -uroot -p'password' -e "delete from task.users;"

echo "System inserts record every 0.5 sec."

for i in {1..11}
do
    docker exec mysql-m mysql -uroot -p'password' -e "insert into task.users (first_name, last_name, nickname) values ('name $i', 'surname $i', 'nick $i');"
    echo "User Name $i inserted [Hit CTRL+C to stop]"

    if (($i % 5 == 0)); then 
        echo "-------> result on slave 1 <-------"
        docker exec mysql-s1 mysql -uroot -p'password' -e "select * from task.users;"
        echo "-------> result on slave 2 <-------"
        docker exec mysql-s2 mysql -uroot -p'password' -e "select * from task.users;"
    fi

    if (($i == 6)); then 
        docker exec mysql-s1 mysql -uroot -p'password' -e "ALTER TABLE task.users DROP COLUMN last_name;"
    fi

    sleep .5

done
