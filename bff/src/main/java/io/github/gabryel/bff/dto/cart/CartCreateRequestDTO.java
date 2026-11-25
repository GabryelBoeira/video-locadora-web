package io.github.gabryel.bff.dto.cart;

import jakarta.validation.constraints.Min;

public record CartCreateRequestDTO(
        Long customerId,
        @Min(5) Integer expirationMinutes
) {
}
