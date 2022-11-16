FROM openjdk:8
ARG JAR_FILE="/target/springboot-blog-application.jar"
COPY ${JAR_FILE} app.jar
EXPOSE 8090
EXPOSE 5432
ENTRYPOINT ["java", "-jar", "/app.jar"]