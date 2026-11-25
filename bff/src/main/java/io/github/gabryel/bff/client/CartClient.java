package io.github.gabryel.bff.client;

import io.github.gabryel.bff.client.support.BackendResponseErrorHandler;
import io.github.gabryel.bff.dto.cart.CartAddItemRequestDTO;
import io.github.gabryel.bff.dto.cart.CartCreateRequestDTO;
import io.github.gabryel.bff.dto.cart.CartSummaryDTO;
import io.github.gabryel.bff.dto.page.ResourceEnvelopeDTO;
import io.github.gabryel.bff.exception.BackendApiException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Component
public class CartClient {

    private final WebClient backendWebClient;
    private final BackendResponseErrorHandler errorHandler;

    public CartClient(WebClient backendWebClient, BackendResponseErrorHandler errorHandler) {
        this.backendWebClient = backendWebClient;
        this.errorHandler = errorHandler;
    }

    public CartSummaryDTO findBySessionId(String sessionId) {
        return backendWebClient.get()
                .uri("/cart/{sessionId}", sessionId)
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .bodyToMono(new ParameterizedTypeReference<ResourceEnvelopeDTO<CartSummaryDTO>>() {
                })
                .map(ResourceEnvelopeDTO::data)
                .block();
    }

    public CartSummaryDTO create(CartCreateRequestDTO request) {
        return backendWebClient.post()
                .uri("/cart")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .bodyToMono(new ParameterizedTypeReference<ResourceEnvelopeDTO<CartSummaryDTO>>() {
                })
                .map(ResourceEnvelopeDTO::data)
                .block();
    }

    public CartSummaryDTO addItem(String sessionId, CartAddItemRequestDTO request) {
        return backendWebClient.put()
                .uri("/cart/{sessionId}/items", sessionId)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .bodyToMono(new ParameterizedTypeReference<ResourceEnvelopeDTO<CartSummaryDTO>>() {
                })
                .map(ResourceEnvelopeDTO::data)
                .block();
    }

    public CartSummaryDTO removeItem(String sessionId, Long itemId) {
        return backendWebClient.delete()
                .uri("/cart/{sessionId}/items/{itemId}", sessionId, itemId)
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .bodyToMono(new ParameterizedTypeReference<ResourceEnvelopeDTO<CartSummaryDTO>>() {
                })
                .map(ResourceEnvelopeDTO::data)
                .block();
    }

    public Long checkout(String sessionId) {
        URI location = backendWebClient.post()
                .uri("/cart/{sessionId}/checkout", sessionId)
                .retrieve()
                .onStatus(HttpStatusCode::isError, errorHandler::handle)
                .toBodilessEntity()
                .block()
                .getHeaders()
                .getLocation();

        if (location == null) {
            throw new BackendApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "O servico video-locadora nao retornou o identificador do aluguel gerado no checkout");
        }
        String path = location.getPath();
        return Long.valueOf(path.substring(path.lastIndexOf('/') + 1));
    }

}
