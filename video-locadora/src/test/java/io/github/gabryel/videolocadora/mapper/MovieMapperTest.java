package io.github.gabryel.videolocadora.mapper;

import io.github.gabryel.videolocadora.model.entity.MovieEntity;
import io.github.gabryel.videolocadora.model.enums.ContentRating;
import io.github.gabryel.videolocadora.model.enums.MediaGenre;
import io.github.gabryel.videolocadora.model.enums.MediaType;
import io.github.gabryel.videolocadora.model.mapper.movie.MovieMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieMapperTest {

    private final MovieMapper movieMapper = Mappers.getMapper(MovieMapper.class);

    @Test
    void toDetailPage_ShouldConvertPopulatedPage() {
        MovieEntity movie = new MovieEntity();
        movie.setId(1L);
        movie.setTitle("O Senhor dos Anéis");
        movie.setContentRating(ContentRating.FREE);
        movie.setInternalCode(UUID.randomUUID());
        movie.setPrice(19.90);
        movie.setAvailable(true);
        movie.setType(MediaType.DVD);
        movie.setGenre(MediaGenre.FANTASY);

        var page = new PageImpl<>(List.of(movie), PageRequest.of(0, 10), 1);

        var result = movieMapper.toDetailPage(page);

        assertNotNull(result);
        assertEquals(1, result.content().size());
        assertEquals("O Senhor dos Anéis", result.content().get(0).title());
        assertEquals(0, result.pageNumber());
        assertEquals(10, result.pageSize());
        assertEquals(1, result.totalElements());
        assertEquals(1, result.totalPages());
        assertTrue(result.isFirst());
        assertTrue(result.isLast());
    }

    @Test
    void toDetailPage_ShouldReturnEmptyPage_WhenPageIsNull() {
        var result = movieMapper.toDetailPage(null);

        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(0, result.totalElements());
        assertTrue(result.isFirst());
        assertTrue(result.isLast());
    }

    @Test
    void toDetailPage_ShouldReturnEmptyPage_WhenPageIsEmpty() {
        var page = new PageImpl<MovieEntity>(List.of(), PageRequest.of(0, 10), 0);

        var result = movieMapper.toDetailPage(page);

        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(0, result.totalElements());
    }

}
