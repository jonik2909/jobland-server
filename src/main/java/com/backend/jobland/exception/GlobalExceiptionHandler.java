package com.backend.jobland.exception;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.backend.jobland.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceiptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        int statusCode = 400;

        if (ex instanceof ErrorResponse errorResponse) {
            statusCode = errorResponse.getStatusCode().value();
        }

        return ResponseEntity.status(statusCode).body(ApiResponse.error(statusCode, errorMessage));
    }

}
