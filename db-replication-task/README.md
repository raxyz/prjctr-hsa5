# DB Replication Task

### Pre requisits 

1. installed docker

### Prepare MySQL

1. Start docker-compose via command `docker-compose up`
2. When all containers started -- run init script and follow the instructions: `sh init.sh`

#### Insert task

Insert task can be tested via script: `sh insert_task.sh`

**Result:**

![insert_task](img/insert_task.png)


#### Drop column task

Drop column task can be tested via script: `sh drop_column_task.sh`

**Result:**

![drop_column_task](img/drop_column_task.png)
![insert_task](img/insert_task.png)


#### Turn off slave1 task

Turn off slave1 task can be tested via script: `sh turn_off_slave1.sh`

**Result:**

![turn_off_slave1](img/turn_off_slave1.png)