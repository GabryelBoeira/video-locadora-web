-- Script de inicialização executado pelo MySQL no primeiro start do container.
CREATE DATABASE IF NOT EXISTS videolocadora CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Cria os schemas necessários para a aplicação e para o Keycloak.
CREATE DATABASE IF NOT EXISTS keycloak CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;