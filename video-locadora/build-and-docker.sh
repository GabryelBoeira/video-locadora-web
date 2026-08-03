#!/bin/bash
set -e

# Sempre roda a partir da pasta do script
cd "$(dirname "$0")"

if [ "$1" = "full" ]; then
    echo "==> [MODO FULL] Parando tudo, removendo volumes e limpando containers antigos..."
    docker compose down --volumes --remove-orphans

    echo "==> [MODO FULL] Fazendo build completo (sem cache) de todos os serviços..."
    docker compose build --no-cache

    echo "==> [MODO FULL] Subindo toda a stack..."
    docker compose up -d
else
    echo "==> [MODO BÁSICO] Atualizando e subindo apenas o serviço 'video-locadora'..."
    docker compose build video-locadora
    docker compose up -d video-locadora
fi

echo "==> Pronto!"
echo "App: http://localhost:8080/videolocadora"
echo "Swagger: http://localhost:8080/videolocadora/swagger-ui/index.html"