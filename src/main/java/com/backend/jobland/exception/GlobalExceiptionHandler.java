package com.backend.jobland.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.backend.jobland.dto.ApiResponse;
import com.backend.jobland.lib.AppErrors;

@RestControllerAdvice
public class GlobalExceiptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        int statusCode = HttpStatus.BAD_REQUEST.value();

        if (ex instanceof ErrorResponse errorResponse) {
            statusCode = errorResponse.getStatusCode().value();
        }

        return ResponseEntity.status(statusCode).body(ApiResponse.error(statusCode, errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        int errCode = HttpStatus.BAD_REQUEST.value();
        String errMessage = ex.getMessage() != null ? ex.getMessage() : AppErrors.SOMETHING_WENT_WRONG;
        return ResponseEntity.status(errCode).body(ApiResponse.error(errCode, errMessage));
    }

}
