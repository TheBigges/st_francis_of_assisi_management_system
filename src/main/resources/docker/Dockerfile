# ==============================
# 1️⃣ Build Stage
# ==============================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy only pom first (for better Docker cache usage)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source
COPY src ./src

# Build application
RUN mvn clean install package -DskipTests


# ==============================
# 2️⃣ Runtime Stage
# ==============================
FROM eclipse-temurin:21-jre-alpine

# Install necessary packages
RUN apk add --no-cache tzdata

# Set timezone (optional)
ENV TZ=Asia/Manila

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /app

# Copy jar from build stage
COPY --from=builder /app/target/*.jar app.jar

# Expose app port
EXPOSE 8080

# JVM optimization for containers
ENV JAVA_OPTS="-Xms256m -Xmx750m \
               -XX:+UseG1GC \
               -XX:+UseContainerSupport\
               -Djava.security.egd=file:/dev/./urandom"

# Healthcheck (optional but recommended)
HEALTHCHECK --interval=30s --timeout=5s --start-period=40s --retries=3 \
  CMD wget -qO- http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]