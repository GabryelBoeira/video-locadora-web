package io.github.gabryel.videolocadora.controller.api;

import io.github.gabryel.videolocadora.exception.CustomerException;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerDetailDTO;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerSaveDTO;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerUpdateDTO;
import io.github.gabryel.videolocadora.model.dto.hateoas.Resource;
import io.github.gabryel.videolocadora.model.dto.hateoas.ResourceCollection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cliente", description = "Gerenciar/Manipular dados de clientes")
public interface CustomerApi {

    @GetMapping
    @Operation(summary = "Listar clientes", description = "Retorna a lista paginada de clientes envelopada com links HATEOAS.")
    @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso")
    ResponseEntity<ResourceCollection<CustomerDetailDTO>> getAll(
            @Parameter(hidden = true) @PageableDefault(page = 0, size = 10) Pageable pageable
    );

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente específico e seus links de ação.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    ResponseEntity<Resource<CustomerDetailDTO>> getById(
            @Parameter(description = "ID do cliente", example = "1")
            @PathVariable Long id
    ) throws CustomerException;

    @PostMapping
    @Operation(summary = "Criar cliente", description = "Cria um novo cliente com os dados informados.")
    @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso")
    ResponseEntity<Void> createNewCustomer(
            @Valid @RequestBody CustomerSaveDTO createDto
    ) throws CustomerException;

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza um cliente e retorna os dados novos com seus links HATEOAS.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    ResponseEntity<Resource<CustomerDetailDTO>> updateCustomer(
            @Parameter(description = "ID do cliente", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody CustomerUpdateDTO updateDto
    ) throws CustomerException;

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar cliente por CPF", description = "Retorna um cliente por CPF com seus links HATEOAS.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    ResponseEntity<Resource<CustomerDetailDTO>> getCustomerByCpf(
            @Parameter(description = "CPF do cliente", example = "12345678900")
            @PathVariable String cpf
    ) throws CustomerException;

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar cliente", description = "Desativa o cadastro do cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    ResponseEntity<Void> desactivateCustomer(
            @Parameter(description = "ID do cliente", example = "1")
            @PathVariable Long id
    ) throws CustomerException;

}