# Stage 1: The Build Stage (Uses a full Maven/JDK environment)
# Uses the full Maven environment to compile the Java code and package it into a JAR.
FROM eclipse-temurin:17-jdk-jammy AS build 
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw install -DskipTests

# Run the full Maven package command. 
# This compiles the code and generates the final JAR in target/. 
# Note: The actual 'mvn clean install' with testing is handled by cloudbuild.yaml (Step 1).
RUN ./mvnw clean package -DskipTests

# Stage 2: The Final Runtime Stage (Uses a minimal JRE environment)
# We switch to a minimal OpenJDK JRE image for reduced size and faster startup.
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the built JAR file from the 'build' stage into the new, minimal 'app' folder.
# The JAR file name uses the artifact ID and version from your pom.xml.
COPY --from=build /app/target/*.jar /app/app.jar

# Cloud Run automatically sets the PORT environment variable (default 8080).
EXPOSE 8080

# Command to run the JAR file when the container starts.
ENTRYPOINT ["java","-jar","/app/app.jar"]