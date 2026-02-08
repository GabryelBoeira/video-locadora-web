package io.github.gabryel.videolocadora.model.dto.address;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Objeto")
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