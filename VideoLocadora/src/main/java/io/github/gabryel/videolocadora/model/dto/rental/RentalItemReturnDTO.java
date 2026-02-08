package io.github.gabryel.videolocadora.model.dto.rental;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record RentalItemReturnDTO(
        @NotNull
        Instant returnedAt
) {}