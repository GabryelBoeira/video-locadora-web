package io.github.gabryel.videolocadora.controller;

import io.github.gabryel.videolocadora.dto.movie.MovieDetailDTO;
import io.github.gabryel.videolocadora.dto.movie.MovieSaveDTO;
import io.github.gabryel.videolocadora.service.movie.MovieService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
@Tag(name = "Filmes", description = "Gerenciar/Manipular dados de clientes")
public class MovieController {
    
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDetailDTO create(@RequestBody MovieSaveDTO dto) {
        return movieService.save(dto);
    }
    
    @GetMapping
    public List<MovieDetailDTO> getAll() {
        return movieService.findAll();
    }
    
    @GetMapping("/{id}")
    public MovieDetailDTO getById(@PathVariable Long id) {
        return movieService.findById(id);
    }
    
    @PutMapping("/{id}")
    public MovieDetailDTO update(@PathVariable Long id, @RequestBody MovieSaveDTO dto) {
        return movieService.update(id, dto);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        movieService.delete(id);
    }

}
