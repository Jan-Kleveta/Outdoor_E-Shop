# Stage 1: Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app/

# Copy Maven configuration and source code
COPY pom.xml .
COPY src /app/src

# Run Maven to compile and package the application
RUN mvn clean compile
RUN mvn package

# Stage 2: Runtime stage
FROM eclipse-temurin:21-jre-alpine

# Set environment variable for Spring profiles
ENV SPRING_PROFILES_ACTIVE=docker

# Copy the JAR file from the build stage to the runtime stage
COPY --from=build /app/target/*.jar /stock_service_app.jar

# Set the command to run the application
ENTRYPOINT ["java", "-jar", "/stock_service_app.jar"]
