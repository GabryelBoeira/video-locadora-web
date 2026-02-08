package io.github.gabryel.videolocadora.model.dto.customer;

import io.github.gabryel.videolocadora.model.dto.address.AddressSaveDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Objeto de transferência de dados para criar um novo cliente.")
public record CustomerSaveDTO(
        @Schema(description = "Nome completo do cliente.", example = "João da Silva", required = true)
        @NotBlank
        String name,

        @Schema(description = "Número de CPF do cliente (sem formatação).", example = "12345678909", required = true, pattern = "^\\d{11}$")
        @CPF
        @NotBlank
        String cpf,

        @Schema(description = "Endereço de e-mail do cliente.", example = "joao.silva@example.com", format = "email")
        @Email
        String email,

        @Schema(description = "Data de nascimento do cliente.", example = "1998-05-22", required = true)
        @NotNull
        @Past
        LocalDate birthDate,

        @Schema(description = "Objeto contendo os detalhes do endereço do cliente.", required = true)
        @Valid
        @NotNull
        List<AddressSaveDTO> addresses
) {
    public CustomerSaveDTO() {
        this(null, null, null, null, null);
    }

}
