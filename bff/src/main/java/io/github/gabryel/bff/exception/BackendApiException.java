package io.github.gabryel.bff.exception;

import org.springframework.http.HttpStatusCode;

public class BackendApiException extends RuntimeException {

    private final HttpStatusCode status;

    public BackendApiException(HttpStatusCode status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatusCode getStatus() {
        return status;
    }

}
