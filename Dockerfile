FROM openjdk:21-jdk
WORKDIR /app
COPY "target/ulk-aniamlz-1.0.0.jar" /app/ulk-aniamlz-1.0.0.jar
ENTRYPOINT ["java", "-jar", "/app/ulk-aniamlz-1.0.0.jar"]