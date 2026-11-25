package io.github.gabryel.bff.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartAddItemRequestDTO(
        @NotNull Long movieId,
        @NotNull @Min(1) Integer rentalDays
) {
}
