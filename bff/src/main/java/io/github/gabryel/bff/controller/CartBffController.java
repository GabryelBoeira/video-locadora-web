package io.github.gabryel.bff.controller;

import io.github.gabryel.bff.dto.cart.CartAddItemRequestDTO;
import io.github.gabryel.bff.dto.cart.CartCreateRequestDTO;
import io.github.gabryel.bff.dto.cart.CartSummaryDTO;
import io.github.gabryel.bff.service.CartBffService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/bff/cart")
public class CartBffController {

    private final CartBffService service;

    public CartBffController(CartBffService service) {
        this.service = service;
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<CartSummaryDTO> getBySessionId(@PathVariable String sessionId) {
        return ResponseEntity.ok(service.findBySessionId(sessionId));
    }

    @PostMapping
    public ResponseEntity<CartSummaryDTO> create(@RequestBody @Valid CartCreateRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{sessionId}/items")
    public ResponseEntity<CartSummaryDTO> addItem(@PathVariable String sessionId,
            @RequestBody @Valid CartAddItemRequestDTO request) {
        return ResponseEntity.ok(service.addItem(sessionId, request));
    }

    @DeleteMapping("/{sessionId}/items/{itemId}")
    public ResponseEntity<CartSummaryDTO> removeItem(@PathVariable String sessionId, @PathVariable Long itemId) {
        return ResponseEntity.ok(service.removeItem(sessionId, itemId));
    }

    @PostMapping("/{sessionId}/checkout")
    public ResponseEntity<Map<String, Long>> checkout(@PathVariable String sessionId) {
        Long rentalId = service.checkout(sessionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("rentalId", rentalId));
    }

}
