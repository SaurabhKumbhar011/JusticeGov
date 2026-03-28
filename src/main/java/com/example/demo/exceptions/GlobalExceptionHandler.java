package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Handles "Not Found" or Business Rule violations (RuntimeException)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(RuntimeException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("module", "JUDICIARY_GOVERNANCE_SYSTEM"); // General module name
        body.put("status", HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // 2. Handles bad input data or Enum conversion errors (IllegalArgumentException)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Validation Error: " + ex.getMessage());
        body.put("module", "JUDICIARY_GOVERNANCE_SYSTEM");
        body.put("status", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // 3. Catch-all for any other unexpected errors (General Exception)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "An unexpected error occurred: " + ex.getMessage());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}