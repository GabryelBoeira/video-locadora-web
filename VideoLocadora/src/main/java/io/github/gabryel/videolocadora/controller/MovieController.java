package io.github.gabryel.videolocadora.controller;

import io.github.gabryel.videolocadora.dto.movie.MovieDetailDTO;
import io.github.gabryel.videolocadora.dto.movie.MovieSaveDTO;
import io.github.gabryel.videolocadora.service.movie.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/filmes")
@Tag(name = "Filmes", description = "Operações relacionadas a filmes") // Adiciona a tag para o Swagger
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    @Operation(
            summary = "Obter todos os filmes",
            description = "Retorna a lista completa de filmes."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de filmes retornada com sucesso.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = MovieDetailDTO.class)) // Define o tipo do array na resposta
            )
    )
    public ResponseEntity<List<MovieDetailDTO>> getAllMovies() {
        List<MovieDetailDTO> movies = movieService.findAllMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obter filme por ID",
            description = "Retorna um filme específico com base no ID fornecido."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Filme encontrado e retornado com sucesso.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MovieDetailDTO.class) // Define o tipo do objeto na resposta
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Filme não encontrado.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(type = "object", example = "{\"mensagem\": \"Filme não encontrado.\"}")
                    )
            )
    })
    public ResponseEntity<MovieDetailDTO> getMovieById(@Parameter(description = "ID do filme a ser recuperado", example = "1") @PathVariable Long id) {
        MovieDetailDTO movie = movieService.findById(id);
        return Optional.ofNullable(movie).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
            summary = "Criar um novo filme",
            description = "Cria um novo filme com os dados fornecidos."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Filme criado com sucesso.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = MovieSaveDTO.class)  // Define o tipo do objeto na resposta
            )
    )
    public ResponseEntity<MovieDetailDTO> createMovie(@Valid @RequestBody MovieSaveDTO movie, UriComponentsBuilder uriBuilder) {
        MovieDetailDTO savedMovie = movieService.save(movie);
        URI uri = uriBuilder.path("/filmes/{id}").buildAndExpand(savedMovie.id()).toUri();
        return ResponseEntity.created(uri).body(savedMovie);
    }

//    @PutMapping("/{id}")
//    @Operation(
//            summary = "Atualizar filme",
//            description = "Atualiza um filme existente, substituindo-o completamente."
//    )
//    @ApiResponses({
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "Filme atualizado com sucesso.",
//                    content = @Content(
//                            mediaType = MediaType.APPLICATION_JSON_VALUE,
//                            schema = @Schema(implementation = Movie.class)
//                    )
//            ),
//            @ApiResponse(
//                    responseCode = "404",
//                    description = "Filme não encontrado.",
//                    content = @Content(
//                            mediaType = MediaType.APPLICATION_JSON_VALUE,
//                            schema = @Schema(type = "object", example = "{\"mensagem\": \"Filme não encontrado.\"}")
//                    )
//            )
//    })
//    public ResponseEntity<Movie> updateMovie(@Parameter(description = "ID do filme a ser atualizado", example = "1") @PathVariable Long id, @Valid @RequestBody Movie movie) {
//        Optional<Movie> existingMovie = movieService.findMovieById(id);
//        if (existingMovie.isPresent()) {
//            movie.setId(id); // Garante que o ID seja o correto
//            Movie updatedMovie = movieService.saveMovie(movie); // Usa o save para atualizar
//            return ResponseEntity.ok(updatedMovie);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PatchMapping("/{id}")
//    @Operation(
//            summary = "Atualizar filme parcialmente",
//            description = "Atualiza parcialmente um filme existente, modificando apenas os campos fornecidos."
//    )
//    @ApiResponses({
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "Filme atualizado parcialmente com sucesso.",
//                    content = @Content(
//                            mediaType = MediaType.APPLICATION_JSON_VALUE,
//                            schema = @Schema(implementation = Movie.class)
//                    )
//            ),
//            @ApiResponse(
//                    responseCode = "404",
//                    description = "Filme não encontrado.",
//                    content = @Content(
//                            mediaType = MediaType.APPLICATION_JSON_VALUE,
//                            schema = @Schema(type = "object", example = "{\"mensagem\": \"Filme não encontrado.\"}")
//                    )
//            )
//    })
//    public ResponseEntity<Movie> partialUpdateMovie(
//            @Parameter(description = "ID do filme a ser atualizado", example = "1") @PathVariable Long id,
//            @RequestBody Movie movie
//    ) {
//        Optional<Movie> existingMovie = movieService.findMovieById(id);
//        if (existingMovie.isPresent()) {
//            Movie movieToUpdate = existingMovie.get();
//            // Atualiza apenas os campos não nulos do filme fornecido
//            if (movie.getTitle() != null) {
//                movieToUpdate.setTitle(movie.getTitle());
//            }
//            if (movie.getContentRating() != null) {
//                movieToUpdate.setContentRating(movie.getContentRating());
//            }
//            if (movie.getInternalCode() != null) {
//                movieToUpdate.setInternalCode(movie.getInternalCode());
//            }
//            if (movie.getPrice() != null) {
//                movieToUpdate.setPrice(movie.getPrice());
//            }
//            if (movie.getAvailable() != null) {
//                movieToUpdate.setAvailable(movie.getAvailable());
//            }
//            if (movie.getType() != null) {
//                movieToUpdate.setType(movie.getType());
//            }
//            if (movie.getGenre() != null) {
//                movieToUpdate.setGenre(movie.getGenre());
//            }
//            Movie updatedMovie = movieService.saveMovie(movieToUpdate);
//            return ResponseEntity.ok(updatedMovie);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir filme",
            description = "Exclui um filme com base no ID fornecido."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Filme excluído com sucesso."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Filme não encontrado.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(type = "object", example = "{\"mensagem\": \"Filme não encontrado.\"}")
                    )
            )
    })
    public ResponseEntity<Void> deleteMovie(@Parameter(description = "ID do filme a ser excluído", example = "1") @PathVariable Long id) {
        if ( movieService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

