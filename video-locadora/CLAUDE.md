# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**video-locadora** is the main Spring Boot monolith for a video rental store management system. It exposes a REST API for managing customers, movies, carts, and rentals, with Keycloak-based authentication and Kafka event publishing.

- Java 25 / Spring Boot 4.0.7 / Maven
- Runs on port 8080 with context path `/videolocadora`
- MySQL 8 with Flyway migrations
- OAuth2 Resource Server (Keycloak JWT)
- Publishes domain events to Kafka

## Build & Run

```bash
# Build
./mvnw clean package

# Run locally (requires infrastructure: MySQL, Keycloak, Kafka)
./mvnw spring-boot:run

# Run tests
./mvnw test

# Run a single test
./mvnw test -Dtest=com.gabryel.videolocadora.SomeTest

# Build Docker image
docker build -t video-locadora .
```

## Architecture

**Layers:**
- `controller/` — REST controllers (CustomerController, MovieController, RentalController, CartController)
- `controller/api/` — API interfaces with Swagger annotations (CustomerApi, MovieApi, RentalApi, CartApi)
- `controller/advice/` — Global exception handling (ApplicationControllerAdvice)
- `service/` — Business logic organized by domain (customer/, movie/, rent/, cart/)
- `repository/` — Spring Data JPA repositories; `customer/` has JPA Specifications (CustomerSpecs)
- `model/entity/` — JPA entities (CustomerEntity, MovieEntity, RentalEntity, RentalItemEntity, AddressEntity, cart/)
- `model/dto/` — DTOs organized by domain (customer/, movie/, rental/, cart/, address/, event/, page/, hateoas/)
- `model/mapper/` — Manual mappers between entities and DTOs (customer/, movie/, rent/, cart/, address/, hateoas/)
- `model/enums/` — Business enums (MediaGenre, MediaType, ContentRating, RentalStatus, CartStatus, EventTypeEnum)
- `event.producer/` — EventPublisher for Kafka event publishing
- `configuration/` — WebConfig, Messages, security/ (SecurityConfig, JwtConfig), swagger/ (OpenAPIConfig)
- `exception/` — Custom exceptions (BusinessException, CustomerException)

**Key patterns:**
- HATEOAS-style responses with custom Link/Resource/ResourceCollection DTOs
- JPA Specifications for dynamic customer queries
- Flyway migrations in `resources/db.migration/` (V1 through V7)
- i18n via `messages.properties` (pt-BR default)

## Configuration

Environment variables (see `application.yaml`):
- `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` — MySQL connection
- `KEYCLOAK_URI`, `KEYCLOAK_CERTS_URI` — Keycloak JWT issuer and JWKS endpoints
- `SPRING_KAFKA_BOOTSTRAP_SERVERS` — Kafka broker
- `SPRING_KAFKA_TOPICS_DOMAIN_EVENTS` — Kafka topic for domain events
- `APP_CORS_ALLOWED_ORIGINS` — CORS origins
- `PROFILE` — Spring profiles (default: local,development)

## Dependencies

- **spring-boot-starter-web** — REST API
- **spring-boot-starter-data-jpa** — Persistence
- **spring-boot-starter-validation** — Bean validation
- **spring-boot-starter-security** + **oauth2-resource-server** — Keycloak JWT auth
- **spring-boot-starter-kafka** — Event publishing
- **spring-boot-starter-flyway** — Database migrations
- **spring-boot-starter-actuator** — Health/info endpoints
- **spring-boot-docker-compose** — Docker Compose integration
- **springdoc** — Swagger/OpenAPI documentation

## Testing

Tests use JUnit 5 with Mockito. Current test coverage focuses on customer domain:
- `CustomerServiceTest` — Service layer unit tests
- `CustomerMapperTest` — Mapper unit tests
- `ObjectUtils` — Test utility class
