package com.example.urlshortener.common;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations().stream()
            .map(violation ->
                    violation.getPropertyPath() + ": " + violation.getMessage()
            )
            .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            Map.of(
                "status", "error",
                "message", "Validation failed",
                "errors", errors
            )
        );
    }
}