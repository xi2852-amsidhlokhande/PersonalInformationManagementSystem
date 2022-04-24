package com.amsidh.mvc.handler;

import com.amsidh.mvc.handler.exception.DataNotFoundException;
import com.amsidh.mvc.model.response.ApiValidationError;
import com.amsidh.mvc.model.response.BaseResponse;
import com.amsidh.mvc.model.response.ErrorDetails;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static com.amsidh.mvc.handler.ErrorCode.ERRORS;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestControllerAdvice
@RequiredArgsConstructor
public class CommonAdviceController extends ResponseEntityExceptionHandler {

    private final Gson gson;

    @ExceptionHandler(value = {DataNotFoundException.class})
    @ResponseStatus(OK)
    public ResponseEntity<BaseResponse> handleDataNotFoundException(DataNotFoundException dataNotFoundException) {
        return ResponseEntity.ok(BaseResponse.builder().errorDetails(ERRORS.get(dataNotFoundException.getMessage())).build());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest().body(BaseResponse.builder().errorDetails(ErrorDetails.builder()
                .status(BAD_REQUEST)
                .message(ex.getLocalizedMessage())
                .code("0003")
                .build()));
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ApiValidationError> fieldErrors = ex.getBindingResult()
                .getFieldErrors().stream()
                .map(fieldError -> ApiValidationError.builder().field(fieldError.getField()).message(fieldError.getDefaultMessage()).object(fieldError.getObjectName()).rejectedValue(fieldError.getRejectedValue()).build())
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(BaseResponse.builder().errorDetails(ErrorDetails.builder()
                .status(OK)
                .fieldErrors(fieldErrors)
                .code("0003")
                .build()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ApiValidationError> fieldErrors = ex.getBindingResult()
                .getFieldErrors().stream()
                .map(fieldError -> ApiValidationError.builder().field(fieldError.getField()).message(fieldError.getDefaultMessage()).object(fieldError.getObjectName()).rejectedValue(fieldError.getRejectedValue()).build()).collect(Collectors.toList());
        return ResponseEntity.ok().body(BaseResponse.builder().errorDetails(ErrorDetails.builder()
                .status(OK)
                .fieldErrors(fieldErrors)
                .code("0003")
                .build()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest().body(BaseResponse.builder().errorDetails(ErrorDetails.builder()
                .status(BAD_REQUEST)
                .message(ex.getMessage())
                .code("0002")
                .build()));
    }
}
