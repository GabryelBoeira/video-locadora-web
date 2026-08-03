#!/bin/bash
set -e

# Sempre roda a partir da pasta do script
cd "$(dirname "$0")"

if [ "$1" = "full" ]; then
    echo "==> [MODO FULL] Parando tudo, removendo volumes e limpando containers antigos..."
    docker compose down --volumes --remove-orphans

    echo "==> [MODO FULL] Fazendo build completo (sem cache) de todos os serviços..."
    docker compose build --no-cache

    echo "==> [MODO FULL] Subindo toda a stack (Kafka, Kafka UI e Notification Service)..."
    docker compose up -d
else
    echo "==> [MODO BÁSICO] Atualizando e subindo apenas o notification-service..."
    docker compose build notification-service
    docker compose up -d notification-service
fi

echo "==> Operação concluída com sucesso!"