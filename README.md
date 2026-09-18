# Video Locadora Web

Sistema web para gestão de uma vídeo locadora, desenvolvido como uma refatoração de um projeto original criado no final de 2017.  
A proposta desta versão é servir como uma evolução técnica e arquitetural do sistema anterior, aplicando práticas mais atuais com **Java**, **Spring Boot** e um ecossistema moderno de desenvolvimento e deploy.

O projeto contempla uma aplicação monolítica, com API documentada, autenticação integrada, persistência em banco relacional e execução simplificada via Docker.

## Sobre o projeto

A aplicação foi pensada para simular os principais fluxos de uma locadora digital, oferecendo recursos para organização e administração do catálogo, clientes, locações e processos operacionais do sistema.

Além de atender às regras de negócio, o projeto também foi estruturado para demonstrar:

- modernização de código legado;
- organização em camadas;
- separação de responsabilidades;
- persistência com banco relacional;
- documentação automática da API;
- autenticação e autorização com Keycloak;
- execução local simplificada com Docker.
- Utilização de padrões e boas práticas com Spring.
- Comunicação entre microserviços utilizando eventos (Kafka).
- Utilizacao de Spring WebFlux para envio assíncrono de eventos(email/sms).
- Utilizacao do Restful com os três níveis de abstração.

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
- **Spring WebFlux**
- **Kafka**

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

## Decisões técnicas

Esta seção registra as principais decisões técnicas adotadas durante a evolução do projeto, incluindo escolhas de arquitetura, padrões de projeto, tecnologias, estratégias de testes e práticas de desenvolvimento.

O objetivo é manter um histórico claro das decisões tomadas, facilitando a manutenção, a evolução futura e o entendimento do racional técnico por trás da aplicação.

### Stack principal

- **Java 25** como versão principal da linguagem.
- **Spring Boot 3** como base da aplicação.
- **Jakarta EE** para APIs corporativas modernas.
- **Spring MVC** para construção da API REST.
- **Spring Data JPA** para persistência e acesso a dados.
- **MySQL 8** como banco de dados relacional.
- **Docker e Docker Compose** para execução local da aplicação e infraestrutura.

### Arquitetura

- A aplicação principal segue uma abordagem **monolítica**, mantendo os principais fluxos de negócio em uma única base.
- O projeto é organizado em camadas para separar responsabilidades entre:
    - controllers;
    - services;
    - repositories;
    - entidades;
    - DTOs;
    - configurações;
    - integrações externas.
- A separação por camadas facilita manutenção, testes e evolução gradual do sistema.

### API REST

- A API segue princípios REST para exposição dos recursos do sistema.
- Os endpoints representam recursos de negócio da locadora.
- A documentação da API é gerada com **Swagger/OpenAPI**.
- A estrutura busca evoluir para maior aderência aos níveis de maturidade REST, incluindo uso adequado de recursos, métodos HTTP e respostas padronizadas.

### Segurança

- A autenticação e autorização são integradas com **Keycloak**.
- O Keycloak é executado em ambiente local via Docker.
- A estratégia permite separar a gestão de identidade da lógica principal da aplicação.

### Persistência

- O acesso ao banco de dados é realizado com **Spring Data JPA**.
- O banco relacional utilizado é **MySQL 8**.
- As entidades representam os principais conceitos de negócio do domínio da locadora.
- A persistência deve priorizar clareza, consistência e integridade dos dados.

### Comunicação assíncrona

- O projeto utiliza **Kafka** para comunicação baseada em eventos.
- Eventos são usados para desacoplar fluxos que não precisam ser executados de forma síncrona.
- Essa decisão prepara o sistema para integração com serviços externos e evolução para cenários distribuídos.

### Serviço de notificações

- O projeto contempla um serviço separado para notificações.
- O envio de notificações pode ser tratado de forma assíncrona.
- **Spring WebFlux** é utilizado em fluxos reativos e não bloqueantes relacionados a notificações, como envio de e-mail ou SMS.

### Padrões e boas práticas

Durante a evolução do projeto, podem ser aplicados padrões como:

- **DTO** para entrada e saída de dados da API;
- **Service Layer** para centralizar regras de negócio;
- **Repository** para abstração do acesso a dados;
- **Factory** quando houver necessidade de criação controlada de objetos;
- **Strategy** para regras variáveis de negócio;
- **Builder** para construção de objetos complexos em testes ou cenários específicos;
- **Mapper** para conversão entre entidades e DTOs.

A aplicação deve priorizar:

- baixo acoplamento;
- alta coesão;
- legibilidade;
- responsabilidades bem definidas;
- código simples antes de abstrações complexas;
- evolução incremental.

### Testes

A estratégia de testes deve evoluir junto com o projeto, contemplando:

- testes unitários para regras de negócio;
- testes de integração para persistência, serviços e APIs;
- testes dos fluxos principais da aplicação;
- validação de contratos da API quando necessário;
- uso de mocks apenas quando agregarem clareza e isolamento.

Os testes devem servir como documentação viva do comportamento esperado do sistema.

### Docker e ambiente local

- A infraestrutura local é executada com **Docker Compose**.
- O ambiente inclui banco de dados, Keycloak, Kafka e ferramentas auxiliares.
- A aplicação possui Dockerfile próprio para build e execução em container.
- Essa decisão facilita padronização do ambiente e reduz problemas de configuração local.
