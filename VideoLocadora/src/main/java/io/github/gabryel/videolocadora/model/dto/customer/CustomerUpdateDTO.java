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

@Schema(description = "Objeto de transferência de dados para atualizar cliente.")
public record CustomerUpdateDTO(
        @Schema(description = "Nome completo do cliente.", example = "João da Silva")
        @NotBlank
        String name,

        @Schema(description = "Endereço de e-mail do cliente.", example = "joao.silva@example.com", format = "email")
        @Email
        String email,

        @Schema(description = "Data de nascimento do cliente.", example = "1998-05-22")
        @NotNull
        @Past
        LocalDate birthDate,

        @Schema(description = "Objeto contendo os detalhes do endereço do cliente.")
        @Valid
        @NotNull
        List<AddressSaveDTO> addresses
) {

}
