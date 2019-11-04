FROM openjdk:latest
MAINTAINER Dmitry Pudov <dimapudov99@gmail.com>
COPY . .

ENV JAVA_OPTS="-Duser.timezone=GMT -Dfile.encoding=UTF-8 -Denvironment.type=production"
RUN javac -sourcepath src -d out -classpath out/server.jar src/com/dpudov/server/Main.java
CMD java $JAVA_OPTS -classpath out:out/server.jar com/dpudov/server/Main
EXPOSE 80