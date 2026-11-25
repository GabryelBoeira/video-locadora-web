package io.github.gabryel.bff.service;

import io.github.gabryel.bff.client.CartClient;
import io.github.gabryel.bff.dto.cart.CartAddItemRequestDTO;
import io.github.gabryel.bff.dto.cart.CartCreateRequestDTO;
import io.github.gabryel.bff.dto.cart.CartSummaryDTO;
import org.springframework.stereotype.Service;

@Service
public class CartBffService {

    private final CartClient cartClient;

    public CartBffService(CartClient cartClient) {
        this.cartClient = cartClient;
    }

    public CartSummaryDTO findBySessionId(String sessionId) {
        return cartClient.findBySessionId(sessionId);
    }

    public CartSummaryDTO create(CartCreateRequestDTO request) {
        return cartClient.create(request);
    }

    public CartSummaryDTO addItem(String sessionId, CartAddItemRequestDTO request) {
        return cartClient.addItem(sessionId, request);
    }

    public CartSummaryDTO removeItem(String sessionId, Long itemId) {
        return cartClient.removeItem(sessionId, itemId);
    }

    public Long checkout(String sessionId) {
        return cartClient.checkout(sessionId);
    }

}
