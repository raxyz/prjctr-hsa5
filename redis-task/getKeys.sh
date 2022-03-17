#!/usr/bin/env bash

echo "Get keys"

for ind in 1 2 3; do
    echo "Keys for redis_$ind:"
    docker exec -it redis_$ind redis-cli KEYS \*
done