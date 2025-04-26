package io.github.gabryel.videolocadora.dto.customer;

import io.github.gabryel.videolocadora.dto.address.AddressSaveDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public record CustomerSaveDTO(
        @NotBlank
        String name,

        @CPF
        @NotBlank
        String cpf,

        @Email
        String email,

        @Valid
        @NotNull
        List<AddressSaveDTO> addresses
) {
    public CustomerSaveDTO() {
        this(null, null, null, null);
    }

}
