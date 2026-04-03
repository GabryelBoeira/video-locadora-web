package io.github.gabryel.videolocadora.controller;

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
@Tag(name = "Carrinho", description = "Operações de carrinho de compras")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    @Operation(summary = "Criar novo carrinho", description = "Cria um carrinho de compras com sessão única")
    @ApiResponse(responseCode = "201", description = "Carrinho criado")
    public ResponseEntity<CartDetailDTO> createCart(@Valid @RequestBody CartCreateDTO dto) throws BusinessException {
        var cart = cartService.createCart(dto);
        return ResponseEntity
                .created(URI.create("/cart/" + cart.sessionId()))
                .body(cart);
    }

    @GetMapping("/{sessionId}")
    @Operation(summary = "Buscar carrinho", description = "Retorna carrinho ativo por sessionId")
    @ApiResponse(responseCode = "200", description = "Carrinho encontrado")
    public ResponseEntity<CartDetailDTO> getCart(
            @Parameter(description = "Session ID do carrinho") @PathVariable String sessionId
    ) throws BusinessException {
        return ResponseEntity.ok(cartService.findBySessionId(sessionId));
    }

    @PutMapping("/{sessionId}/items")
    @Operation(summary = "Adicionar item ao carrinho", description = "Adiciona filme com reserva temporária (idempotente via movieId)")
    @ApiResponse(responseCode = "200", description = "Item adicionado")
    public ResponseEntity<CartDetailDTO> addItem(
            @PathVariable String sessionId,
            @Valid @RequestBody CartAddItemDTO dto
    ) throws BusinessException {
        // Idempotência: se o filme já está no carrinho, retorna 409
        var cart = cartService.addItem(sessionId, dto);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{sessionId}/items/{itemId}")
    @Operation(summary = "Remover item do carrinho", description = "Remove item e libera estoque")
    @ApiResponse(responseCode = "200", description = "Item removido")
    public ResponseEntity<CartDetailDTO> removeItem(
            @PathVariable String sessionId,
            @PathVariable Long itemId
    ) throws BusinessException {
        var cart = cartService.removeItem(sessionId, itemId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{sessionId}/checkout")
    @Operation(summary = "Finalizar carrinho", description = "Converte carrinho em locação")
    @ApiResponse(responseCode = "201", description = "Locação criada")
    public ResponseEntity<Void> checkout(@PathVariable String sessionId) throws BusinessException {
        Long rentalId = cartService.convertToRental(sessionId);
        return ResponseEntity
                .created(URI.create("/rentals/" + rentalId))
                .build();
    }
}