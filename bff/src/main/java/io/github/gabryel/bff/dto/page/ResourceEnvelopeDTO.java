package io.github.gabryel.bff.dto.page;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Espelha o envelope de recurso unico do video-locadora (Resource<T>), ignorando
 * o campo "links"/"_links" que o BFF nao repassa ao cliente.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ResourceEnvelopeDTO<T>(
        T data
) {
}
