# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**video-locadora-web** is a multi-project system for managing a video rental store. It consists of two Spring Boot applications and shared Docker infrastructure.

### Projects

| Directory | Description | Build Tool | Port |
|-----------|-------------|------------|------|
| `video-locadora/` | Main monolith — REST API, JPA, Keycloak auth, Kafka producer | Maven | 8080 |
| `notification-service/` | Microservice — Kafka consumer, email/SMS notifications | Gradle | 8082 |
| `docker/` | Infrastructure — Docker Compose for MySQL, Keycloak, Kafka, Kafka UI | — | — |

### Communication Flow

```
video-locadora  --[Kafka: videolocadora.domain-events]-->  notification-service
                                                              |-> Email (JavaMail)
                                                              |-> SMS (WebClient)
```

## Running the Full Stack

```bash
# 1. Start infrastructure (MySQL, Keycloak, Kafka, Kafka UI)
cd docker && docker-compose -f docker-compose-infra.yml up -d

# 2. Start the main application
cd video-locadora && ./mvnw spring-boot:run

# 3. Start notification service
cd notification-service && ./gradlew bootRun
```

### Infrastructure Services

| Service | URL | Credentials |
|---------|-----|-------------|
| MySQL | localhost:3306 | root / (env MYSQL_DB_PASSWORD) |
| Keycloak | http://localhost:8081 | admin / admin |
| Kafka | localhost:9092 | — |
| Kafka UI | http://localhost:8090 | — |

All infrastructure services share the `locadora-net` Docker network.

## Common Commands

```bash
# Build all
cd video-locadora && ./mvnw clean package
cd notification-service && ./gradlew build

# Run all tests
cd video-locadora && ./mvnw test
cd notification-service && ./gradlew test
```

## Tech Stack

- Java 25
- Spring Boot 4.x (4.0.7 main app, 4.1.0 notification-service)
- MySQL 8 with Flyway migrations
- Keycloak for OAuth2/JWT authentication
- Apache Kafka for event-driven communication
- Spring WebFlux (notification-service, async HTTP)
- Spring MVC (main app, REST API)
- Swagger/OpenAPI for API docs

## Conventions

- Language: Portuguese for business domain terms, English for technical code
- API documentation at http://localhost:8080/videolocadora/swagger-ui/index.html
- Environment variables for all external configuration (see each project's `application.yaml`)
