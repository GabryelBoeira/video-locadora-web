package io.github.gabryel.videolocadora.mapper.movie;

import io.github.gabryel.videolocadora.dto.movie.MovieDetailDTO;
import io.github.gabryel.videolocadora.dto.movie.MovieSaveDTO;
import io.github.gabryel.videolocadora.entity.MovieEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDetailDTO toDetailDto(MovieEntity movieEntity);

    @Mapping(target = "id", ignore = true)
    MovieEntity toEntity(MovieSaveDTO movieSaveDTO);

}
