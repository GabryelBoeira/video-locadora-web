package com.gabryel.notificationservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PayloadEvent(
        Long rentalId,
        String customerName,
        String customerEmail,
        String cellPhone,
        String phone,
        BigDecimal totalPrice,
        LocalDateTime rentalDueAt,
        List<String> movieNames
) {
}
