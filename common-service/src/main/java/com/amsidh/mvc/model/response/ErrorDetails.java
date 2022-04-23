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
public class ErrorDetails implements Serializable {
  private String errorCode;
  private String errorMessage;
}
