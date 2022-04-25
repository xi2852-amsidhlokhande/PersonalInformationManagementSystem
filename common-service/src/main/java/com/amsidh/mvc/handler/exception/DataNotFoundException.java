package com.amsidh.mvc.handler.exception;

public class DataNotFoundException extends ApplicationException {
	private String errorMessage;

	public DataNotFoundException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
}
