package io.github.gabryel.videolocadora.exception;

import java.io.Serializable;

public class CustomerException extends Exception implements Serializable {

    private static final long serialVersionUID = -8583570553972579284L;

    public CustomerException(String message) {
        super(message);
    }
}