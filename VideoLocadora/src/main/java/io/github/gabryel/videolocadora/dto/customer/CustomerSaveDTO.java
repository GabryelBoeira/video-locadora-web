package io.github.gabryel.videolocadora.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record CustomerSaveDTO(
        @NotBlank
        String name,

        @CPF
        @NotBlank
        String cpf,

        @Email
        String email
) {
}
