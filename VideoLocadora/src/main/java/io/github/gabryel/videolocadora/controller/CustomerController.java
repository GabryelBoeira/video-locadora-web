package io.github.gabryel.videolocadora.controller;

import io.github.gabryel.videolocadora.model.dto.page.PagedResponseDTO;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerDetailDTO;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerSaveDTO;
import io.github.gabryel.videolocadora.exception.BusinessException;
import io.github.gabryel.videolocadora.service.customer.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
    @Operation(summary = "Listar todos os clientes", description = "Listar todos os clientes", tags = {"Cliente"})
    @ApiResponse(
            description = "Success",
            responseCode = "200",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerDetailDTO.class)))
    )
    public ResponseEntity<PagedResponseDTO<CustomerDetailDTO>> getAll(
            @Parameter(hidden = true) @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok(customerService.findAllPaginated(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente específico com base no ID")
    @ApiResponses({
            @ApiResponse(
                    description = "Cliente encontrado",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = CustomerDetailDTO.class))
            ),
            @ApiResponse(
                    description = "Cliente não encontrado",
                    responseCode = "404",
                    content = @Content(schema = @Schema(type = "object", example = "{\"mensagem\": \"Cliente não encontrado\"}"))
            )
    })
    public ResponseEntity<CustomerDetailDTO> getById(@Parameter(description = "ID do cliente a ser buscado", example = "1") @PathVariable Long id) throws BusinessException {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Criar novo cliente", description = "Cria um novo cliente com os dados fornecidos")
    @ApiResponse(
            description = "Cliente criado com sucesso",
            responseCode = "201",
            content = @Content(schema = @Schema(implementation = CustomerDetailDTO.class)) //TODO: Change to CustomerDetailDTO
    )
    public ResponseEntity<Void> createNewCustomer(@Valid @RequestBody CustomerSaveDTO createDto) {
        customerService.save(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza um cliente existente com base no ID")
    @ApiResponses({
            @ApiResponse(
                    description = "Cliente atualizado com sucesso",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = CustomerDetailDTO.class))
            ),
            @ApiResponse(
                    description = "Cliente não encontrado",
                    responseCode = "404",
                    content = @Content(schema = @Schema(type = "object", example = "{\"mensagem\": \"Cliente não encontrado\"}"))
            )
    })
    public ResponseEntity<Void> updateCustomer(@Parameter(description = "ID do cliente a ser atualizado", example = "1") @PathVariable Long id, @Valid @RequestBody CustomerSaveDTO updateDto) throws BusinessException {
        customerService.update(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar cliente por CPF", description = "Retorna um cliente específico com base no CPF")
    @ApiResponses({
            @ApiResponse(
                    description = "Cliente encontrado",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = CustomerDetailDTO.class))
            ),
            @ApiResponse(
                    description = "Cliente não encontrado",
                    responseCode = "404",
                    content = @Content(schema = @Schema(type = "object", example = "{\"mensagem\": \"Cliente não encontrado\"}"))
            )
    })
    public ResponseEntity<CustomerDetailDTO> getCustomerByCpf(@Parameter(description = "CPF do cliente a ser buscado", example = "123.456.789-00") @PathVariable String cpf) throws BusinessException {
        return ResponseEntity.ok(customerService.findByCpf(cpf));
    }
    
}
