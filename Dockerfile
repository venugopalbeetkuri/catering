# Stage 1: Build the application using Maven
FROM maven:3.9-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml to leverage Docker's layer caching for dependencies
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy the source code and build the application, skipping tests
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Create the final, lightweight image
FROM eclipse-temurin:21-jre-jammy

# Copy the built JAR from the 'build' stage
COPY --from=build /app/target/restaurant-backend-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application will run on (default for Spring Boot is 8080)
EXPOSE 8080

# The command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]