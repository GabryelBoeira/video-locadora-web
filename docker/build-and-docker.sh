#!/bin/bash
set -e

cd "$(dirname "$0")"

COMPOSE_FILE="docker-compose-full.yml"
APP_SERVICES="video-locadora bff notification-service"

if [ "$1" = "full" ]; then
    echo "==> [FULL] Buildando e subindo infra + aplicações..."
    docker compose -f "$COMPOSE_FILE" up -d --build
elif [ -n "$1" ]; then
    echo "==> [APP] Buildando e reiniciando: $*"
    docker compose -f "$COMPOSE_FILE" up -d --build "$@"
else
    echo "==> [APP] Buildando e reiniciando apenas as aplicações..."
    docker compose -f "$COMPOSE_FILE" up -d --build $APP_SERVICES
fi

echo "==> Pronto!"
echo "App: http://localhost:8080/videolocadora"
echo "Swagger: http://localhost:8080/videolocadora/swagger-ui/index.html"
echo "BFF: http://localhost:8083"
echo "Keycloak: http://localhost:8081"
echo "Kafka UI: http://localhost:8090"
