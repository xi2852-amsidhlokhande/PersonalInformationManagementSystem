package com.amsidh.mvc.handler.exception;

public class PersonDataNotFoundException extends DataNotFoundException {
    private String errorKey;
    private Integer personId;

    public PersonDataNotFoundException(String errorKey, Integer personId) {
        super(errorKey);
        this.errorKey = errorKey;
        this.personId = personId;
    }
}
