# Use latest official Java 17 image
FROM eclipse-temurin:17-jdk-jammy

# Working directory
WORKDIR /app

# Copy jar
COPY target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run application
ENTRYPOINT ["java","-jar","app.jar"]
