package io.github.gabryel.videolocadora.event.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.gabryel.videolocadora.model.dto.event.EventNotification;
import io.github.gabryel.videolocadora.model.dto.event.PayloadEvent;
import io.github.gabryel.videolocadora.model.enums.EventTypeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class EventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String domainEventsTopic;

    public EventPublisher(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper,
            @Value("${app.kafka.topics.domain-events}") String domainEventsTopic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.domainEventsTopic = domainEventsTopic;
    }

    public void publish(EventTypeEnum eventType, PayloadEvent payload) {
        EventNotification event = EventNotification.of(eventType, payload);

        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(domainEventsTopic, event.eventId().toString(), message);
        } catch (Exception ex) {
            throw new IllegalStateException("Erro ao serializar evento: " + eventType, ex);
        }
    }
}
