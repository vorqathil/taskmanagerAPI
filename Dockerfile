FROM maven:3.9.10-eclipse-temurin-21-noble AS build
WORKDIR /app
COPY src ./src
COPY pom.xml .
RUN mvn -f ./pom.xml clean package

FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/taskmanager-0.0.1-SNAPSHOT.jar ./task-manager-api-docker.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "task-manager-api-docker.jar"]