package io.github.gabryel.videolocadora.dto.address;

import jakarta.validation.constraints.NotBlank;

public record AddressSaveDTO(
        @NotBlank
        String street,

        @NotBlank
        String city,

        @NotBlank
        String state,

        @NotBlank
        String country,

        @NotBlank
        String zipCode,

        @NotBlank
        String neighborhood,

        String complement,

        Integer number
        ) {
}