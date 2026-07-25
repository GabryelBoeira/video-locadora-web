package com.gabryel.notificationservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record SentEventNotification(
        UUID eventId,
        String eventType,
        LocalDateTime occurredAt,
        PayloadEvent payload
) {
}