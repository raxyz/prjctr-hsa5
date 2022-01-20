#!/bin/bash

masterip=$(docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mysql-m)
slave1ip=$(docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mysql-s1)
slave2ip=$(docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mysql-s2)

echo "Conteiner's IPs:\n master:$masterip\n slave1: $slave1ip\n slave2: $slave2ip"

echo "Create table in master note"
query="$(<./scripts/schema.sql)"
docker exec mysql-m mysql -uroot -p'password' -e "$query"

echo "Copy cnf file"
docker cp ./configs/mysql-m.cnf mysql-m:/etc/mysql/conf.d/custom-mysql.cnf

docker restart mysql-m

echo "Continue? press enter when node get up. It takes ~10 sec"
read tt

docker exec mysql-m mysql -uroot -p'password' -e "CREATE USER slave@$slave1ip IDENTIFIED BY 'password';GRANT REPLICATION SLAVE ON *.* TO slave@$slave1ip;FLUSH PRIVILEGES;"
docker exec mysql-m mysql -uroot -p'password' -e "CREATE USER slave@$slave2ip IDENTIFIED BY 'password';GRANT REPLICATION SLAVE ON *.* TO slave@$slave2ip;FLUSH PRIVILEGES;"

docker exec mysql-m mysql -uroot -p'password' -e "USE task; FLUSH TABLES WITH READ LOCK;SHOW MASTER STATUS;"

echo -n "\n\nEnter the value of FileName: "
read masterFileName

echo "The value is : $masterFileName"

echo -n "\n\nEnter the value of Position (it next to file mysql-bin.000001): "
read masterPosition

echo "The value is : $masterPosition"

docker exec mysql-m mysqldump -uroot -p'password' task  > ./scripts/task.sql
docker exec mysql-m mysql -uroot -p'password' -e "UNLOCK TABLES;"

echo "Update data to Slave 1"
dumbTask="$(<./scripts/task.sql)"
docker exec mysql-s1 mysql -uroot -p'password' -e "DROP DATABASE IF EXISTS task; CREATE DATABASE task;USE task; $dumbTask"

docker cp ./configs/mysql-s1.cnf mysql-s1:/etc/mysql/conf.d/custom-mysql.cnf

docker restart mysql-s1

echo "Continue? press enter when node get up. It takes ~10 sec"
read tt

echo "Start slave1"

docker exec mysql-s1 mysql -uroot -p'password' -e "CHANGE MASTER TO MASTER_HOST='$masterip', MASTER_USER='slave', MASTER_PASSWORD='password', MASTER_LOG_FILE = '$masterFileName', MASTER_LOG_POS = $masterPosition, GET_MASTER_PUBLIC_KEY = 1; START SLAVE;"

echo "Update data to Slave 2"
docker exec mysql-s2 mysql -uroot -p'password' -e "DROP DATABASE IF EXISTS task; CREATE DATABASE task;USE task; $dumbTask"
docker cp ./configs/mysql-s2.cnf mysql-s2:/etc/mysql/conf.d/custom-mysql.cnf

docker restart mysql-s2

echo "Continue? press enter when node get up. It takes ~10 sec"
read tt

echo "Start slave1"

docker exec mysql-s2 mysql -uroot -p'password' -e "CHANGE MASTER TO MASTER_HOST='$masterip', MASTER_USER='slave', MASTER_PASSWORD='password', MASTER_LOG_FILE = '$masterFileName', MASTER_LOG_POS = $masterPosition, GET_MASTER_PUBLIC_KEY = 1; START SLAVE;"

echo "Done\nLet's test:"

echo "-------> records from master <-------"
docker exec mysql-m mysql -uroot -p'password' -e "select * from task.names;"
echo "-------> records from slave 1 <-------"
docker exec mysql-s1 mysql -uroot -p'password' -e "select * from task.names;"
echo "-------> records from slave 2 <-------"
docker exec mysql-s2 mysql -uroot -p'password' -e "select * from task.names;"
echo "-------> insert a record to the master <-------"
docker exec mysql-m mysql -uroot -p'password' -e "insert into task.names values ('Name 2');"
echo "-------> result on slave 1 <-------"
docker exec mysql-s1 mysql -uroot -p'password' -e "select * from task.names;"
echo "-------> result on slave 2 <-------"
docker exec mysql-s2 mysql -uroot -p'password' -e "select * from task.names;"
