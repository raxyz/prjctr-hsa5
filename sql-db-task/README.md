### Pre requisits 

1. installed docker

### Start MySQL with default data

1. Run `docker-compose` config
2. When DB is ready -- run `init.sh` (The script removes all old data and creates new. 
You can specify the preferred amount of records to be created. Default is 10k)

### Connect to mysql

First of all, you should connect to the container `docker exec -it mysql bash`. 
Next step is connect to MySQL (default credentials) `mysql -uroot -p'password'`. 
Then you should select the necessary database using the command `use task;`


### Select test

Before doing each measurement the profiling was enabled in MySQL by command: `SET profiling = 1;`

After executing two selects system can provide profile details using the next commands:

```bash
SHOW PROFILES; 
SHOW PROFILE FOR QUERY 2;
``` 

Created database with 1m records in DB.

```sql
-- Select with specific birthday
select * from users where birthday = '2005-01-07';

-- Select with range birthday
select * from users where birthday between '2005-01-07' and '2010-01-07';
```

| Mode | Command | Total time (specific), sec  | Total time (range), sec |
|---|---|---|---|
| No index    | `-` | 0.49020875 | 0.81525875 |
| BTREE index | `CREATE INDEX db_intex_bt ON users(birthday) USING BTREE;` | 0.00092925 | 0.23056175 |
| HASH index <br><small>MySQL creates btree index :( </small> | `CREATE INDEX db_intex_hs ON users(birthday) USING HASH;` | 0.00090600 | 0.21549050 |

Command to view indexes: `show index from users from task;`
Drop index: `DROP INDEX db_intex_hs ON users;`

### Insert test

The system creates all required tables and inserts **10000** records into DB. 
Below you can find details regarding how much time each operation takes.

```shell
time sh init.sh
```

| Mode  | Result | Total time, sec |
|---|---|---|
| innodb_flush_log_at_trx_commit=0 | sh init.sh  0.11s user 0.08s system 1% cpu 15.136 total | 15.136 |
| innodb_flush_log_at_trx_commit=1 | sh init.sh  0.11s user 0.06s system 0% cpu | 27.317 |
| innodb_flush_log_at_trx_commit=2 | sh init.sh  0.12s user 0.06s system 1% cpu 15.572 total | 15.572 |