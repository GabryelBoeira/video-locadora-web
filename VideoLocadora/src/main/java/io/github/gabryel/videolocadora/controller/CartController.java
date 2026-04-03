package io.github.gabryel.videolocadora.controller;

import io.github.gabryel.videolocadora.controller.api.CartApi;
import io.github.gabryel.videolocadora.exception.BusinessException;
import io.github.gabryel.videolocadora.model.dto.cart.CartAddItemDTO;
import io.github.gabryel.videolocadora.model.dto.cart.CartCreateDTO;
import io.github.gabryel.videolocadora.model.dto.cart.CartDetailDTO;
import io.github.gabryel.videolocadora.service.cart.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/cart")
public class CartController implements CartApi {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public ResponseEntity<CartDetailDTO> createCart(@Valid @RequestBody CartCreateDTO dto) throws BusinessException {
        var cart = cartService.createCart(dto);
        return ResponseEntity
                .created(URI.create("/cart/" + cart.sessionId()))
                .body(cart);
    }

    @Override
    public ResponseEntity<CartDetailDTO> getCart(
            @Parameter(description = "Session ID do carrinho") @PathVariable String sessionId
    ) throws BusinessException {
        return ResponseEntity.ok(cartService.findBySessionId(sessionId));
    }

    @Override
    public ResponseEntity<CartDetailDTO> addItem(
            @PathVariable String sessionId,
            @Valid @RequestBody CartAddItemDTO dto
    ) throws BusinessException {
        // Idempotência: se o filme já está no carrinho, retorna 409
        var cart = cartService.addItem(sessionId, dto);
        return ResponseEntity.ok(cart);
    }

    @Override
    public ResponseEntity<CartDetailDTO> removeItem(
            @PathVariable String sessionId,
            @PathVariable Long itemId
    ) throws BusinessException {
        var cart = cartService.removeItem(sessionId, itemId);
        return ResponseEntity.ok(cart);
    }

    @Override
    public ResponseEntity<Void> checkout(@PathVariable String sessionId) throws BusinessException {
        Long rentalId = cartService.convertToRental(sessionId);
        return ResponseEntity
                .created(URI.create("/rentals/" + rentalId))
                .build();
    }
}