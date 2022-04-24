package com.amsidh.mvc.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDetails implements Serializable {

  private HttpStatus status;
  private String code;
  private String message;
  private String reason;
  private List<ApiValidationError> fieldErrors;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyyy hh:mm:ss")
  private LocalDateTime timestamp;
}
