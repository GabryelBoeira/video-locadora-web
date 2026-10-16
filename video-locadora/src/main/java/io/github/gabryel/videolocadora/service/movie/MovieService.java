package io.github.gabryel.videolocadora.service.movie;

import io.github.gabryel.videolocadora.model.dto.movie.MovieDetailDTO;
import io.github.gabryel.videolocadora.model.dto.movie.MovieSaveDTO;
import io.github.gabryel.videolocadora.model.dto.page.PagedResponseDTO;
import io.github.gabryel.videolocadora.model.entity.MovieEntity;
import io.github.gabryel.videolocadora.model.mapper.movie.MovieMapper;
import io.github.gabryel.videolocadora.repository.MovieRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    public MovieDetailDTO save(MovieSaveDTO dto) {
        MovieEntity movieEntity = movieMapper.toEntity(dto);
        return movieMapper.toDetailDto(movieRepository.save(movieEntity));
    }

    public PagedResponseDTO<MovieDetailDTO> findAllPaginated(Pageable pageable) {
        var result = movieRepository.findAll(pageable);
        return movieMapper.toDetailPage(result);
    }

    public MovieDetailDTO findById(Long id) {
        return movieRepository.findById(id)
                .map(movieMapper::toDetailDto)
                .orElseThrow();
    }

    public MovieDetailDTO update(Long id, MovieSaveDTO dto) {
        MovieEntity movieEntity = movieRepository.findById(id).orElseThrow();
        movieMapper.toEntity(dto);
        return movieMapper.toDetailDto(movieRepository.save(movieEntity));
    }

    public boolean delete(Long id) {
        Optional<MovieEntity> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            movieRepository.deleteById(id);
            return true;
        }

        return false;
    }

}
