#WEB SERVER

## Response codes

* 200 - OK

* 404 - NOT FOUND

* 403 - PROHIBITED

* 405 - DEFAULT

## Run locally

```
sudo /usr/lib/jvm/java-11-openjdk-amd64/bin/java -javaagent:/home/dpudov/Загрузки/ideaIU-2018.3.4/idea-IU-183.5429.30/lib/idea_rt.jar=40077:/home/dpudov/Загрузки/ideaIU-2018.3.4/idea-IU-183.5429.30/bin -Dfile.encoding=UTF-8 -classpath /media/dpudov/media/workspace/Technopark/third_semester/highload/http-highload-server/out/production/http-highload-server com.dpudov.server.Main
```

## Run docker

```
docker run -p 80:80 -v ~/etc/httpd.conf:/etc/httpd.conf:ro -v ~/var/www/html:/var/www/html:ro --name dpudov-httpd -t dpudov-httpd
```
