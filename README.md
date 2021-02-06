# demo-spring-boot-sql
This is simple demo application using spring boot sql

To run application
1. Run ./gradlew assemble to build jar file
2. Create docker image by running following command docker 
   docker build --build-arg JAR_FILE=build/libs/\*.jar -t sql-demo .
   Image will be named sql-demo, and can be checked using
   docker image ls
3. Create container by running docker compose specified by docker-compose.yaml
   docker-compose -f docker-compose.yml up
   There will be two instances created inside container
   a. db, database using mysql
   b. sql-demo, run on openjdk:8-jdk-alpine
   
   Sql demo is accessing database on container network with identifier db on port 3036 (see sql demo worspace resources/application.properties)
   
 Testing application
 1. To add entry to database use curl localhost:8080/demo/add -d name=John -d email=John@gmail.com
 2. To get all entry to database use curl localhost:8080/demo/all
