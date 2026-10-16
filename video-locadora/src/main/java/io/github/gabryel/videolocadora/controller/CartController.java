package io.github.gabryel.videolocadora.controller;

import io.github.gabryel.videolocadora.controller.api.CartApi;
import io.github.gabryel.videolocadora.exception.BusinessException;
import io.github.gabryel.videolocadora.model.dto.cart.CartAddItemDTO;
import io.github.gabryel.videolocadora.model.dto.cart.CartCreateDTO;
import io.github.gabryel.videolocadora.model.dto.cart.CartDetailDTO;
import io.github.gabryel.videolocadora.model.dto.hateoas.Resource;
import io.github.gabryel.videolocadora.model.mapper.hateoas.LinkBuilder;
import io.github.gabryel.videolocadora.service.cart.CartService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/cart")
public class CartController implements CartApi {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public ResponseEntity<Resource<CartDetailDTO>> createCart(@Valid @RequestBody CartCreateDTO dto) throws BusinessException {
        var cart = cartService.createCart(dto);
        return ResponseEntity
                .created(URI.create("/cart/" + cart.sessionId()))
                .body(toResource(cart));
    }

    @Override
    public ResponseEntity<Resource<CartDetailDTO>> getCart(
            @Parameter(description = "Session ID do carrinho") @PathVariable String sessionId
    ) throws BusinessException {
        return ResponseEntity.ok(toResource(cartService.findBySessionId(sessionId)));
    }

    @Override
    public ResponseEntity<Resource<CartDetailDTO>> addItem(
            @PathVariable String sessionId,
            @Valid @RequestBody CartAddItemDTO dto
    ) throws BusinessException {
        // Idempotência: se o filme já está no carrinho, retorna 409
        var cart = cartService.addItem(sessionId, dto);
        return ResponseEntity.ok(toResource(cart));
    }

    @Override
    public ResponseEntity<Resource<CartDetailDTO>> removeItem(
            @PathVariable String sessionId,
            @PathVariable Long itemId
    ) throws BusinessException {
        var cart = cartService.removeItem(sessionId, itemId);
        return ResponseEntity.ok(toResource(cart));
    }

    @Override
    public ResponseEntity<Void> checkout(@PathVariable String sessionId) throws BusinessException {
        Long rentalId = cartService.convertToRental(sessionId);
        return ResponseEntity
                .created(URI.create("/rentals/" + rentalId))
                .build();
    }

    private Resource<CartDetailDTO> toResource(CartDetailDTO cart) {
        var links = LinkBuilder.from("/cart", cart.sessionId())
                .self()
                .custom("addItem", "/items", "PUT")
                .custom("checkout", "/checkout", "POST")
                .build();
        return Resource.of(cart, links);
    }
}