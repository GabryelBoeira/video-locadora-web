package com.gabryel.notificationservice.event.consumer;


import com.gabryel.notificationservice.dto.EventNotification;
import com.gabryel.notificationservice.service.NotificationService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class EventConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;

    public EventConsumer(
            ObjectMapper objectMapper,
            NotificationService notificationService
    ) {
        this.objectMapper = objectMapper;
        this.notificationService = notificationService;
    }

    @KafkaListener(
            topics = "${app.kafka.topics.domain-events}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        try {
            EventNotification event = objectMapper.readValue(record.value(), EventNotification.class);
            notificationService.process(event);
            acknowledgment.acknowledge();
        } catch (Exception ex) {
            throw new IllegalStateException("Erro ao processar evento Kafka: " + record.value(), ex);
        }
    }
}
