package io.github.gabryel.videolocadora.model.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

@Schema(description = "Detalhes do carrinho de compras")
public record CartDetailDTO(
        Long id,
        String sessionId,
        Long customerId,
        String customerName,
        Instant createdAt,
        Instant expiresAt,
        Double totalPrice,
        String status,
        List<CartItemDetailDTO> items,
        Integer itemCount,
        boolean expired
) {
    @Schema(description = "Detalhes de item do carrinho")
    public record CartItemDetailDTO(
            Long id,
            Long movieId,
            String movieTitle,
            Integer rentalDays,
            Double unitPrice,
            Double subtotal,
            Instant addedAt,
            Instant reservationExpiresAt,
            boolean reserved,
            boolean reservationExpired
    ) {}
}