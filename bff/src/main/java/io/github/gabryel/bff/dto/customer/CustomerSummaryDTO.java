package io.github.gabryel.bff.dto.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CustomerSummaryDTO(
        Long id,
        String name,
        String cpf,
        boolean delayDevolution,
        LocalDate birthDate,
        String email,
        String cellPhone,
        List<AddressSummaryDTO> addresses
) {
}
