package com.gabryel.notificationservice.dto;

import com.gabryel.notificationservice.enums.EventTypeEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventNotification(
        UUID eventId,
        EventTypeEnum eventType,
        LocalDateTime occurredAt,
        PayloadEvent payload
) {
}