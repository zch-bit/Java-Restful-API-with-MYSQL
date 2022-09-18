FROM openjdk:17-jdk-alpine3.12
ARG JAR_FILE=build/libs/\*.jar

COPY ${JAR_FILE} app/
EXPOSE 8080
ENTRYPOINT ["java","-jar","app/ProjectDemo-0.1.0-SNAPSHOT.jar"]

