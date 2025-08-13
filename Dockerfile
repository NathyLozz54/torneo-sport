FROM amazoncorretto:17
ARG JAR_FILE=app-service/build/libs/app-service.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
