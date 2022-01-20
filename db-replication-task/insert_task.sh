#!/bin/bash

docker exec mysql-m mysql -uroot -p'password' -e "delete from task.names;"

echo "Infinite loop started. System inserts record every 0.5 sec. [Hit CTRL+C to stop]"
i=0
for (( ; ; ))
do
    i=$((i + 1))
    docker exec mysql-m mysql -uroot -p'password' -e "insert into task.names values ('Name $i');"
    echo "Name $i inserted [Hit CTRL+C to stop]"
    sleep .5

    if (($i % 5 == 0)); then 
        echo "-------> result on slave 1 <-------"
        docker exec mysql-s1 mysql -uroot -p'password' -e "select GROUP_CONCAT(name SEPARATOR ', ') from task.names;"
        echo "-------> result on slave 2 <-------"
        docker exec mysql-s2 mysql -uroot -p'password' -e "select GROUP_CONCAT(name SEPARATOR ', ') from task.names;"
    fi

done
