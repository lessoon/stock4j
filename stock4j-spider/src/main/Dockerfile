FROM java:8
ARG JAR_FILE=target/spring-boot-docker-0.0.1-SNAPSHOT.jar
VOLUME /tmp/tomcat
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]