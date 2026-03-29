package com.example.demo.exceptions;

<<<<<<< HEAD
<<<<<<< HEAD
=======
import java.time.OffsetDateTime;

>>>>>>> origin/Saurabh
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
<<<<<<< HEAD
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
            errors.put(err.getField(), err.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
=======

import com.example.demo.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, HttpServletRequest request) {

		log.warn("NotFoundException: {}", ex.getMessage());

		ErrorResponse error = new ErrorResponse();
		error.setTimestamp(OffsetDateTime.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError("Not Found");
		error.setMessage(ex.getMessage());
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex, HttpServletRequest request) {

		log.warn("BadRequestException: {}", ex.getMessage());

		ErrorResponse error = new ErrorResponse();
		error.setTimestamp(OffsetDateTime.now());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setError("Bad Request");
		error.setMessage(ex.getMessage());
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	// fallback – no stacktrace to client
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {

		log.error("Unexpected error", ex);

		ErrorResponse error = new ErrorResponse();
		error.setTimestamp(OffsetDateTime.now());
		error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setError("Internal Server Error");
		error.setMessage("Something went wrong.");
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		// Take first validation error message
		String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

		ErrorResponse error = new ErrorResponse();
		error.setTimestamp(OffsetDateTime.now());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setError("Bad Request");
		error.setMessage(message);
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
>>>>>>> origin/Saurabh
=======
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
>>>>>>> origin/Vedasri
