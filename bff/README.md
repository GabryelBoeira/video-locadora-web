# bff

Backend For Frontend do sistema de locadora de vídeos. É o único componente pensado para ser chamado diretamente por um navegador/frontend: conduz o login/logout OAuth2 (Authorization Code) com o Keycloak, mantém a sessão do usuário no servidor (cookie HttpOnly), e expõe endpoints próprios em `/bff/**` que repassam o token de acesso para orquestrar chamadas ao `video-locadora`.

## Responsabilidade

- Autenticação/autorização: login via Keycloak, sessão em `HttpSession`, logout também no Keycloak (SSO).
- Proxy/orquestração: endpoints específicos por domínio (customer, movie, rental, cart) que chamam o `video-locadora` via `WebClient`, com o token de acesso do usuário logado.
- Nunca expõe tokens ao navegador — apenas um cookie de sessão (`JSESSIONID`, HttpOnly) e um cookie CSRF legível por JS (`XSRF-TOKEN`).

## Estrutura

```
bff/
├── src/
│   └── main/
│       ├── java/io/github/gabryel/bff/
│       │   ├── BffApplication.java
│       │   ├── configuration/
│       │   │   ├── security/SecurityConfig.java
│       │   │   ├── WebClientConfig.java
│       │   │   └── swagger/OpenAPIConfig.java
│       │   ├── client/              # CustomerClient, MovieClient, RentalClient, CartClient
│       │   ├── controller/          # endpoints /bff/**
│       │   │   └── advice/BffControllerAdvice.java
│       │   ├── service/
│       │   ├── dto/<domínio>/
│       │   └── exception/
│       └── resources/
│           └── application.yaml
├── Dockerfile
├── docker-compose-local.yml
└── pom.xml
```

## Build & Run

```bash
# Build
./mvnw clean package

# Rodar localmente (requer Keycloak + video-locadora no ar)
./mvnw spring-boot:run

# Testes
./mvnw test
```

## Fluxo de autenticação

1. Navegador acessa um endpoint protegido, ex. `GET /bff/customers`, sem sessão.
2. Spring Security redireciona para o Keycloak (`/oauth2/authorization/keycloak`).
3. Usuário loga no Keycloak; callback em `/login/oauth2/code/keycloak` cria a sessão no BFF.
4. Chamadas subsequentes ao backend (`video-locadora`) usam o access token do usuário, injetado automaticamente pelo `WebClient` configurado em `WebClientConfig`.
5. `GET /logout` invalida a sessão local e também desloga do Keycloak (`OidcClientInitiatedLogoutSuccessHandler`).

## Variáveis de ambiente

Ver `.env` e `application.yaml`. Principais:
- `KEYCLOAK_BFF_CLIENT_ID` / `KEYCLOAK_BFF_CLIENT_SECRET` — client confidencial `bff-client` no realm `video-locadora`.
- `KEYCLOAK_AUTHORIZATION_URI` — host **externo** do Keycloak (usado pelo navegador).
- `KEYCLOAK_TOKEN_URI` / `KEYCLOAK_CERTS_URI` / `KEYCLOAK_USERINFO_URI` — host **interno** do Keycloak quando o BFF roda dentro da rede Docker `locadora-net` (fora do Docker, podem apontar para `localhost:8081`).
- `VIDEOLOCADORA_BASE_URL` — URL do backend chamada pelo BFF.
- `APP_POST_LOGOUT_REDIRECT_URI`, `APP_CORS_ALLOWED_ORIGINS`.

## Limitação conhecida

A sessão é mantida em memória (`HttpSession` padrão do Tomcat embarcado), sem Spring Session/Redis. Não escala horizontalmente sem sticky sessions. Aceitável no estágio atual do projeto (ainda sem frontend real) — migrar para Spring Session + Redis é o próximo passo natural se surgir necessidade de múltiplas instâncias.
