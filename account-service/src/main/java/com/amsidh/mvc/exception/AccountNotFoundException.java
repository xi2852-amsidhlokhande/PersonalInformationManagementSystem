package com.amsidh.mvc.exception;

public class AccountNotFoundException extends RuntimeException {
    private String errorMessage;

    public AccountNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
