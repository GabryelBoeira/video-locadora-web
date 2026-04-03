package io.github.gabryel.videolocadora.controller;

import io.github.gabryel.videolocadora.controller.api.MovieApi;
import io.github.gabryel.videolocadora.model.dto.movie.MovieDetailDTO;
import io.github.gabryel.videolocadora.model.dto.movie.MovieSaveDTO;
import io.github.gabryel.videolocadora.service.movie.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/filmes")
public class MovieController implements MovieApi {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public ResponseEntity<List<MovieDetailDTO>> getAllMovies() {
        return ResponseEntity.ok(movieService.findAllMovies());
    }

    @Override
    public ResponseEntity<MovieDetailDTO> getMovieById(Long id) {
        MovieDetailDTO movie = movieService.findById(id);
        return Optional.ofNullable(movie)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<MovieDetailDTO> createMovie(@Valid @RequestBody MovieSaveDTO movie, UriComponentsBuilder uriBuilder) {
        MovieDetailDTO savedMovie = movieService.save(movie);
        URI uri = uriBuilder.path("/filmes/{id}").buildAndExpand(savedMovie.id()).toUri();
        return ResponseEntity.created(uri).body(savedMovie);
    }

    @Override
    public ResponseEntity<Void> deleteMovie(Long id) {
        return movieService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}