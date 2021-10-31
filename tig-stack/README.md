# Start working

### Pre requisits 

1. installed JDK 17
2. installed maven
3. installed docker
4. installed apache bench (om macos it's installed by default)

## Steps

0. Open CLI and go to the project folder. Filrst of all, navigate to greeting project `./tig-stack/greeting`
1. Build greeting project first: `mvn clean install -DskipTests` (or `maven clean install -DskipTests`)
2. Then you should build docker image `docker build -t greeting-app .`
3. Go back to the tig-stack folder (`cd ../`) and execute this command: `sudo chown $USER /var/run/docker.sock` 
(allow telegraf to work with docker.sock)
4. You are readt to start:`docker-compose up` (it is fine if greeting app failed to start first time. It has 'restart' 
flag and will start on 2-3 time)
5. When all services are up -- upload default configs (grafana dashboard and init data) by running `./run.sh`
6. open grafana: [http://localhost:3000/] => Dashboards => Manage => select "Custom Stats" dashboard => Switch 
interval to 15 mins for better view.
7. run ab test for details: `ab -n 5000 -c 500 -g out.txt http://localhost:8080/api/v1/users`
8. Enjoy :)

Result:
![stats1](img/stats1.jpeg)
