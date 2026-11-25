package io.github.gabryel.bff.client;

import io.github.gabryel.bff.client.support.BackendResponseErrorHandler;
import io.github.gabryel.bff.dto.customer.CustomerCreateRequestDTO;
import io.github.gabryel.bff.dto.customer.CustomerSummaryDTO;
import io.github.gabryel.bff.dto.customer.CustomerUpdateRequestDTO;
import io.github.gabryel.bff.dto.page.PageResultDTO;
import io.github.gabryel.bff.dto.page.ResourceEnvelopeDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CustomerClient {

    private final WebClient backendWebClient;
    private final BackendResponseErrorHandler errorHandler;

    public CustomerClient(WebClient backendWebClient, BackendResponseErrorHandler errorHandler) {
        this.backendWebClient = backendWebClient;
        this.errorHandler = errorHandler;
    }

    public PageResultDTO<CustomerSummaryDTO> findAll(Pageable pageable) {
        return backendWebClient.get()
                .uri(uriBuilder -> uriBuilder.path("/customer")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .bodyToMono(new ParameterizedTypeReference<PageResultDTO<CustomerSummaryDTO>>() {
                })
                .block();
    }

    public CustomerSummaryDTO findById(Long id) {
        return backendWebClient.get()
                .uri("/customer/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .bodyToMono(new ParameterizedTypeReference<ResourceEnvelopeDTO<CustomerSummaryDTO>>() {
                })
                .map(ResourceEnvelopeDTO::data)
                .block();
    }

    public CustomerSummaryDTO findByCpf(String cpf) {
        return backendWebClient.get()
                .uri("/customer/cpf/{cpf}", cpf)
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .bodyToMono(new ParameterizedTypeReference<ResourceEnvelopeDTO<CustomerSummaryDTO>>() {
                })
                .map(ResourceEnvelopeDTO::data)
                .block();
    }

    public void create(CustomerCreateRequestDTO request) {
        backendWebClient.post()
                .uri("/customer")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .toBodilessEntity()
                .block();
    }

    public CustomerSummaryDTO update(Long id, CustomerUpdateRequestDTO request) {
        return backendWebClient.put()
                .uri("/customer/{id}", id)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .bodyToMono(new ParameterizedTypeReference<ResourceEnvelopeDTO<CustomerSummaryDTO>>() {
                })
                .map(ResourceEnvelopeDTO::data)
                .block();
    }

    public void delete(Long id) {
        backendWebClient.delete()
                .uri("/customer/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .toBodilessEntity()
                .block();
    }

}
