# Start working

### Pre requisits 

1. installed docker
2. installed [siege](https://github.com/JoeDog/siege)
3. (OPTIONAL) installed JDK 17
4. (OPTIONAL) installed maven

## Steps to run with included app.jar

1. Open CLI and go to the project folder `./stress-test-stack`
2. You are ready to start:`docker-compose up` (TTL: 60 sec)
3. Run `initData.sh` to upload default data
2. Enjoy :)

## Results:

1. Test #01

`siege -b -c100 -v -t60s http://localhost:8080/api/v1/customers`

```shell
Lifting the server siege...
Transactions:		       23776 hits
Availability:		      100.00 %
Elapsed time:		       59.91 secs
Data transferred:	      951.83 MB
Response time:		        0.25 secs
Transaction rate:	      396.86 trans/sec
Throughput:		       15.89 MB/sec
Concurrency:		       99.74
Successful transactions:       23776
Failed transactions:	           0
Longest transaction:	        1.43
Shortest transaction:	        0.01
```

2. Test #02

`siege -b -c250 -v -t60s http://localhost:8080/api/v1/customers`

```shell
Lifting the server siege...
Transactions:		       23316 hits
Availability:		       99.16 %
Elapsed time:		       59.91 secs
Data transferred:	      484.96 MB
Response time:		        0.41 secs
Transaction rate:	      389.18 trans/sec
Throughput:		        8.09 MB/sec
Concurrency:		      160.21
Successful transactions:       23316
Failed transactions:	         197
Longest transaction:	       20.12
Shortest transaction:	        0.00
```

3. Test #03

`siege -b -c150 -v -t60s http://localhost:8080/api/v1/customers`

```shell
Lifting the server siege...
Transactions:		       27419 hits
Availability:		       99.60 %
Elapsed time:		       59.38 secs
Data transferred:	      787.38 MB
Response time:		        0.32 secs
Transaction rate:	      461.75 trans/sec
Throughput:		       13.26 MB/sec
Concurrency:		      147.76
Successful transactions:       27419
Failed transactions:	         109
Longest transaction:	        1.78
Shortest transaction:	        0.01
```

4. Test #04

`siege -b -c140 -v -t60s http://localhost:8080/api/v1/customers`

```shell
Lifting the server siege...
Transactions:		       27733 hits
Availability:		      100.00 %
Elapsed time:		       59.77 secs
Data transferred:	      631.32 MB
Response time:		        0.30 secs
Transaction rate:	      464.00 trans/sec
Throughput:		       10.56 MB/sec
Concurrency:		      139.63
Successful transactions:       27733
Failed transactions:	           0
Longest transaction:	       14.43
Shortest transaction:	        0.01
```

5. Test #05

`siege -b -c145 -v -t60s http://localhost:8080/api/v1/customers`

```shell
Lifting the server siege...
Transactions:		       27340 hits
Availability:		       99.99 %
Elapsed time:		       59.02 secs
Data transferred:	      547.32 MB
Response time:		        0.31 secs
Transaction rate:	      463.23 trans/sec
Throughput:		        9.27 MB/sec
Concurrency:		      143.47
Successful transactions:       27340
Failed transactions:	           2
Longest transaction:	       20.39
Shortest transaction:	        0.00
```
