package io.github.gabryel.videolocadora.controller.api;

import io.github.gabryel.videolocadora.exception.BusinessException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "", description = "Gerenciar/Manipular dados de clientes")
public interface RentalApi {

    @PostMapping
    @Operation(
            summary = "Criar locação",
            description = "Cria uma nova locação a partir dos dados informados."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Locação criada com sucesso",
            content = @Content(schema = @Schema(implementation = RentalDetailDTO.class))
    )
    ResponseEntity<RentalDetailDTO> create(@Valid @RequestBody RentalCreateDTO dto) throws BusinessException;

    @GetMapping
    @Operation(
            summary = "Listar locações",
            description = "Retorna a lista de todas as locações."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de locações retornada com sucesso",
            content = @Content(schema = @Schema(implementation = RentalDetailDTO.class))
    )
    ResponseEntity<List<RentalDetailDTO>> list();

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar locação por ID",
            description = "Retorna uma locação específica com base no ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Locação encontrada",
                    content = @Content(schema = @Schema(implementation = RentalDetailDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Locação não encontrada")
    })
    ResponseEntity<RentalDetailDTO> getById(
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