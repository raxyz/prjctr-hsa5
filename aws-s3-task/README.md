# Start working

### Pre requisits 

- AWS account

## Create bucket 

1. Create S3 bucket with default configuration.
2. Enable access logging (seems you cannot use 
the same bucket to store logs)
![access-log](imgs/access-log.png)
3. Enable WORM strategy via Object Lock property
![object-lock](imgs/object-lock.png)

## Result 

Try to upload a file and then delete it

![result](imgs/delete-res.png)

Access logs:

![res-access-log](imgs/res-access-log.png)
