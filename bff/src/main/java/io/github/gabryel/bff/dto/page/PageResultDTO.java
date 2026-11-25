package io.github.gabryel.bff.dto.page;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Espelha o formato de resposta paginada do video-locadora (ResourceCollection),
 * ignorando os campos "_links" (nivel raiz e por item) que o BFF nao repassa ao cliente.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PageResultDTO<T>(
        List<T> data,
        PageMetaDTO meta
) {
}
