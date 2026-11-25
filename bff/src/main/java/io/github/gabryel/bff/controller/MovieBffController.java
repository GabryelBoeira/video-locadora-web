package io.github.gabryel.bff.controller;

import io.github.gabryel.bff.dto.movie.MovieCreateRequestDTO;
import io.github.gabryel.bff.dto.movie.MovieSummaryDTO;
import io.github.gabryel.bff.dto.page.PageResultDTO;
import io.github.gabryel.bff.service.MovieBffService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Expõe o domínio "filmes" do video-locadora sob o path padronizado /bff/movies.
 */
@RestController
@RequestMapping("/bff/movies")
public class MovieBffController {

    private final MovieBffService service;

    public MovieBffController(MovieBffService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PageResultDTO<MovieSummaryDTO>> list(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieSummaryDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<MovieSummaryDTO> create(@RequestBody @Valid MovieCreateRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
