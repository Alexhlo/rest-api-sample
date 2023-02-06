FROM openjdk:17-jdk-slim-buster
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} service.jar
ENTRYPOINT ["java", "-jar", "/service.jar"]