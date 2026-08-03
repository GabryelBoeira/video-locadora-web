package io.github.gabryel.videolocadora.model.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO para adicionar item ao carrinho")
public record CartAddItemDTO(
        @Schema(description = "ID do filme a ser adicionado", example = "1")
        @NotNull(message = "ID do filme é obrigatório")
        Long movieId,

        @Schema(description = "Número de dias de locação", example = "3")
        @NotNull(message = "Número de dias é obrigatório")
        @Min(value = 1, message = "Mínimo de 1 dia de locação")
        Integer rentalDays
) {}