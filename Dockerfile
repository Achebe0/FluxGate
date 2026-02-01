# --- Stage 1: Build the application ---
FROM eclipse-temurin:17-jdk-jammy as builder

WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build the JAR
RUN ./mvnw package -DskipTests

# --- Stage 2: Run the application ---
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port (Railway sets PORT env var, but 8080 is a good default)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
