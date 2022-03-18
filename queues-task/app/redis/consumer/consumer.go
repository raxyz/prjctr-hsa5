package main

import (
	"context"
	"fmt"
	"time"

	"github.com/go-redis/redis/v8"
)

var ctx = context.Background()

func main() {
	addr := "localhost:7002"
	rdb := redis.NewClient(&redis.Options{
		Addr:     addr,
		Password: "", // no password set
		DB:       0,  // use default DB
	})
	defer rdb.Close()

	fmt.Printf("Start redis consumer for %s\n", addr)
	start := time.Now()
	doWork(rdb)
	elapsed := time.Since(start)
	fmt.Printf("Time took %s\n", elapsed)
}

func doWork(c *redis.Client) {
	fmt.Println("Producer Started")

	for {
		_, err := c.LPop(ctx, "redis-queues").Result()
		if err != nil {
			fmt.Println(err)
			break
		}
		// fmt.Printf("Record '%s' received \n", val)
	}

	fmt.Println("Producer Finished")
}
