package io.github.gabryel.videolocadora.dto.customer;

import io.github.gabryel.videolocadora.dto.address.AddressDetailDTO;

import java.util.List;

public record CustomerDetailDTO(
        Long id,
        String name,
        String cpf,
        boolean delayDevolution,
        String email,
        List<AddressDetailDTO> addresses
) {
    public CustomerDetailDTO() {
        this(null, null, null, false, null, null);
    }
}
