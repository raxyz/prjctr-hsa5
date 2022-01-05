# Start working

### Pre requisits 

1. installed docker

## Test scenario

0. Start docker conainter using: `docker-compose up`
1. Open a page. For example: http://localhost:8080/1.html
2. After two refreshes system will cache the image (please note that your browser
 could cache it as well. So it's better to use page hard load)
3. You can remove the cache using PURGE request. 
For example: `curl -X PURGE http://localhost:8080/html/pic_trulli.jpg`
