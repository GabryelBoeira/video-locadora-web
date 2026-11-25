package io.github.gabryel.bff.exception;

import org.springframework.http.HttpStatusCode;

public class UpstreamUnauthorizedException extends RuntimeException {

    private final HttpStatusCode status;

    public UpstreamUnauthorizedException(HttpStatusCode status) {
        super("Sem permissao para acessar este recurso no video-locadora");
        this.status = status;
    }

    public HttpStatusCode getStatus() {
        return status;
    }

}
