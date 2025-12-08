FROM gradle:7.6-jdk17-alpine AS builder
WORKDIR /app
COPY . .
RUN ./gradlew build -x test
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]