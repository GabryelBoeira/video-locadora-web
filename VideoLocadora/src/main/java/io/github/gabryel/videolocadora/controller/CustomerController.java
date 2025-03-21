package io.github.gabryel.videolocadora.controller;

import io.github.gabryel.videolocadora.dto.page.PagedResponseDTO;
import io.github.gabryel.videolocadora.dto.customer.CustomerDetailDTO;
import io.github.gabryel.videolocadora.dto.customer.CustomerSaveDTO;
import io.github.gabryel.videolocadora.exception.BusinessException;
import io.github.gabryel.videolocadora.service.customer.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@Tag(name = "Cliente", description = "Gerenciar/Manipular dados de clientes")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    @Operation(summary = "Listar todos os clientes", description = "Listar todos os clientes",
            tags = {"Cliente"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerDetailDTO.class)))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content()),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content()),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content()),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content()),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content()),
            }
    )
    public ResponseEntity<PagedResponseDTO<CustomerDetailDTO>> getAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok(customerService.findAllPaginated(pageable));
    }

    @Operation(summary = "Buscar cliente por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDetailDTO> getById(@PathVariable Long id) throws BusinessException {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @Operation(summary = "Criar novo cliente")
    @PostMapping
    public ResponseEntity<Void> createNewCustomer(@RequestBody CustomerSaveDTO createDto) {
        customerService.save(createDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualizar cliente")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long id, @RequestBody CustomerSaveDTO updateDto) throws BusinessException {
        customerService.update(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Buscar cliente por CPF")
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<CustomerDetailDTO> getCustomerByCpf(@PathVariable String cpf) throws BusinessException {
        return ResponseEntity.ok(customerService.findByCpf(cpf));
    }

}
