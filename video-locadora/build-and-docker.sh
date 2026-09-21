#!/bin/bash
set -e

cd "$(dirname "$0")"

INFRA_COMPOSE_FILE="../docker/docker-compose-infra.yml"
APP_COMPOSE_FILE="docker-compose-local.yml"

if [ "$1" = "full" ]; then
    echo "==> [FULL] Subindo infra..."
    docker compose -f "$INFRA_COMPOSE_FILE" up -d

    echo "==> [FULL] Buildando e subindo aplicação..."
    docker compose -f "$APP_COMPOSE_FILE" up -d --build
else
    echo "==> [APP] Buildando e reiniciando apenas a aplicação..."
    docker compose -f "$APP_COMPOSE_FILE" up -d --build video-locadora
fi

echo "==> Pronto!"
echo "App: http://localhost:8080/videolocadora"
echo "Swagger: http://localhost:8080/videolocadora/swagger-ui/index.html"