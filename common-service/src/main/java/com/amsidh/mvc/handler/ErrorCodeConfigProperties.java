package com.amsidh.mvc.handler;

import com.amsidh.mvc.model.response.ErrorDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "application")
public class ErrorCodeConfigProperties {

    public static Map<String, ErrorDetails> ERRORS = new HashMap<>();
    public void setErrors(Map<String, ErrorDetails> errors) {
        ErrorCodeConfigProperties.ERRORS = errors;
    }
}
