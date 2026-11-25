#!/bin/bash
# Executado pelo MySQL apenas no primeiro start (volume vazio), depois do 01-create-databases.sql.
# Se existir um backup gerado por mysql/backup.sh, restaura os dados automaticamente.

BACKUP_FILE="/backup/locadora-backup.sql.gz"

if [ -f "$BACKUP_FILE" ]; then
    echo "==> Restaurando backup $BACKUP_FILE..."
    gunzip -c "$BACKUP_FILE" | mysql -uroot -p"$MYSQL_ROOT_PASSWORD"
    echo "==> Backup restaurado."
else
    echo "==> Nenhum backup encontrado em $BACKUP_FILE, iniciando com bancos vazios."
fi
