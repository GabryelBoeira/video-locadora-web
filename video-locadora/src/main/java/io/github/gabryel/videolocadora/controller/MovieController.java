package io.github.gabryel.videolocadora.controller;

import io.github.gabryel.videolocadora.controller.api.MovieApi;
import io.github.gabryel.videolocadora.model.dto.hateoas.Link;
import io.github.gabryel.videolocadora.model.dto.hateoas.Resource;
import io.github.gabryel.videolocadora.model.dto.hateoas.ResourceCollection;
import io.github.gabryel.videolocadora.model.dto.movie.MovieDetailDTO;
import io.github.gabryel.videolocadora.model.dto.movie.MovieSaveDTO;
import io.github.gabryel.videolocadora.model.mapper.hateoas.LinkBuilder;
import io.github.gabryel.videolocadora.service.movie.MovieService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/filmes")
public class MovieController implements MovieApi {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public ResponseEntity<ResourceCollection<MovieDetailDTO>> getAllMovies(Pageable pageable) {
        return ResponseEntity.ok(ResourceCollection.of(movieService.findAllPaginated(pageable), "/filmes", this::movieLinks));
    }

    @Override
    public ResponseEntity<Resource<MovieDetailDTO>> getMovieById(Long id) {
        MovieDetailDTO movie = movieService.findById(id);
        return Optional.ofNullable(movie)
                .map(this::toResource)
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

    private Resource<MovieDetailDTO> toResource(MovieDetailDTO movie) {
        return Resource.of(movie, movieLinks(movie));
    }

    private Map<String, Link> movieLinks(MovieDetailDTO movie) {
        return LinkBuilder.from("/filmes", movie.id())
                .self()
                .delete()
                .build();
    }

}