package io.github.gabryel.videolocadora.model.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(description = "DTO para criar carrinho")
public record CartCreateDTO(
        @Schema(description = "ID do cliente (opcional para carrinho anônimo)")
        Long customerId,

        @Schema(description = "Tempo de expiração em minutos", example = "30")
        @Min(value = 5, message = "Tempo mínimo de expiração é 5 minutos")
        Integer expirationMinutes
) {
    public CartCreateDTO {
        if (expirationMinutes == null) {
            expirationMinutes = 30; // padrão
        }
    }
}