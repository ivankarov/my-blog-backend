FROM gradle:7.6-jdk17-alpine AS builder 
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradle/ gradle/
RUN ./gradlew dependecies --no-deamon
COPY src/ src/
RUN ./gradlew build -x text --no-deamon 
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
