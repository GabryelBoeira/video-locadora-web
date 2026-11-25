package io.github.gabryel.bff.dto.rental;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RentalItemSummaryDTO(
        Long id,
        Long movieId,
        String movieTitle,
        Double itemPrice,
        Instant returnedAt
) {
}
