### Pre requisits 

1. installed docker


## Redis cluster

Redis cluster is created accoring to the [tutorial](https://redis.io/topics/cluster-tutorial)

**Steps to start cluster:**

0. Run `docker-compose up`
1. When all containers are up -- run `init.sh` script to build a cluster

## Eviction strategies

All the strategies are working as expected. Description can be found 
[here](https://docs.redis.com/latest/rs/concepts/memory-performance/eviction-policy/)

**Steps to test strategy:**

0. Select eviction strategy you wanna test in the `redis-cluster.conf` file
1. Start redis cluster
2. Use script `addData.sh` to generate random data
3. Use `getKeys.sh` script to get keys from the cluster.
4. Compare expectation to result based on strategy description. 


### Strategy volatile-lru

![redis_volatile-lru](imgs/redis_volatile-lru.png)

### Strategy allkeys-lru

![redis_allkeys-lru](imgs/redis_allkeys-lru.png)

### Strategy volatile-lfu

![redis_volatile-lfu](imgs/redis_volatile-lfu.png)

### Strategy allkeys-lfu

![redis_allkeys-lfu](imgs/redis_allkeys-lfu.png)

### Strategy volatile-random

![redis_volatile-random](imgs/redis_volatile-random.png)

### Strategy allkeys-random

![redis_allkeys-random](imgs/redis_allkeys-random.png)

### Strategy volatile-ttl

![redis_volatile-ttl](imgs/redis_volatile-ttl.png)

### Strategy noeviction 

![redis_noeviction](imgs/redis_noeviction.png)



## Cache stampede

This part is already done in [homework #4](https://github.com/raxyz/prjctr-hsa5/tree/main/stress-test-stack)