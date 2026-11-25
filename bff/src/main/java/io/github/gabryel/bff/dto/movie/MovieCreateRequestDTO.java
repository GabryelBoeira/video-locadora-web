package io.github.gabryel.bff.dto.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MovieCreateRequestDTO(
        @NotBlank String title,
        @NotBlank String contentRating,
        @NotNull Double price,
        @NotNull Boolean available,
        @NotBlank String type,
        @NotBlank String genre
) {
}
