## Percona 

Follow the instructions below to reproduce all problems.

### Pre-requisits

Connect to the docker container via command: `docker exec -it percona bash`
Then connect to DB: `mysql -uroot -p'password'`

Execute the script below for test data:

```sql
SET autocommit = 0;
DROP DATABASE IF EXISTS task;
CREATE DATABASE IF NOT EXISTS task;
USE task;

CREATE TABLE records (
  id int NOT NULL AUTO_INCREMENT,
  record VARCHAR(50) not null,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

insert into records(record)
values ("Record 1"),("Record 2");
COMMIT;
```

### Dirty reads

#### Read Uncommitted

| Transaction 1 | Transaction 2 |
|---|---|
| `SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED; START TRANSACTION;` | `SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED; START TRANSACTION;` |
| `select * from records where id = 1;` |  |
| | `update records set record = 'Record 1 UPDATED' where id = 1;` |
| `select * from records where id = 1;` |  |
| | `ROLLBACK;` |
| `select * from records where id = 1;` |  |

![percona-dirty-reads-read-uncommitted](imgs/percona-dirty-reads-read-uncommitted.png)


#### Read Committed

| Transaction 1 | Transaction 2 |
|---|---|
| `SET TRANSACTION ISOLATION LEVEL READ COMMITTED; START TRANSACTION;` | `SET TRANSACTION ISOLATION LEVEL READ COMMITTED; START TRANSACTION;` |
| `select * from records where id = 1;` |  |
| | `update records set record = 'Record 1 UPDATED' where id = 1;` |
| `select * from records where id = 1;` |  |
| | `ROLLBACK;` |
| `select * from records where id = 1;` |  |

![percona-dirty-reads-read-committed](imgs/percona-dirty-reads-read-committed.png)


#### Repeatable Read

| Transaction 1 | Transaction 2 |
|---|---|
| `SET TRANSACTION ISOLATION LEVEL REPEATABLE READ; START TRANSACTION;` | `SET TRANSACTION ISOLATION LEVEL REPEATABLE READ; START TRANSACTION;` |
| `select * from records where id = 1;` |  |
| | `update records set record = 'Record 1 UPDATED' where id = 1;` |
| `select * from records where id = 1;` |  |
| | `ROLLBACK;` |
| `select * from records where id = 1;` |  |

![percona-dirty-reads-repeatable-read](imgs/percona-dirty-reads-repeatable-read.png)


#### Serializable

| Transaction 1 | Transaction 2 |
|---|---|
| `SET TRANSACTION ISOLATION LEVEL SERIALIZABLE; START TRANSACTION;` | `SET TRANSACTION ISOLATION LEVEL SERIALIZABLE; START TRANSACTION;` |
| `select * from records where id = 1;` |  |
| | `update records set record = 'Record 1 UPDATED' where id = 1;` |

![percona-dirty-reads-serializable](imgs/percona-dirty-reads-serializable.png)


### Lost updates

#### Read Uncommitted

| Transaction 1 | Transaction 2 |
|---|---|
| `SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED; START TRANSACTION;` | `SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED; START TRANSACTION;` |
| `update records set record = 'Record 1 v1' where id = 1;` |  |
|  | `update records set record = 'Record 1 v2' where id = 1;` |

![percona-lost-updates-read-uncommitted](imgs/percona-lost-updates-read-uncommitted.png)


#### Read Committed

| Transaction 1 | Transaction 2 |
|---|---|
| `SET TRANSACTION ISOLATION LEVEL READ COMMITTED; START TRANSACTION;` | `SET TRANSACTION ISOLATION LEVEL READ COMMITTED; START TRANSACTION;` |
| `update records set record = 'Record 1 v1' where id = 1;` |  |
|  | `update records set record = 'Record 1 v2' where id = 1;` |

![percona-lost-updates-read-committed](imgs/percona-lost-updates-read-committed.png)


#### Repeatable Read

| Transaction 1 | Transaction 2 |
|---|---|
| `SET TRANSACTION ISOLATION LEVEL REPEATABLE READ; START TRANSACTION;` | `SET TRANSACTION ISOLATION LEVEL REPEATABLE READ; START TRANSACTION;` |
| `update records set record = 'Record 1 v1' where id = 1;` |  |
|  | `update records set record = 'Record 1 v2' where id = 1;` |

![percona-lost-updates-repeatable-read](imgs/percona-lost-updates-repeatable-read.png)


#### Serializable

| Transaction 1 | Transaction 2 |
|---|---|
| `SET TRANSACTION ISOLATION LEVEL SERIALIZABLE; START TRANSACTION;` | `SET TRANSACTION ISOLATION LEVEL SERIALIZABLE; START TRANSACTION;` |
| `update records set record = 'Record 1 v1' where id = 1;` |  |
|  | `update records set record = 'Record 1 v2' where id = 1;` |

![percona-lost-updates-serializable](imgs/percona-lost-updates-serializable.png)


### Non-repeatable reads

#### Read Uncommitted

| Transaction 1 | Transaction 2 |
|---|---|
| `SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED; START TRANSACTION;` | `SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED; START TRANSACTION;` |
| `select * from records where id = 1;` |  |
| | `update records set record = 'Record 1 UPDATED' where id = 1;` |
| | `COMMIT;` |
| `select * from records where id = 1;` |  |

![percona-non-repeatable-reads-read-uncommitted](imgs/percona-non-repeatable-reads-read-uncommitted.png)


#### Read Committed

| Transaction 1 | Transaction 2 |
|---|---|
| `SET TRANSACTION ISOLATION LEVEL READ COMMITTED; START TRANSACTION;` | `SET TRANSACTION ISOLATION LEVEL READ COMMITTED; START TRANSACTION;` |
| `select * from records where id = 1;` |  |
| | `update records set record = 'Record 1 UPDATED' where id = 1;` |
| | `COMMIT;` |
| `select * from records where id = 1;` |  |

![percona-non-repeatable-reads-read-committed](imgs/percona-non-repeatable-reads-read-committed.png)


#### Repeatable Read

| Transaction 1 | Transaction 2 |
|---|---|
| `SET TRANSACTION ISOLATION LEVEL REPEATABLE READ; START TRANSACTION;` | `SET TRANSACTION ISOLATION LEVEL REPEATABLE READ; START TRANSACTION;` |
| `select * from records where id = 1;` |  |
| | `update records set record = 'Record 1 UPDATED' where id = 1;` |
| | `COMMIT;` |
| `select * from records where id = 1;` |  |

![percona-non-repeatable-reads-repeatable-read](imgs/percona-non-repeatable-reads-repeatable-read.png)


#### Serializable

| Transaction 1 | Transaction 2 |
|---|---|
| `SET TRANSACTION ISOLATION LEVEL SERIALIZABLE; START TRANSACTION;` | `SET TRANSACTION ISOLATION LEVEL SERIALIZABLE; START TRANSACTION;` |
| `select * from records where id = 1;` |  |
| | `update records set record = 'Record 1 UPDATED' where id = 1;` |

![percona-non-repeatable-reads-serializable](imgs/percona-non-repeatable-reads-serializable.png)


### Phantom reads

#### Read Uncommitted

| Transaction 1 | Transaction 2 |
|---|---|
| `SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED; START TRANSACTION;` | `SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED; START TRANSACTION;` |
| `select count(*) from records;` |  |
| | `insert into records(record) values ('Record 3'), ('Record 4');` |
| | `COMMIT;` |
| `select count(*) from records;` |  |

![percona-phantom-reads-read-uncommitted](imgs/percona-phantom-reads-read-uncommitted.png)


#### Read Committed

| Transaction 1 | Transaction 2 |
|---|---|
| `SET TRANSACTION ISOLATION LEVEL READ COMMITTED; START TRANSACTION;` | `SET TRANSACTION ISOLATION LEVEL READ COMMITTED; START TRANSACTION;` |
| `select count(*) from records;` |  |
| | `insert into records(record) values ('Record 3'), ('Record 4');` |
| | `COMMIT;` |
| `select count(*) from records;` |  |

![percona-phantom-reads-read-committed](imgs/percona-phantom-reads-read-committed.png)


#### Repeatable Read

| Transaction 1 | Transaction 2 |
|---|---|
| `SET TRANSACTION ISOLATION LEVEL REPEATABLE READ; START TRANSACTION;` | `SET TRANSACTION ISOLATION LEVEL REPEATABLE READ; START TRANSACTION;` |
| `select count(*) from records;` |  |
| | `insert into records(record) values ('Record 3'), ('Record 4');` |
| | `COMMIT;` |
| `select count(*) from records;` |  |

![percona-phantom-reads-repeatable-read](imgs/percona-phantom-reads-repeatable-read.png)

**Attempt 2**

| Transaction 1 | Transaction 2 |
|---|---|
| `SET TRANSACTION ISOLATION LEVEL REPEATABLE READ; START TRANSACTION;` | `SET TRANSACTION ISOLATION LEVEL REPEATABLE READ; START TRANSACTION;` |
| `select * from records;` |  |
| | `insert into records(record) values ('Record 3'), ('Record 4');` |
| | `COMMIT;` |
| `select * from records;` |  |
| `update records set record = 'Record 3 NEW!!!' where id = 3;` |  |
| `select * from records;` |  |

![percona-phantom-reads-repeatable-read2](imgs/percona-phantom-reads-repeatable-read2.png)


#### Serializable

| Transaction 1 | Transaction 2 |
|---|---|
| `SET TRANSACTION ISOLATION LEVEL SERIALIZABLE; START TRANSACTION;` | `SET TRANSACTION ISOLATION LEVEL SERIALIZABLE; START TRANSACTION;` |
| `select count(*) from records;` |  |
| | `insert into records(record) values ('Record 3'), ('Record 4');` |

![percona-phantom-reads-serializable](imgs/percona-phantom-reads-serializable.png)


### Results

|  | Dirty reads | Lost updates | Non-repeatable reads | Phantom reads |
|---|---|---|---|---|
| **Read Uncommitted**  | possible | not possible | possible | possible |
| **Read Committed**    | not possible | not possible | possible | possible |
| **Repeatable Read**   | not possible | not possible | not possible | ~~not possible~~ possible |
| **Serializable**      | not possible | not possible | not possible | not possible |
