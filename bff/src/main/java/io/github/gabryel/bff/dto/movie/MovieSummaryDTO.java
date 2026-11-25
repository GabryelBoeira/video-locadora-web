package io.github.gabryel.bff.dto.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MovieSummaryDTO(
        Long id,
        String title,
        String contentRating,
        UUID internalCode,
        Double price,
        boolean available,
        String type,
        String genre
) {
}
