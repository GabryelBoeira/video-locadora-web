# Video Locadora Web

Sistema web para gestão de uma vídeo locadora, desenvolvido como uma refatoração de um projeto original criado no final de 2017.  
A proposta desta versão é servir como uma evolução técnica e arquitetural do sistema anterior, aplicando práticas mais atuais com **Java**, **Spring Boot** e um ecossistema moderno de desenvolvimento e deploy.

O projeto contempla uma aplicação monolítica, com API documentada, autenticação integrada, persistência em banco relacional e execução simplificada via Docker.

## Sobre o projeto

A aplicação foi pensada para simular os principais fluxos de uma locadora digital, oferecendo recursos para organização e administração do catálogo, clientes, locações e processos operacionais do sistema.

Além de atender às regras de negócio, o projeto também foi estruturado para demonstrar:

- evolução de código legado para uma base mais organizada;
- separação de responsabilidades;
- uso de padrões e boas práticas com Spring;
- documentação automática da API;
- integração com autenticação baseada em identidade;
- facilidade de execução local com containers.

## Funcionalidades do sistema

Entre os principais recursos da aplicação, destacam-se:

- gerenciamento do catálogo de filmes e mídias;
- cadastro e manutenção de clientes;
- controle de locações e devoluções;
- regras de negócio para validações e consistência dos dados;
- API REST documentada via Swagger/OpenAPI;
- autenticação e autorização com Keycloak;
- configuração para execução em ambiente Docker.

## Tecnologias utilizadas

- **Java 25**
- **Spring Boot 3**
- **Spring MVC**
- **Spring Data JPA**
- **Jakarta EE**
- **MySQL 8**
- **Keycloak**
- **Swagger / OpenAPI**
- **Docker e Docker Compose**
- **IntelliJ IDEA**
- **Arquitetura monolítica**

## Objetivo da refatoração

O principal objetivo deste projeto é transformar a versão antiga da aplicação em uma base mais moderna, sustentável e aderente às práticas atuais do mercado.

Essa refatoração busca:

- atualizar a stack tecnológica;
- melhorar a legibilidade e manutenção do código;
- organizar melhor as camadas da aplicação;
- preparar o sistema para evolução futura;
- documentar o processo de modernização do projeto.

## Como executar o projeto

### Pré-requisitos
- Docker
- Docker Compose

### Acessos disponíveis

- **Aplicação:** http://localhost:8080/videolocadora
- **Documentação da API:** http://localhost:8080/videolocadora/swagger-ui/index.html
- **Keycloak:** http://localhost:8081

## Credenciais padrão do Keycloak

As credenciais padrão utilizadas no ambiente local são:
- Usuário: admin 
- Senha: admin

## Observações

Este projeto foi desenvolvido como estudo prático de refatoração, modernização e organização de uma aplicação Java corporativa, servindo como base para evolução técnica e documentação de boas práticas.
