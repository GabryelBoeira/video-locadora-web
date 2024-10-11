package io.github.gabryel.videolocadora.controller;

import io.github.gabryel.videolocadora.exception.BusinessException;
import io.github.gabryel.videolocadora.service.customer.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/customer")
@Tag(name = "Cliente", description = "Gerenciar/Manipular dados de clientes")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }

    @Operation(summary = "Buscar cliente por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) throws BusinessException {
        return ResponseEntity.ok(customerService.findById(id));
    }

}
