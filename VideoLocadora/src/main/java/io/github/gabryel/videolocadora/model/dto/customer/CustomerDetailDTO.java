package io.github.gabryel.videolocadora.model.dto.customer;

import io.github.gabryel.videolocadora.model.dto.address.AddressDetailDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Objeto contendo os detalhes de um cliente.")
public record CustomerDetailDTO(
        Long id,
        String name,
        String cpf,
        boolean delayDevolution,
        LocalDate birthDate,
        String email,
        List<AddressDetailDTO> addresses
) {
    public CustomerDetailDTO() {
        this(null, null, null, false, null, null, null);
    }
}
