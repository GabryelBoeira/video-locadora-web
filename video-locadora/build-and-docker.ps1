$ErrorActionPreference = "Stop"

$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $ScriptDir

$InfraComposeFile = "../docker/docker-compose-infra.yml"
$AppComposeFile = "docker-compose-local.yml"

if ($args[0] -eq "full") {
    Write-Host "==> [FULL] Subindo infra..."
    docker compose -f $InfraComposeFile up -d

    Write-Host "==> [FULL] Buildando e subindo aplicação..."
    docker compose -f $AppComposeFile up -d --build
} else {
    Write-Host "==> [APP] Buildando e reiniciando apenas a aplicação..."
    docker compose -f $AppComposeFile up -d --build video-locadora
}

Write-Host "==> Pronto!"
Write-Host "App: http://localhost:8080/videolocadora"
Write-Host "Swagger: http://localhost:8080/videolocadora/swagger-ui/index.html"