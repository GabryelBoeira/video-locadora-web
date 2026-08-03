package io.github.gabryel.videolocadora.model.dto.customer;

import io.github.gabryel.videolocadora.model.dto.address.AddressSaveDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Objeto de transferência de dados para atualizar cliente.")
public record CustomerUpdateDTO(
        @Schema(description = "Nome completo do cliente.", example = "João da Silva")
        @NotBlank
        String name,

        @Email
        @Schema(description = "Endereço de e-mail do cliente.", example = "joao.silva@example.com", format = "email")
        String email,

        @Schema(description = "Data de nascimento do cliente.", example = "1998-05-22")
        @NotNull
        @Past
        LocalDate birthDate,

        @Max(20)
        @NotBlank
        @Schema(description = "Número de celular do cliente (sem formatação).", example = "+5541999999999")
        String cellPhone,

        @Valid
        @NotNull
        @Schema(description = "Objeto contendo os detalhes do endereço do cliente.")
        List<AddressSaveDTO> addresses
) {

}
