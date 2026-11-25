package io.github.gabryel.bff.dto.customer;

import jakarta.validation.constraints.NotBlank;

public record AddressRequestDTO(
        @NotBlank String street,
        Integer number,
        @NotBlank String city,
        @NotBlank String state,
        @NotBlank String country,
        @NotBlank String zipCode,
        @NotBlank String neighborhood,
        String complement
) {
}
