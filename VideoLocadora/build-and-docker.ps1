$ErrorActionPreference = "Stop"

# Sempre roda a partir da pasta onde o script está (VideoLocadora)
$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $ScriptDir

Write-Host "==> Docker build (Maven roda dentro do Dockerfile)"
docker build -t videolocadora:latest .

Write-Host "==> Subir com docker compose"
docker compose up --build -d

Write-Host "==> Pronto!"
Write-Host "Imagem: videolocadora:latest"
Write-Host "App: http://localhost:8080/videolocadora"