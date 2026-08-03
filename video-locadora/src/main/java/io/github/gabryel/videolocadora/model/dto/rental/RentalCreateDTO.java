package io.github.gabryel.videolocadora.model.dto.rental;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.List;

public record RentalCreateDTO(
        @NotNull
        Long customerId,

        @NotEmpty
        List<Long> movieIds,

        @NotNull
        Instant dueAt
) {}