package io.github.gabryel.bff.client;

import io.github.gabryel.bff.client.support.BackendResponseErrorHandler;
import io.github.gabryel.bff.dto.movie.MovieCreateRequestDTO;
import io.github.gabryel.bff.dto.movie.MovieSummaryDTO;
import io.github.gabryel.bff.dto.page.PageResultDTO;
import io.github.gabryel.bff.dto.page.ResourceEnvelopeDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MovieClient {

    private final WebClient backendWebClient;
    private final BackendResponseErrorHandler errorHandler;

    public MovieClient(WebClient backendWebClient, BackendResponseErrorHandler errorHandler) {
        this.backendWebClient = backendWebClient;
        this.errorHandler = errorHandler;
    }

    public PageResultDTO<MovieSummaryDTO> findAll(Pageable pageable) {
        return backendWebClient.get()
                .uri(uriBuilder -> uriBuilder.path("/filmes")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .bodyToMono(new ParameterizedTypeReference<PageResultDTO<MovieSummaryDTO>>() {
                })
                .block();
    }

    public MovieSummaryDTO findById(Long id) {
        return backendWebClient.get()
                .uri("/filmes/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .bodyToMono(new ParameterizedTypeReference<ResourceEnvelopeDTO<MovieSummaryDTO>>() {
                })
                .map(ResourceEnvelopeDTO::data)
                .block();
    }

    public MovieSummaryDTO create(MovieCreateRequestDTO request) {
        return backendWebClient.post()
                .uri("/filmes")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .bodyToMono(MovieSummaryDTO.class)
                .block();
    }

    public void delete(Long id) {
        backendWebClient.delete()
                .uri("/filmes/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .toBodilessEntity()
                .block();
    }

}
