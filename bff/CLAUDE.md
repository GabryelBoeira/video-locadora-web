# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**bff** is the Backend For Frontend for the video rental store system. It is the only component meant to be called directly by a browser/frontend: it performs the OAuth2 Authorization Code login/logout flow against Keycloak, keeps the user's tokens server-side in an `HttpSession`, and exposes its own `/bff/**` endpoints that orchestrate calls to `video-locadora`, forwarding the user's access token.

- Java 25 / Spring Boot 4.0.7 / Maven
- Runs on port 8083
- Unlike `video-locadora` (stateless resource server), this app is session-based (`oauth2Login`, cookie session, CSRF enabled)
- Does not persist domain data itself — no JPA/Flyway/MySQL/Kafka dependency

## Build & Run

```bash
# Build
./mvnw clean package

# Run locally (requires: Keycloak + video-locadora running)
./mvnw spring-boot:run

# Run tests
./mvnw test

# Build Docker image
docker build -t bff .

# Run with docker-compose locally (inside locadora-net)
docker-compose -f docker-compose-local.yml up
```

## Architecture

**Layers:**
- `configuration/` — `SecurityConfig` (oauth2Login, logout, CSRF, CORS), `WebClientConfig` (OAuth2AuthorizedClientManager + token-propagating WebClient), `swagger/OpenAPIConfig`
- `controller/` — `/bff/**` REST controllers per domain (customer, movie, rental, cart)
- `controller/advice/` — `BffControllerAdvice`, maps upstream/session errors to HTTP responses
- `service/` — thin orchestration layer between controller and client
- `client/` — WebClient-based clients calling `video-locadora` (token is injected automatically by `ServletOAuth2AuthorizedClientExchangeFilterFunction`)
- `dto/<domínio>/` — BFF's own DTOs (independent Maven module, no shared classes with `video-locadora`)
- `exception/` — `BackendApiException`, `UpstreamUnauthorizedException`, `BackendUnavailableException`

**Key patterns:**
- Session-based OAuth2 client (not resource server): tokens never reach the browser, only an `HttpSession` cookie does.
- Access/refresh token propagation to `video-locadora` happens per-request via the authenticated user's `OAuth2AuthorizedClient`, not a service account.
- Known limitation: session is in-memory (no Spring Session/Redis) — does not scale horizontally without sticky sessions. Acceptable at this stage (no real frontend yet); revisit if multiple BFF instances are needed.

## Configuration

Environment variables (see `application.yaml` and `.env`):
- `KEYCLOAK_BFF_CLIENT_ID`, `KEYCLOAK_BFF_CLIENT_SECRET` — confidential OAuth2 client registered in the `video-locadora` realm (`bff-client`)
- `KEYCLOAK_AUTHORIZATION_URI` — Keycloak endpoint the **browser** is redirected to (must be the externally reachable host, e.g. `http://localhost:8081/...`)
- `KEYCLOAK_TOKEN_URI`, `KEYCLOAK_CERTS_URI`, `KEYCLOAK_USERINFO_URI` — Keycloak endpoints called **server-to-server** by the BFF process (can use the internal Docker host, e.g. `http://keycloak:8080/...`, when running inside `locadora-net`)
- `VIDEOLOCADORA_BASE_URL` — base URL used by the BFF to call the backend (internal host when in Docker)
- `APP_POST_LOGOUT_REDIRECT_URI` — where Keycloak redirects back to after logout
- `APP_CORS_ALLOWED_ORIGINS` — origins allowed to call the BFF with credentials (future frontend)

This dual internal/external host split mirrors the same problem already solved manually in `video-locadora`'s `JwtConfig` (see its `configuration/security/JwtConfig.java`). If the BFF runs outside Docker, all Keycloak URIs can simply point to `http://localhost:8081/...`.

## Dependencies

- **spring-boot-starter-web** — Servlet/MVC, required for `HttpSession`
- **spring-boot-starter-webflux** — only to bring `WebClient` as an outbound HTTP client on top of the servlet app
- **spring-boot-starter-security** + **oauth2-client** — Authorization Code login/logout flow with Keycloak
- **spring-boot-starter-validation**, **spring-boot-starter-actuator**
- **springdoc-openapi-starter-webmvc-ui** — Swagger/OpenAPI docs

## Testing

Tests use JUnit 5 + `spring-security-test` for simulating authenticated OAuth2 sessions.
