
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21
WORKDIR /app
COPY --from=build ./build/target/*.jar ./application.jar
EXPOSE 8080
ENTRYPOINT java -jar application.jar
