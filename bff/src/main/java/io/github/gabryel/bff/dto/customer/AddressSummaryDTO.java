package io.github.gabryel.bff.dto.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AddressSummaryDTO(
        Long id,
        String street,
        Integer number,
        String city,
        String state,
        String country,
        String zipCode,
        String neighborhood,
        String complement,
        boolean active,
        boolean primaryAddress
) {
}
