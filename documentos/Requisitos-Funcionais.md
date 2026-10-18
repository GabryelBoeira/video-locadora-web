# Requisitos Funcionais — Video Locadora

*Documento gerado em 2026-10-18*

## Visão Geral e Objetivos

O **video-locadora-web** é um sistema de gestão de locadora de vídeo, com o objetivo de construir um ecossistema completo — do front-end ao back-end — organizado em microsserviços.

**Este não é um projeto comercial.** O objetivo principal é de estudo/portfólio: usar um domínio simples e conhecido (locadora de vídeo) como pretexto para aplicar, na prática, múltiplos padrões de arquitetura, design e engenharia de software em um sistema real, end-to-end. Os requisitos de negócio abaixo existem para dar contexto às decisões técnicas, não como meta de produto.

Estado atual (base para este documento): dois serviços back-end já implementados (`video-locadora`, o monólito de domínio, e `notification-service`, consumidor de eventos), com infraestrutura via Docker (MySQL, Keycloak, Kafka). O front-end ainda não existe — ver seção **Planejado (Futuro)**.

Objetivos de negócio do sistema:
- Permitir o cadastro e gestão de clientes da locadora.
- Manter um catálogo de filmes com controle de disponibilidade em estoque.
- Permitir que o cliente monte um carrinho de locação antes de confirmar o aluguel.
- Controlar o ciclo de vida de um aluguel (criação, devolução total ou parcial).
- Notificar o cliente sobre eventos relevantes (cadastro, aluguel, devolução, disponibilidade de filme).
- Garantir autenticação e autorização de acesso via Keycloak (OAuth2/JWT).

## Objetivos Técnicos e Padrões a Aplicar

Lista de padrões/conhecimentos já aplicados ou pretendidos para este projeto. Serve de checklist de aprendizado, não de requisito de negócio — ajuste livremente.

**Já aplicados:**
- Arquitetura de microsserviços com comunicação assíncrona via Kafka (event-driven).
- HATEOAS (nível 3 de maturidade REST).
- Autenticação/autorização centralizada via Keycloak (OAuth2/JWT).
- Paginação de listagens.
- Versionamento de schema de banco com Flyway.

**Planejados (candidatos a estudar/aplicar):**
- Transactional Outbox — garantir publicação confiável de eventos no Kafka junto com a transação do banco (hoje o evento é publicado sem essa garantia).
- Saga Pattern — coordenar o fluxo carrinho → aluguel → notificação como uma transação distribuída, com compensação em caso de falha.
- Circuit Breaker / Retry / Bulkhead (ex: Resilience4j) — tolerância a falha na comunicação entre serviços.
- API Gateway / Backend for Frontend (BFF) — ponto único de entrada quando o front-end e novos microsserviços existirem.
- CQRS — separar leitura e escrita no catálogo de filmes ou no histórico de aluguéis.
- Observabilidade distribuída — tracing (ex: OpenTelemetry), métricas e logs correlacionados entre `video-locadora` e `notification-service`.
- Idempotência no consumo de eventos Kafka — evitar notificações duplicadas em caso de reprocessamento.
- Testes de contrato (ex: Pact) entre os microsserviços.
- Testcontainers para testes de integração com MySQL/Kafka/Keycloak reais.
- Cache (ex: Redis) para o catálogo de filmes.

## Atores e Perfis de Usuário

| Ator | Descrição |
| --- | --- |
| Cliente | Pessoa cadastrada na locadora que navega pelo catálogo, monta carrinho e realiza aluguéis. Precisa de data de nascimento cadastrada (usada na classificação indicativa). |
| Atendente / Operador | Usuário interno que cadastra/atualiza clientes, gerencia o catálogo de filmes e registra devoluções. |
| Sistema (job agendado) | Processo automático que expira carrinhos abandonados e libera o estoque reservado. |
| Sistema de Notificação | Serviço que consome eventos de domínio e envia e-mail/SMS ao cliente. |

A autenticação e autorização desses atores é feita via Keycloak (OAuth2/JWT) — ver seção específica abaixo.

## Requisitos Funcionais Implementados — Gestão de Clientes

| ID | Requisito | Descrição | Ator |
| --- | --- | --- | --- |
| RF01 | Listar clientes | O sistema deve listar clientes cadastrados de forma paginada. | Atendente |
| RF02 | Consultar cliente por ID | O sistema deve permitir consultar os dados detalhados de um cliente pelo seu identificador. | Atendente |
| RF03 | Consultar cliente por CPF | O sistema deve permitir localizar um cliente pelo CPF. | Atendente |
| RF04 | Cadastrar cliente | O sistema deve permitir cadastrar um novo cliente, validando os dados de entrada. | Atendente |
| RF05 | Atualizar cliente | O sistema deve permitir atualizar os dados de um cliente existente. | Atendente |
| RF06 | Desativar cliente | O sistema deve permitir desativar (inativar) o cadastro de um cliente, em vez de excluí-lo definitivamente. | Atendente |

*Regra de negócio associada:* a data de nascimento do cliente é obrigatória para a realização de aluguéis, pois é usada no cálculo de idade para classificação indicativa (ver seção de Locação).

## Requisitos Funcionais Implementados — Catálogo de Filmes

| ID | Requisito | Descrição | Ator |
| --- | --- | --- | --- |
| RF07 | Listar filmes | O sistema deve listar os filmes do catálogo de forma paginada. | Cliente / Atendente |
| RF08 | Consultar filme por ID | O sistema deve permitir consultar os detalhes de um filme específico. | Cliente / Atendente |
| RF09 | Cadastrar filme | O sistema deve permitir cadastrar um novo filme no catálogo. | Atendente |
| RF10 | Remover filme | O sistema deve permitir remover um filme do catálogo. | Atendente |
| RF11 | Controlar disponibilidade | Cada filme deve ter um indicador de disponibilidade em estoque, atualizado automaticamente quando alugado, reservado no carrinho ou devolvido. | Sistema |

*Atributos de um filme:* título, preço, tipo de mídia (`DVD`, `BLU_RAY`, `VHS`), gênero e classificação indicativa (`FREE`, `PG_10`, `PG_13`, `AGE_16`, `AGE_18`), cada uma com idade mínima associada.

## Requisitos Funcionais Implementados — Carrinho de Locação

| ID | Requisito | Descrição | Ator |
| --- | --- | --- | --- |
| RF12 | Criar carrinho | O sistema deve permitir criar um carrinho de sessão, associado ou não a um cliente, com tempo de expiração configurável. | Cliente |
| RF13 | Consultar carrinho | O sistema deve permitir consultar o carrinho ativo pelo identificador de sessão. | Cliente |
| RF14 | Adicionar item ao carrinho | O sistema deve permitir adicionar um filme disponível ao carrinho, reservando-o temporariamente (ficando indisponível para outros clientes). | Cliente |
| RF15 | Remover item do carrinho | O sistema deve permitir remover um item do carrinho, liberando o filme reservado. | Cliente |
| RF16 | Finalizar carrinho (checkout) | O sistema deve permitir converter um carrinho ativo, com cliente associado e ao menos um item, em um aluguel. | Cliente |
| RF17 | Expirar carrinhos abandonados | O sistema deve expirar automaticamente, a cada 5 minutos, carrinhos cujo prazo de validade tenha vencido, liberando os filmes reservados. | Sistema |

*Regras de negócio associadas:*
- Não é possível adicionar um filme indisponível ou já presente no mesmo carrinho.
- O checkout exige carrinho com cliente vinculado e não vazio.
- Status possíveis do carrinho: `ACTIVE`, `EXPIRED`, `CONVERTED_TO_RENTAL`, `ABANDONED`.

## Requisitos Funcionais Implementados — Locação e Devolução

| ID | Requisito | Descrição | Ator |
| --- | --- | --- | --- |
| RF18 | Criar aluguel | O sistema deve permitir registrar um aluguel para um cliente, com um ou mais filmes e data de vencimento. | Atendente |
| RF19 | Listar aluguéis | O sistema deve listar os aluguéis de forma paginada. | Atendente |
| RF20 | Consultar aluguel por ID | O sistema deve permitir consultar os detalhes de um aluguel específico. | Atendente |
| RF21 | Registrar devolução total | O sistema deve permitir registrar a devolução de todos os itens de um aluguel, liberando os filmes no estoque. | Atendente |
| RF22 | Registrar devolução parcial | O sistema deve permitir registrar a devolução de um item específico do aluguel; quando todos os itens forem devolvidos, o aluguel é encerrado automaticamente. | Atendente |

*Regras de negócio associadas:*
- O cliente precisa ter data de nascimento cadastrada para alugar.
- Todos os filmes do pedido devem estar disponíveis em estoque.
- A idade do cliente deve atender à classificação indicativa mínima de cada filme solicitado; caso contrário, o aluguel é rejeitado.
- Um filme alugado fica indisponível até ser devolvido.
- Um item já devolvido não pode ser devolvido novamente.
- Só é possível devolver itens de um aluguel com status `OPEN`.
- Status possíveis do aluguel: `OPEN`, `RETURNED`, `CANCELED`.

## Requisitos Funcionais Implementados — Notificações

O `video-locadora` publica eventos de domínio em um tópico Kafka (`videolocadora.domain-events`); o `notification-service` consome esses eventos e dispara notificações por e-mail e SMS.

| ID | Requisito | Evento de domínio | Descrição |
| --- | --- | --- | --- |
| RF23 | Notificar cadastro de cliente | `CUSTOMER_CREATED` | O sistema deve notificar o cliente com boas-vindas ao criar o cadastro. |
| RF24 | Notificar exclusão de cliente | `CUSTOMER_DELETED` | O sistema deve notificar o cliente sobre o cancelamento do cadastro. |
| RF25 | Notificar aluguel criado | `RENTAL_CREATED` | O sistema deve notificar o cliente com os dados do aluguel e data de vencimento. |
| RF26 | Notificar devolução registrada | `RENTAL_RETURNED` | O sistema deve notificar o cliente de que a devolução foi registrada. |
| RF27 | Notificar aluguel vencido | `RENTAL_EXPIRED` | O sistema deve notificar o cliente de que o aluguel passou do prazo de devolução. |
| RF28 | Notificar disponibilidade de filme | `MOVIE_AVAILABLE_FOR_RENT` | O sistema deve notificar o cliente quando um filme de interesse ficar disponível para aluguel. |

*Observação:* no código atual, o processamento desses eventos registra log da notificação (via `NotificationService`); o envio efetivo por e-mail (JavaMail) e SMS (WebClient) faz parte da infraestrutura do serviço, mas a ligação completa evento → envio real precisa ser validada/concluída.

## Requisitos Funcionais Implementados — Autenticação e Autorização

| ID | Requisito | Descrição |
| --- | --- | --- |
| RF29 | Autenticar via Keycloak | O sistema deve autenticar usuários via Keycloak, usando o protocolo OAuth2/JWT. |
| RF30 | Proteger endpoints da API | Os endpoints da API REST do `video-locadora` devem exigir um token JWT válido emitido pelo Keycloak. |

Keycloak roda como serviço de infraestrutura no Docker Compose (porta 8081), compartilhado com os demais serviços via rede `locadora-net`.

## Requisitos Não Funcionais

| ID | Requisito | Descrição |
| --- | --- | --- |
| RNF01 | Arquitetura de microsserviços | Comunicação entre `video-locadora` e `notification-service` deve ser assíncrona, via Kafka, evitando acoplamento direto entre os serviços. |
| RNF02 | Persistência | Dados devem ser armazenados em MySQL 8, com versionamento de schema via Flyway. |
| RNF03 | Documentação de API | A API REST deve expor documentação via Swagger/OpenAPI. |
| RNF04 | Segurança | Toda comunicação autenticada deve usar tokens JWT emitidos pelo Keycloak; segredos de configuração (ex: senha do banco) devem vir de variáveis de ambiente. |
| RNF05 | Paginação | Listagens (clientes, filmes, aluguéis) devem ser paginadas para suportar grandes volumes de dados. |
| RNF06 | HATEOAS | Respostas da API devem incluir links de navegação (nível 3 de maturidade REST) para os fluxos de filmes, carrinho e aluguel. |
| RNF07 | Idioma | Termos de domínio de negócio em Português; código técnico em Inglês. |

## Requisitos Funcionais Planejados (Futuro)

Esta seção lista o que falta para completar a visão de ecossistema (front-end ao back-end, em microsserviços). **A preencher/priorizar junto ao time:**

| ID | Requisito | Descrição |
| --- | --- | --- |
| RF-F01 | Front-end web | Interface para o cliente navegar pelo catálogo, montar carrinho, acompanhar seus aluguéis e para o atendente gerenciar clientes/filmes. |
| RF-F02 | Envio real de notificações | Concluir a integração do `notification-service` com envio efetivo de e-mail e SMS (hoje há apenas log das notificações). |
| RF-F03 | Detecção de aluguel vencido | Implementar o job/mecanismo que efetivamente dispara o evento `RENTAL_EXPIRED` quando um aluguel passa da data de vencimento (não encontrado no código atual). |
| RF-F04 | Lista de interesse de filme | Implementar o fluxo que permite ao cliente marcar interesse em um filme indisponível, disparando `MOVIE_AVAILABLE_FOR_RENT` quando ele for devolvido/liberado. |
| RF-F05 | Pagamento online | Implementar cobrança via gateway de pagamento no fluxo de checkout do carrinho. |
| RF-F06 | Multa por atraso na devolução | Implementar cálculo e cobrança de multa quando a devolução ocorrer após a data de vencimento do aluguel. |
| RF-F07 | Limite de filmes por cliente/aluguel | Implementar regra de limite máximo de filmes que um cliente pode alugar simultaneamente. |
| RF-F08 | Avaliação de filmes | Permitir que clientes avaliem/comentem filmes já alugados. |
| RF-F09 | Relatórios gerenciais | Implementar relatórios e dashboards gerenciais (ex: filmes mais alugados, clientes mais ativos, faturamento). |

*Nota: itens marcados como "planejados" ainda não têm regra de negócio definida no código — precisam de levantamento específico antes da implementação.*

