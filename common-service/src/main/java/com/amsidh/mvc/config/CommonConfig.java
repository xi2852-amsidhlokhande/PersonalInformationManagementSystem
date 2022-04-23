package com.amsidh.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Configuration
public class CommonConfig {

	@Bean
	public ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper;
	}

	@Bean
	public Gson getGson() {
		Gson gson = new Gson();
		return gson;
	}
}
