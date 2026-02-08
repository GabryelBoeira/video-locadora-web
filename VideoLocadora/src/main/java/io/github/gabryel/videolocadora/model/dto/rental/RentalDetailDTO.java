package io.github.gabryel.videolocadora.model.dto.rental;

import io.github.gabryel.videolocadora.model.enums.RentalStatus;

import java.time.Instant;
import java.util.List;

public record RentalDetailDTO(
        Long id,
        Long customerId,
        String customerName,
        RentalStatus status,
        Instant rentedAt,
        Instant dueAt,
        Instant returnedAt,
        Double totalPrice,
        List<RentalItemDetailDTO> items
) {
    public record RentalItemDetailDTO(
            Long id,
            Long movieId,
            String movieTitle,
            Double itemPrice,
            Instant returnedAt
    ) {}
}