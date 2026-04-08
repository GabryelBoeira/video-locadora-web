# Video Locadora Web

Projeto de modernização de uma aplicação web de locadora de vídeos originalmente desenvolvida no final de 2017.

A aplicação surgiu em uma versão anterior baseada em uma stack legada, e foi posteriormente **refatorada e atualizada** para uma arquitetura mais moderna, com foco em manutenção, organização, compatibilidade e evolução tecnológica.

Repositório original do projeto legado:  
https://github.com/GabryelBoeira/VideoLocadoraJSF

## Visão Geral

Este projeto tem como objetivo demonstrar a evolução de uma aplicação Java web ao longo do tempo, saindo de uma implementação antiga para uma versão atualizada com tecnologias mais recentes do ecossistema Java.

A proposta principal é evidenciar:

- atualização da base tecnológica;
- melhoria na organização do código;
- adoção de práticas mais modernas de desenvolvimento;
- integração com ferramentas atuais de documentação e containerização;
- facilidade de execução local e em ambiente isolado via Docker.

## Histórico da Evolução

### Versão original
A aplicação foi criada em 2017 com uma abordagem mais antiga de desenvolvimento web em Java, utilizando a stack disponível na época.

### Versão atualizada
O projeto foi reestruturado com foco em modernização, adotando versões recentes do Java e do Spring, além de suporte a banco de dados moderno, documentação de API e execução via containers.

## Stack Tecnológica

- **Java:** 25
- **Spring Boot**
- **Spring MVC**
- **Spring Data JPA**
- **Jakarta EE**
- **Banco de dados:** MySQL 8
- **Documentação da API:** Swagger / OpenAPI
- **Contêineres:** Docker e Docker Compose
- **IDE recomendada:** IntelliJ IDEA

## Arquitetura

A aplicação segue uma estrutura orientada a camadas, separando responsabilidades entre:

- camada de apresentação;
- camada de serviço;
- camada de persistência;
- entidades de domínio;
- configuração e infraestrutura.
- camada de testes.
- camada de client para servicos externos.

Esse modelo facilita evolução do sistema, testes e manutenção.

## Objetivo Técnico

Este repositório serve como base para demonstrar a evolução de um sistema Java web antigo para uma implementação moderna, com melhor alinhamento às práticas atuais do ecossistema Spring e Jakarta.

## Observações

- O projeto foi atualizado a partir de uma versão anterior.
- O foco não é apenas funcionalidade, mas também a evolução da base tecnológica.
- A documentação e a execução foram pensadas para facilitar o uso em ambientes locais e conteinerizados.

## Execução do Projeto

### Pré-requisitos

- Java 25
- Docker
- Docker Compose

### Executar o projeto via Script
- Linux
```bash
  chmod +x build-and-docker.sh
  ./build-and-docker.sh
```

### Executar o projeto via Script

- Windows (iniciar o docker antes de executar o comando)
```bash
    docker compose up -d --build
```

### Acessar a documentação da API

Após a aplicação subir, acesse:
- http://localhost:8080/videolocadora/swagger-ui/index.html
- http://localhost:8080/videolocadora/v3/api-docs

