FROM maven:3.9.9-eclipse-temurin-22 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:22-jdk AS runner

WORKDIR /app

COPY --from=builder ./app/target/client-service-0.0.1-SNAPSHOT.jar ./app.jar

# Specify port to run the app on.
EXPOSE 4000

# Run the jar file.
ENTRYPOINT ["java", "-jar", "app.jar"]
