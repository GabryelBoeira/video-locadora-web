package io.github.gabryel.videolocadora.controller.api;

import io.github.gabryel.videolocadora.exception.BusinessException;
import io.github.gabryel.videolocadora.model.dto.hateoas.Resource;
import io.github.gabryel.videolocadora.model.dto.hateoas.ResourceCollection;
import io.github.gabryel.videolocadora.model.dto.rental.RentalCreateDTO;
import io.github.gabryel.videolocadora.model.dto.rental.RentalDetailDTO;
import io.github.gabryel.videolocadora.model.dto.rental.RentalItemReturnDTO;
import io.github.gabryel.videolocadora.model.dto.rental.RentalReturnDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Aluguel", description = "Gerenciar/Manipular dados de Aluguel")
public interface RentalApi {

    @PostMapping
    @Operation(
            summary = "Criar locação",
            description = "Cria uma nova locação a partir dos dados informados, com links HATEOAS de navegação."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Locação criada com sucesso, incluindo links HATEOAS",
            content = @Content(schema = @Schema(implementation = Resource.class))
    )
    ResponseEntity<Resource<RentalDetailDTO>> create(@Valid @RequestBody RentalCreateDTO dto) throws BusinessException;

    @GetMapping
    @Operation(
            summary = "Listar locações",
            description = "Retorna a lista paginada de todas as locações, envelopada com links HATEOAS."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Página de locações retornada com sucesso, incluindo links HATEOAS",
            content = @Content(schema = @Schema(implementation = ResourceCollection.class))
    )
    ResponseEntity<ResourceCollection<RentalDetailDTO>> list(
            @Parameter(hidden = true) @PageableDefault(page = 0, size = 10) Pageable pageable
    );

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar locação por ID",
            description = "Retorna uma locação específica com base no ID, com links HATEOAS de navegação."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Locação encontrada, incluindo links HATEOAS",
                    content = @Content(schema = @Schema(implementation = Resource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Locação não encontrada")
    })
    ResponseEntity<Resource<RentalDetailDTO>> getById(
            @Parameter(description = "ID da locação", example = "1")
            @PathVariable Long id
    ) throws BusinessException;

    @PostMapping("/{id}/return")
    @Operation(
            summary = "Devolver locação",
            description = "Registra a devolução de uma locação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Locação devolvida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Locação não encontrada"),
            @ApiResponse(responseCode = "400", description = "Locação não está aberta")
    })
    ResponseEntity<Void> returnRental(
            @Parameter(description = "ID da locação", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody RentalReturnDTO dto
    ) throws BusinessException;

    @PostMapping("/{id}/items/{itemId}/return")
    @Operation(
            summary = "Devolver item da locação",
            description = "Registra a devolução de um item específico da locação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item devolvido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Locação ou item não encontrado")
    })
    ResponseEntity<Void> returnRentalItem(
            @Parameter(description = "ID da locação", example = "1")
            @PathVariable Long id,
            @Parameter(description = "ID do item", example = "10")
            @PathVariable Long itemId,
            @Valid @RequestBody RentalItemReturnDTO dto
    ) throws BusinessException;

}