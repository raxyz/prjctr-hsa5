package main

import (
	"fmt"
	"log"
	"time"

	"github.com/beanstalkd/go-beanstalk"
)

const COUNTER = 1000

func main() {
	c, err := beanstalk.Dial("tcp", "127.0.0.1:11300")
	if err != nil {
		log.Fatalf("Connection error: %s", err.Error())
	}
	defer c.Close()

	fmt.Println("Start beanstalkd consumer")
	start := time.Now()
	doWork(c)
	elapsed := time.Since(start)
	fmt.Printf("Time took %s\n", elapsed)
}

func doWork(c *beanstalk.Conn) {
	fmt.Println("Producer Started")

	i := 1
	for {
		id, _, err := c.Reserve(5 * time.Second)
		if err != nil {
			fmt.Println(err)
			break
		}
		if i == COUNTER {
			break
		}
		c.Delete(id)
		i++
		// fmt.Printf("Record %d with body '%s' received \n", id, body)
	}

	fmt.Println("Producer Finished")
}
