package com.amsidh.mvc.handler.exception;

public class AccountDataNotFoundException extends DataNotFoundException {
	public AccountDataNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
