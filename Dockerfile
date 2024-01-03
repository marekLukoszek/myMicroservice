FROM openjdk:17-jdk-slim-buster
LABEL authors="mlukoszek"

EXPOSE 8081
COPY target/MyMicroService-*.jar /microservice.jar
ENTRYPOINT ["java","-jar","/microservice.jar"]
