package io.github.gabryel.videolocadora.controller.api;

import io.github.gabryel.videolocadora.exception.BusinessException;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerDetailDTO;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerSaveDTO;
import io.github.gabryel.videolocadora.model.dto.page.PagedResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Cliente", description = "Gerenciar/Manipular dados de clientes")
public interface CustomerApi {

    @GetMapping("/")
    @Operation(summary = "Listar clientes", description = "Retorna a lista paginada de clientes cadastrados.")
    @ApiResponse(
            responseCode = "200",
            description = "Lista de clientes retornada com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerDetailDTO.class)))
    )
    ResponseEntity<PagedResponseDTO<CustomerDetailDTO>> getAll(
            @Parameter(hidden = true) @PageableDefault(page = 0, size = 10) Pageable pageable
    );

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente específico com base no ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente encontrado",
                    content = @Content(schema = @Schema(implementation = CustomerDetailDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado"
            )
    })
    ResponseEntity<CustomerDetailDTO> getById(
            @Parameter(description = "ID do cliente", example = "1")
            @PathVariable Long id
    ) throws BusinessException;

    @PostMapping
    @Operation(summary = "Criar cliente", description = "Cria um novo cliente com os dados informados.")
    @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso")
    ResponseEntity<Void> createNewCustomer(@RequestBody CustomerSaveDTO createDto);

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza um cliente existente com base no ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    ResponseEntity<Void> updateCustomer(
            @Parameter(description = "ID do cliente", example = "1")
            @PathVariable Long id,
            @RequestBody CustomerSaveDTO updateDto
    ) throws BusinessException;

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar cliente por CPF", description = "Retorna um cliente específico com base no CPF.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente encontrado",
                    content = @Content(schema = @Schema(implementation = CustomerDetailDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    ResponseEntity<CustomerDetailDTO> getCustomerByCpf(
            @Parameter(description = "CPF do cliente", example = "123.456.789-00")
            @PathVariable String cpf
    ) throws BusinessException;
}