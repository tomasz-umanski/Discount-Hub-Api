# Stage 1: Build the application
FROM maven:latest AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and the source code
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn -f pom.xml clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim AS run

# Set the working directory
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/discounthub-0.0.1-SNAPSHOT.jar discounthub-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 8818

# Run the application
CMD ["java", "-Xmx512m", "-jar", "discounthub-0.0.1-SNAPSHOT.jar"]
