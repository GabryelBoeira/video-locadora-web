#!/bin/bash
set -e

# Sempre roda a partir da pasta do script
cd "$(dirname "$0")"

COMPOSE_FILE="docker-compose-local.yml"

if [ "$1" = "full" ]; then
    echo "==> [MODO FULL] Parando tudo, removendo volumes e limpando containers antigos..."
    docker compose -f "$COMPOSE_FILE" down --volumes --remove-orphans

    echo "==> [MODO FULL] Fazendo build completo (sem cache) de todos os serviços..."
    docker compose -f "$COMPOSE_FILE" build --no-cache

    echo "==> [MODO FULL] Subindo toda a stack (Kafka, Kafka UI e Notification Service)..."
    docker compose -f "$COMPOSE_FILE" up -d
else
    echo "==> [MODO BÁSICO] Atualizando e subindo apenas o notification-service..."
    docker compose -f "$COMPOSE_FILE" build notification-service
    docker compose -f "$COMPOSE_FILE" up -d notification-service
fi

echo "==> Operação concluída com sucesso! - notification-service"