package io.github.gabryel.bff.dto.rental;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record RentalReturnRequestDTO(
        @NotNull Instant returnedAt
) {
}
