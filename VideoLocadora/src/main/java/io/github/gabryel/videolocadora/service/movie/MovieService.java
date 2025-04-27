package io.github.gabryel.videolocadora.service.movie;

import io.github.gabryel.videolocadora.dto.movie.MovieDetailDTO;
import io.github.gabryel.videolocadora.dto.movie.MovieSaveDTO;
import io.github.gabryel.videolocadora.entity.MovieEntity;
import io.github.gabryel.videolocadora.mapper.movie.MovieMapper;
import io.github.gabryel.videolocadora.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
    
    public List<MovieDetailDTO> findAll() {
        return movieRepository.findAll().stream()
            .map(movieMapper::toDetailDto)
            .toList();
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
    
    public void delete(Long id) {
        movieRepository.deleteById(id);
    }
}
