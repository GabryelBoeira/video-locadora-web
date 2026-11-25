package io.github.gabryel.bff.controller;

import io.github.gabryel.bff.dto.customer.CustomerCreateRequestDTO;
import io.github.gabryel.bff.dto.customer.CustomerSummaryDTO;
import io.github.gabryel.bff.dto.customer.CustomerUpdateRequestDTO;
import io.github.gabryel.bff.dto.page.PageResultDTO;
import io.github.gabryel.bff.service.CustomerBffService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bff/customers")
public class CustomerBffController {

    private final CustomerBffService service;

    public CustomerBffController(CustomerBffService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PageResultDTO<CustomerSummaryDTO>> list(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerSummaryDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<CustomerSummaryDTO> getByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(service.findByCpf(cpf));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CustomerCreateRequestDTO request) {
        service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerSummaryDTO> update(@PathVariable Long id,
            @RequestBody @Valid CustomerUpdateRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
