package com.amsidh.mvc.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.amsidh.mvc.model.response.ErrorDetails;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "application")
public class ErrorCode {

	public static Map<String, ErrorDetails> ERRORS = new HashMap<>();

	public void setErrors(Map<String, ErrorDetails> errors) {
		ErrorCode.ERRORS = errors;
	}
}
