FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
VOLUME /tmp
COPY --from=build /app/target/app-users.jar /app-users.jar
ENTRYPOINT ["java", "-jar", "/app-users.jar"]
