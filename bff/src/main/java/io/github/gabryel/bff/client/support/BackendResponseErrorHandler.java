package io.github.gabryel.bff.client.support;

import io.github.gabryel.bff.exception.BackendApiException;
import io.github.gabryel.bff.exception.UpstreamUnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

@Component
public class BackendResponseErrorHandler {

    public Mono<? extends Throwable> handle(ClientResponse response) {
        HttpStatusCode status = response.statusCode();

        if (status == HttpStatus.UNAUTHORIZED || status == HttpStatus.FORBIDDEN) {
            return Mono.just(new UpstreamUnauthorizedException(status));
        }

        return response.bodyToMono(String.class)
                .defaultIfEmpty("")
                .map(body -> new BackendApiException(
                        status,
                        body.isBlank() ? "Erro ao comunicar com o servico video-locadora" : body));
    }

}
