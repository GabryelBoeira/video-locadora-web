package io.github.gabryel.videolocadora.controller.api;

import io.github.gabryel.videolocadora.model.dto.movie.MovieDetailDTO;
import io.github.gabryel.videolocadora.model.dto.movie.MovieSaveDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public interface MovieApi {

    @GetMapping
    @Operation(summary = "Listar filmes", description = "Retorna a lista completa de filmes cadastrados.")
    @ApiResponse(
            responseCode = "200",
            description = "Lista de filmes retornada com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovieDetailDTO.class)))
    )
    ResponseEntity<List<MovieDetailDTO>> getAllMovies();

    @GetMapping("/{id}")
    @Operation(summary = "Buscar filme por ID", description = "Retorna um filme específico com base no ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Filme encontrado",
                    content = @Content(schema = @Schema(implementation = MovieDetailDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    })
    ResponseEntity<MovieDetailDTO> getMovieById(
            @Parameter(description = "ID do filme", example = "1")
            @PathVariable Long id
    );

    @PostMapping
    @Operation(summary = "Criar filme", description = "Cria um novo filme com os dados informados.")
    @ApiResponse(
            responseCode = "201",
            description = "Filme criado com sucesso",
            content = @Content(schema = @Schema(implementation = MovieDetailDTO.class))
    )
    ResponseEntity<MovieDetailDTO> createMovie(
            @Valid @RequestBody MovieSaveDTO movie,
            UriComponentsBuilder uriBuilder
    );

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir filme", description = "Exclui um filme com base no ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Filme excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    })
    ResponseEntity<Void> deleteMovie(
            @Parameter(description = "ID do filme", example = "1")
            @PathVariable Long id
    );

}