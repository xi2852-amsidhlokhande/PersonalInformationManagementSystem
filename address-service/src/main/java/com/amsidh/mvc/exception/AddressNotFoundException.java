package com.amsidh.mvc.exception;

public class AddressNotFoundException extends RuntimeException {
    private String errorMessage;

    public AddressNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
