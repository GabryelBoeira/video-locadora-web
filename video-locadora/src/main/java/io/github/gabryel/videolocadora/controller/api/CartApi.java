package io.github.gabryel.videolocadora.controller.api;

import io.github.gabryel.videolocadora.exception.BusinessException;
import io.github.gabryel.videolocadora.model.dto.cart.CartAddItemDTO;
import io.github.gabryel.videolocadora.model.dto.cart.CartCreateDTO;
import io.github.gabryel.videolocadora.model.dto.cart.CartDetailDTO;
import io.github.gabryel.videolocadora.model.dto.hateoas.Resource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Carrinho", description = "Operações de carrinho de compras")
public interface CartApi {

    @PostMapping
    @Operation(
            summary = "Criar novo carrinho",
            description = "Cria um carrinho de compras com sessão única, com links HATEOAS de navegação."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Carrinho criado com sucesso, incluindo links HATEOAS",
            content = @Content(schema = @Schema(implementation = Resource.class))
    )
    ResponseEntity<Resource<CartDetailDTO>> createCart(@Valid @RequestBody CartCreateDTO dto) throws BusinessException;

    @GetMapping("/{sessionId}")
    @Operation(
            summary = "Buscar carrinho",
            description = "Retorna o carrinho ativo com base no sessionId, com links HATEOAS de navegação."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Carrinho encontrado, incluindo links HATEOAS",
                    content = @Content(schema = @Schema(implementation = Resource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Carrinho não encontrado")
    })
    ResponseEntity<Resource<CartDetailDTO>> getCart(
            @Parameter(description = "Session ID do carrinho", example = "ABC123")
            @PathVariable String sessionId
    ) throws BusinessException;

    @PutMapping("/{sessionId}/items")
    @Operation(
            summary = "Adicionar item ao carrinho",
            description = "Adiciona um filme ao carrinho com reserva temporária, com links HATEOAS de navegação."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Item adicionado com sucesso, incluindo links HATEOAS",
                    content = @Content(schema = @Schema(implementation = Resource.class))
            ),
            @ApiResponse(responseCode = "409", description = "Filme já adicionado ao carrinho")
    })
    ResponseEntity<Resource<CartDetailDTO>> addItem(
            @Parameter(description = "Session ID do carrinho", example = "ABC123")
            @PathVariable String sessionId,
            @Valid @RequestBody CartAddItemDTO dto
    ) throws BusinessException;

    @DeleteMapping("/{sessionId}/items/{itemId}")
    @Operation(
            summary = "Remover item do carrinho",
            description = "Remove um item do carrinho e libera o estoque do filme, com links HATEOAS de navegação."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Item removido com sucesso, incluindo links HATEOAS",
                    content = @Content(schema = @Schema(implementation = Resource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Carrinho ou item não encontrado")
    })
    ResponseEntity<Resource<CartDetailDTO>> removeItem(
            @Parameter(description = "Session ID do carrinho", example = "ABC123")
            @PathVariable String sessionId,
            @Parameter(description = "ID do item", example = "1")
            @PathVariable Long itemId
    ) throws BusinessException;

    @PostMapping("/{sessionId}/checkout")
    @Operation(
            summary = "Finalizar carrinho",
            description = "Converte o carrinho em locação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Locação criada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Carrinho não encontrado")
    })
    ResponseEntity<Void> checkout(
            @Parameter(description = "Session ID do carrinho", example = "ABC123")
            @PathVariable String sessionId
    ) throws BusinessException;
}