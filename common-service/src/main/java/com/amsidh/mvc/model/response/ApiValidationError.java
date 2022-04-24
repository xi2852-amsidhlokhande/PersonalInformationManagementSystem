package com.amsidh.mvc.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiValidationError implements Serializable {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;
}
