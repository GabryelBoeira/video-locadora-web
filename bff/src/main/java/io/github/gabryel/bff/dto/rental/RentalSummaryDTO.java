package io.github.gabryel.bff.dto.rental;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RentalSummaryDTO(
        Long id,
        Long customerId,
        String customerName,
        String status,
        Instant rentedAt,
        Instant dueAt,
        Instant returnedAt,
        Double totalPrice,
        List<RentalItemSummaryDTO> items
) {
}
