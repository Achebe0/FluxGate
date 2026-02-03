# --- Stage 1: Build the frontend ---
FROM node:18 as frontend-builder
WORKDIR /app/FluxFront
COPY FluxFront/package.json FluxFront/package-lock.json ./
RUN npm install
COPY FluxFront/ ./
RUN npm run build

# --- Stage 2: Build the backend ---
FROM eclipse-temurin:17-jdk-jammy as backend-builder
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

# Copy the built frontend from the previous stage into the static resources folder
COPY --from=frontend-builder /app/FluxFront/dist ./src/main/resources/static

# Build the JAR
RUN ./mvnw clean package -DskipTests

# --- Stage 3: Run the application ---
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=backend-builder /app/target/*.jar app.jar

# Expose the port the app will run on
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
