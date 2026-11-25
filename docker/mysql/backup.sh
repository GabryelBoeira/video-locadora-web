#!/bin/bash
set -e

# Gera um dump dos bancos videolocadora e keycloak a partir do container mysql.
# O arquivo gerado é restaurado automaticamente quando o volume do MySQL é criado do zero
# (ver mysql/init/02-restore-backup.sh) ou manualmente via mysql/restore.sh.

cd "$(dirname "$0")"

CONTAINER="mysql"
DATABASES="videolocadora keycloak"
BACKUP_FILE="backup/locadora-backup.sql.gz"

mkdir -p backup

echo "==> Gerando backup dos bancos: $DATABASES"
docker exec "$CONTAINER" sh -c "exec mysqldump -uroot -p\"\$MYSQL_ROOT_PASSWORD\" \
    --databases $DATABASES \
    --single-transaction --routines --triggers --events \
    --set-gtid-purged=OFF 2>/dev/null" | gzip > "$BACKUP_FILE.tmp"

mv "$BACKUP_FILE.tmp" "$BACKUP_FILE"

echo "==> Backup salvo em docker/mysql/$BACKUP_FILE ($(du -h "$BACKUP_FILE" | cut -f1))"
