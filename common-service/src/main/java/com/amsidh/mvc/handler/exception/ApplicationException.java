package com.amsidh.mvc.handler.exception;

public class ApplicationException extends RuntimeException {
    public ApplicationException(String errorMessage) {
        super(errorMessage);
    }
}
