## Passo a passo para construir e executar seu projeto Spring Boot com Docker Compose

Este guia detalha os comandos para compilar, construir a imagem Docker e executar seu projeto Spring Boot utilizando Docker Compose.

### 1. Construir a imagem Docker

```bash
  docker build -t videolocadora .
```

> O '.' Indica que o Dockerfile está no diretório atual. Certifique-se de estar no diretório raiz do seu projeto, onde o Dockerfile está localizado.

### 2. Executar o projeto Spring Boot com Docker Compose

```bash
  docker-compose up -d
```

> Certifique-se de ter o Docker Compose instalado e configurado corretamente no seu sistema.


### 3 Verificar Logs

```bash
  docker-compose logs -f videolocadora
```

### 4. Parar o projeto Spring Boot com Docker Compose

```bash
  docker-compose down
```

### Observações

- Certifique-se de que o Docker e o Docker Compose estão instalados e configurados corretamente em sua máquina.
- O arquivo docker-compose.yml deve estar no mesmo diretório que o Dockerfile.
- O nome da imagem (rest-react-kotlin-server) usado no comando docker build deve corresponder ao nome do serviço definido no arquivo docker-compose.yml.
- Se você tiver alguma dúvida sobre os comandos ou sobre o processo de criação de imagens Docker, consulte a documentação oficial do Docker e do Docker Compose.
- Apos o docker-compose up -d, acesse: http://localhost:8080/videolocadora/swagger-ui/index.html