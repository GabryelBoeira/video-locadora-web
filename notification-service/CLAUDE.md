# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**notification-service** is a Spring Boot microservice that consumes Kafka events published by the main application (video-locadora) and processes notifications. Currently supports email and SMS delivery via integration with external services.

- Java 25 / Spring Boot 4.1.0 / Gradle
- Runs on port 8082
- Consumes events from Kafka topic: `videolocadora.domain-events`
- Uses WebFlux for async HTTP calls and JavaMail for email

## Build & Run

```bash
# Build
./gradlew build

# Run locally (requires Kafka at localhost:9092)
./gradlew bootRun

# Run tests
./gradlew test

# Run a single test
./gradlew test --tests com.gabryel.notificationservice.SomeTest

# Build Docker image
docker build -t notification-service .

# Run with docker-compose locally
docker-compose -f docker-compose-local.yml up
```

## Architecture

**Event Flow:**
1. `EventConsumer` listens to Kafka topic via `@KafkaListener`
2. Deserializes JSON payload to `EventNotification` DTO
3. Routes to `NotificationService` based on event type
4. `NotificationService` dispatches to `EmailService` or `SmsService`
5. Services make async calls via `WebClient` (Spring WebFlux)
6. Manual acknowledgment only after successful processing (error throws exception)

**Key Packages:**
- `event.consumer`: Kafka listener component
- `service`: Business logic (NotificationService, EmailService, SmsService)
- `dto`: Data transfer objects (EventNotification, PayloadEvent)
- `enums`: EventTypeEnum maps event types to handlers
- `config`: WebClientConfiguration, WebClientProperties for HTTP client setup

## Configuration

Environment variables (see `application.yaml`):
- `SPRING_KAFKA_BOOTSTRAP_SERVERS` — Kafka broker address (default: localhost:9092)
- `APP_KAFKA_DOMAIN_EVENTS_TOPIC` — Topic to consume (default: videolocadora.domain-events)
- `WEBCLIENT_*` — HTTP client timeouts and pool settings
- Email credentials via `spring.mail.*` properties

## Dependencies

- **spring-boot-starter-kafka** — Event consumption
- **spring-boot-starter-webflux** — Async HTTP via WebClient
- **spring-boot-starter-mail** — Email sending (JavaMailSender)
- **spring-boot-starter-actuator** — Health/info endpoints
- **spring-boot-starter-json** — Deserialization