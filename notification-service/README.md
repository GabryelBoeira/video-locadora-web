# Responsabilidade

O notification-service será um microserviço separado que consome eventos Kafka publicados pela aplicação principal e executa notificações.

Inicialmente, eu faria ele apenas logar as notificações no console. Depois você pode evoluir para:
- envio de e-mail;
- Mailhog em ambiente local;
- WhatsApp/webhook;
- persistência de histórico;
- retry e dead-letter topic.

# Estrutura do microserviço de envio de mensagem

```
video-locadora-web/
├── video-locadora/
└── notification-service/
├── src/
│   └── main/
│       ├── java/
│       │   └── io/github/gabryel/notification/
│       │       ├── NotificationServiceApplication.java
│       │       ├── config/
│       │       │   └── KafkaConsumerConfig.java
│       │       ├── consumer/
│       │       │   └── DomainEventConsumer.java
│       │       ├── model/
│       │       │   └── DomainEvent.java
│       │       └── service/
│       │           └── NotificationProcessor.java
│       └── resources/
│           └── application.yaml
├── Dockerfile
└── pom.xml
```