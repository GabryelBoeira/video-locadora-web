$ErrorActionPreference = "Stop"

# Sempre roda a partir da pasta do script (VideoLocadora)
$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $ScriptDir

Write-Host "==> Docker compose build (tudo dentro do Docker)"
docker compose build --no-cache app

Write-Host "==> Subir stack"
docker compose up -d

Write-Host "==> Pronto!"
Write-Host "App: http://localhost:8080/videolocadora"