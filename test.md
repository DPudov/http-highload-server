# NGINX

`$ ab -n 20000 -c 500 localhost:80/httptest/jquery-1.9.1.js`
This is ApacheBench, Version 2.3 <$Revision: 1807734 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 2000 requests
Completed 4000 requests
Completed 6000 requests
Completed 8000 requests
Completed 10000 requests
Completed 12000 requests
Completed 14000 requests
Completed 16000 requests
Completed 18000 requests
Completed 20000 requests
Finished 20000 requests


Server Software:        nginx/1.16.1
Server Hostname:        localhost
Server Port:            80

Document Path:          /httptest/jquery-1.9.1.js
Document Length:        268381 bytes

Concurrency Level:      500
Time taken for tests:   3.468 seconds
Complete requests:      20000
Failed requests:        0
Total transferred:      5372640000 bytes
HTML transferred:       5367620000 bytes
Requests per second:    5766.82 [#/sec] (mean)
Time per request:       86.703 [ms] (mean)
Time per request:       0.173 [ms] (mean, across all concurrent requests)
Transfer rate:          1512844.51 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        2    5   2.1      4      20
Processing:    16   81   8.8     82     133
Waiting:        1    6   4.6      5      53
Total:         29   86   8.6     87     147

Percentage of the requests served within a certain time (ms)
  50%     87
  66%     88
  75%     89
  80%     90
  90%     92
  95%     95
  98%     99
  99%    105
 100%    147 (longest request)
 
# MY

$ ab -n 20000 -c 500 localhost:80/httptest/jquery-1.9.1.js
This is ApacheBench, Version 2.3 <$Revision: 1807734 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 2000 requests
Completed 4000 requests
Completed 6000 requests
Completed 8000 requests
Completed 10000 requests
Completed 12000 requests
Completed 14000 requests
Completed 16000 requests
Completed 18000 requests
Completed 20000 requests
Finished 20000 requests


Server Software:        pudov-highload-java-server
Server Hostname:        localhost
Server Port:            80

Document Path:          /httptest/jquery-1.9.1.js
Document Length:        268381 bytes

Concurrency Level:      500
Time taken for tests:   44.120 seconds
Complete requests:      20000
Failed requests:        0
Total transferred:      5370840000 bytes
HTML transferred:       5367620000 bytes
Requests per second:    453.31 [#/sec] (mean)
Time per request:       1102.988 [ms] (mean)
Time per request:       2.206 [ms] (mean, across all concurrent requests)
Transfer rate:          118880.79 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0   18 133.2      0    1035
Processing:   429 1072 133.5   1048    4422
Waiting:      375 1070 133.5   1046    4420
Total:        448 1090 205.0   1049    4436

Percentage of the requests served within a certain time (ms)
  50%   1049
  66%   1079
  75%   1091
  80%   1109
  90%   1142
  95%   1233
  98%   1350
  99%   2079
 100%   4436 (longest request)

 
