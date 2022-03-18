package main

import (
	"context"
	"fmt"
	"time"

	"github.com/go-redis/redis/v8"
)

const COUNTER = 1000

var ctx = context.Background()

func main() {
	addr := "localhost:7002"
	rdb := redis.NewClient(&redis.Options{
		Addr:     addr,
		Password: "", // no password set
		DB:       0,  // use default DB
	})
	defer rdb.Close()

	fmt.Printf("Start redis producer for %d records. Addr: %s\n", COUNTER, addr)
	start := time.Now()
	doWork(rdb)
	elapsed := time.Since(start)
	fmt.Printf("Time took %s\n", elapsed)
}

func doWork(c *redis.Client) {
	fmt.Println("Producer Started")

	for i := 0; i < COUNTER; i++ {
		err := c.RPush(ctx, "redis-queues", fmt.Sprintf("value #%d", i), 0).Err()
		if err != nil {
			fmt.Println(err)
		}
		// fmt.Printf("Record #%d added\n", i)
	}

	fmt.Println("Producer Finished")
}
