package io.github.gabryel.bff.service;

import io.github.gabryel.bff.client.MovieClient;
import io.github.gabryel.bff.dto.movie.MovieCreateRequestDTO;
import io.github.gabryel.bff.dto.movie.MovieSummaryDTO;
import io.github.gabryel.bff.dto.page.PageResultDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovieBffService {

    private final MovieClient movieClient;

    public MovieBffService(MovieClient movieClient) {
        this.movieClient = movieClient;
    }

    public PageResultDTO<MovieSummaryDTO> findAll(Pageable pageable) {
        return movieClient.findAll(pageable);
    }

    public MovieSummaryDTO findById(Long id) {
        return movieClient.findById(id);
    }

    public MovieSummaryDTO create(MovieCreateRequestDTO request) {
        return movieClient.create(request);
    }

    public void delete(Long id) {
        movieClient.delete(id);
    }

}
