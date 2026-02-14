package com.cahiertexte.common.exception;

/**
 * Exception levée lorsque la requête est invalide
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
