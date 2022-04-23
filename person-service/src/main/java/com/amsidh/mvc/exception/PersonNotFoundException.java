package com.amsidh.mvc.exception;

public class PersonNotFoundException extends RuntimeException {
    private String errorMessage;

    public PersonNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
