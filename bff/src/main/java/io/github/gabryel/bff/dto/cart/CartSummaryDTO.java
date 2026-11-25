package io.github.gabryel.bff.dto.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CartSummaryDTO(
        Long id,
        String sessionId,
        Long customerId,
        String customerName,
        Instant createdAt,
        Instant expiresAt,
        Double totalPrice,
        String status,
        List<CartItemSummaryDTO> items,
        Integer itemCount,
        boolean expired
) {
}
