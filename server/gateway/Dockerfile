FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app/
COPY pom.xml .
COPY src /app/src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
ENV SPRING_PROFILES_ACTIVE=docker
COPY --from=build /app/target/*.jar /api_gateway_app.jar
ENTRYPOINT ["java", "-jar", "/api_gateway_app.jar"]
