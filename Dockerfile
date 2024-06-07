# Stage 1: Build the application
# Use official openjdk-21 runtime
FROM maven:3.9.7-amazoncorretto-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml .
COPY src ./src
COPY .env .env

ARG APP_VERSION
# Package the application
RUN mvn clean package

# Stage 2: Create the runtime image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
ARG APP_VERSION
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/international-currency-converter-0.0.1-SNAPSHOT.jar /app/international-currency-converter.jar

# Command to run the jar file
CMD ["java", "-jar", "international-currency-converter.jar"]
