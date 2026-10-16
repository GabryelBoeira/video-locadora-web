package io.github.gabryel.videolocadora.model.mapper.movie;

import io.github.gabryel.videolocadora.model.dto.movie.MovieDetailDTO;
import io.github.gabryel.videolocadora.model.dto.movie.MovieSaveDTO;
import io.github.gabryel.videolocadora.model.dto.page.PagedResponseDTO;
import io.github.gabryel.videolocadora.model.entity.MovieEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDetailDTO toDetailDto(MovieEntity movieEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "internalCode", ignore = true)
    MovieEntity toEntity(MovieSaveDTO movieSaveDTO);

    /**
     * Maps a page of movie entities to a page of movie detail DTOs.
     *
     * @param moviePage the page of movie entities to be mapped
     * @return a page with the movie detail DTOs
     */
    default PagedResponseDTO<MovieDetailDTO> toDetailPage(Page<MovieEntity> moviePage) {
        return PagedResponseDTO.fromPage(moviePage, this::toDetailDto);
    }

}
