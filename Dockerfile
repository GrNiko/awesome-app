FROM openjdk:21
#ARG JAR_FILE=target/*.jar
COPY target/awesome-app-0.1.0.jar awesome-app-0.1.0.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/awesome-app-0.1.0.jar"]