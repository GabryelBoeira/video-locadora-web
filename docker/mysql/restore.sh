#!/bin/bash
set -e

# Restaura o backup gerado por mysql/backup.sh no container mysql em execução.
# ATENÇÃO: sobrescreve os dados atuais dos bancos videolocadora e keycloak.

cd "$(dirname "$0")"

CONTAINER="mysql"
BACKUP_FILE="backup/locadora-backup.sql.gz"

if [ ! -f "$BACKUP_FILE" ]; then
    echo "Backup não encontrado: docker/mysql/$BACKUP_FILE"
    exit 1
fi

echo "==> Restaurando docker/mysql/$BACKUP_FILE no container $CONTAINER..."
gunzip -c "$BACKUP_FILE" | docker exec -i "$CONTAINER" sh -c 'exec mysql -uroot -p"$MYSQL_ROOT_PASSWORD" 2>/dev/null'

echo "==> Restore concluído! Reinicie as aplicações (ex.: ./build-and-docker.sh)."
