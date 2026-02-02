# --- Stage 1: Build the application ---
FROM eclipse-temurin:17-jdk-jammy as builder

WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Make the Maven wrapper executable
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build the JAR
RUN ./mvnw clean package -DskipTests

# --- Stage 2: Run the application ---
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Render uses the PORT environment variable to bind to the correct port.
# This EXPOSE is for documentation and local testing.
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
