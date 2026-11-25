package io.github.gabryel.bff.dto.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CartItemSummaryDTO(
        Long id,
        Long movieId,
        String movieTitle,
        Integer rentalDays,
        Double unitPrice,
        Double subtotal,
        Instant addedAt,
        Instant reservationExpiresAt,
        boolean reserved,
        boolean reservationExpired
) {
}
