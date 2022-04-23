package com.amsidh.mvc.exception;

public class AssetNotFoundException extends RuntimeException {
    private String errorMessage;

    public AssetNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
