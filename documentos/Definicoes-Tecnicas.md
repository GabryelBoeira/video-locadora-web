# Definições Técnicas — Video Locadora Web

[← Voltar ao README](../README.md)

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
