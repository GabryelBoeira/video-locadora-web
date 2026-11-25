package io.github.gabryel.bff.controller.advice;

import io.github.gabryel.bff.exception.BackendApiException;
import io.github.gabryel.bff.exception.UpstreamUnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.ClientAuthorizationRequiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.util.Map;

@RestControllerAdvice
public class BffControllerAdvice {

    @ExceptionHandler(ClientAuthorizationRequiredException.class)
    public ResponseEntity<Object> handleClientAuthorizationRequired(ClientAuthorizationRequiredException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "message", "Sessao expirada, autentique-se novamente",
                        "reauthenticateUrl", "/oauth2/authorization/keycloak"));
    }

    @ExceptionHandler(UpstreamUnauthorizedException.class)
    public ResponseEntity<Object> handleUpstreamUnauthorized(UpstreamUnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(BackendApiException.class)
    public ResponseEntity<Object> handleBackendApiException(BackendApiException ex) {
        return ResponseEntity.status(ex.getStatus()).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<Object> handleWebClientRequestException(WebClientRequestException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("message", "O servico video-locadora esta indisponivel no momento"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Erro inesperado no BFF"));
    }

}
