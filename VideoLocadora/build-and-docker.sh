#!/bin/bash
set -e

# Sempre roda a partir da pasta do script (VideoLocadora)
cd "$(dirname "$0")"

echo "==> Docker compose build (tudo dentro do Docker)"
docker-compose build --no-cache app

echo "==> Subir stack"
docker compose up -d

echo "==> Pronto!"
echo "App: http://localhost:8080/videolocadora"
