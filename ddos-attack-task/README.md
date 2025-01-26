hping3 -S --flood -V -p 80 nginx-safe
hping3 -c 15000 -d 120 -S -w 64 -p 80 --flood --rand-source nginx-safe

slowhttptest -c 100 -H -g -o slowhttp -i 10 -r 200 -t GET -u http://nginx-safe:8080 -x 24 -p 3
slowhttptest -c 100 -H -g -o slowhttp -i 10 -r 200 -t GET -u http://10.240.0.12:8080 -x 24 -p 10
