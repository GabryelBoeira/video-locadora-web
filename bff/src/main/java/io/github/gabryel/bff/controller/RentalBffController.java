package io.github.gabryel.bff.controller;

import io.github.gabryel.bff.dto.page.PageResultDTO;
import io.github.gabryel.bff.dto.rental.RentalCreateRequestDTO;
import io.github.gabryel.bff.dto.rental.RentalReturnRequestDTO;
import io.github.gabryel.bff.dto.rental.RentalSummaryDTO;
import io.github.gabryel.bff.service.RentalBffService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bff/rentals")
public class RentalBffController {

    private final RentalBffService service;

    public RentalBffController(RentalBffService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PageResultDTO<RentalSummaryDTO>> list(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalSummaryDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<RentalSummaryDTO> create(@RequestBody @Valid RentalCreateRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<Void> returnRental(@PathVariable Long id,
            @RequestBody @Valid RentalReturnRequestDTO request) {
        service.returnRental(id, request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/items/{itemId}/return")
    public ResponseEntity<Void> returnItem(@PathVariable Long id, @PathVariable Long itemId,
            @RequestBody @Valid RentalReturnRequestDTO request) {
        service.returnItem(id, itemId, request);
        return ResponseEntity.noContent().build();
    }

}
