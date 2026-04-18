# Stage 1: Build using Maven
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copy pom.xml and download dependencies to leverage Docker layer caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build the application
COPY src ./src
RUN mvn package -DskipTests -B

# Stage 2: Minimal JRE for the runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the fat JAR from the build stage
COPY --from=build /app/target/simple-service-1.0-SNAPSHOT.jar ./app.jar

# Copy the configuration file
COPY config.yml .

# Expose the default Dropwizard ports (8080 for application, 8081 for admin)
EXPOSE 8080 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar", "server", "config.yml"]
