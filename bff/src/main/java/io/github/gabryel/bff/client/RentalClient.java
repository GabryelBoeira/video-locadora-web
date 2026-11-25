package io.github.gabryel.bff.client;

import io.github.gabryel.bff.client.support.BackendResponseErrorHandler;
import io.github.gabryel.bff.dto.page.PageResultDTO;
import io.github.gabryel.bff.dto.page.ResourceEnvelopeDTO;
import io.github.gabryel.bff.dto.rental.RentalCreateRequestDTO;
import io.github.gabryel.bff.dto.rental.RentalReturnRequestDTO;
import io.github.gabryel.bff.dto.rental.RentalSummaryDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class RentalClient {

    private final WebClient backendWebClient;
    private final BackendResponseErrorHandler errorHandler;

    public RentalClient(WebClient backendWebClient, BackendResponseErrorHandler errorHandler) {
        this.backendWebClient = backendWebClient;
        this.errorHandler = errorHandler;
    }

    public PageResultDTO<RentalSummaryDTO> findAll(Pageable pageable) {
        return backendWebClient.get()
                .uri(uriBuilder -> uriBuilder.path("/rentals")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .bodyToMono(new ParameterizedTypeReference<PageResultDTO<RentalSummaryDTO>>() {
                })
                .block();
    }

    public RentalSummaryDTO findById(Long id) {
        return backendWebClient.get()
                .uri("/rentals/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .bodyToMono(new ParameterizedTypeReference<ResourceEnvelopeDTO<RentalSummaryDTO>>() {
                })
                .map(ResourceEnvelopeDTO::data)
                .block();
    }

    public RentalSummaryDTO create(RentalCreateRequestDTO request) {
        return backendWebClient.post()
                .uri("/rentals")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .bodyToMono(new ParameterizedTypeReference<ResourceEnvelopeDTO<RentalSummaryDTO>>() {
                })
                .map(ResourceEnvelopeDTO::data)
                .block();
    }

    public void returnRental(Long id, RentalReturnRequestDTO request) {
        backendWebClient.post()
                .uri("/rentals/{id}/return", id)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .toBodilessEntity()
                .block();
    }

    public void returnItem(Long id, Long itemId, RentalReturnRequestDTO request) {
        backendWebClient.post()
                .uri("/rentals/{id}/items/{itemId}/return", id, itemId)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .toBodilessEntity()
                .block();
    }

}
