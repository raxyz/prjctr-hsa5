package main

import (
	"fmt"
	"time"

	"github.com/beanstalkd/go-beanstalk"
)

const COUNTER = 1000

func main() {
	c, err := beanstalk.Dial("tcp", "127.0.0.1:11300")
	if err != nil {
		fmt.Printf("Connection error: %s\n", err.Error())
	}

	defer c.Close()

	fmt.Printf("Start beanstalkd producer for %d records\n", COUNTER)
	start := time.Now()
	doWork(c)
	elapsed := time.Since(start)
	fmt.Printf("Time took %s\n", elapsed)
}

func doWork(c *beanstalk.Conn) {
	fmt.Println("Producer Started")

	for i := 0; i < COUNTER; i++ {
		_, err := c.Put([]byte(fmt.Sprintf("message #%d", i)), 1, 0, 120*time.Second)
		if err != nil {
			fmt.Println(err)
		}
		// fmt.Printf("Record with ID: %d added\n", id)
	}

	fmt.Println("Producer Finished")
}
