#!/usr/bin/env bash

# echo "Before creating cluster -- connect to redis-cli by command:\n\n" \
#     "docker exec -it redis-cli bash" \
#     "\n\nThen execute this one to create cluster:\n"

res=""
host=""

for ind in `seq 1 6`; do \
    host="$(docker inspect -f \
        '{{(index .NetworkSettings.Networks "redis-task_redis-cluster").IPAddress}}' \
        "redis_${ind}")";
    res="$res $host:6379";
done

command="redis-cli --cluster create $res --cluster-replicas 1 --cluster-yes"

docker exec -it redis-cli $command

echo "\nConnect to random host\n"
echo "redis-cli -c -h $host -p 6379"
