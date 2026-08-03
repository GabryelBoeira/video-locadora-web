package io.github.gabryel.videolocadora.model.dto.event;

import io.github.gabryel.videolocadora.model.enums.EventTypeEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventNotification(
        UUID eventId,
        EventTypeEnum eventType,
        LocalDateTime occurredAt,
        PayloadEvent payload
) {

    public static EventNotification of(EventTypeEnum eventType, PayloadEvent payload) {
        return new EventNotification(
                UUID.randomUUID(),
                eventType,
                LocalDateTime.now(),
                payload
        );
    }
}