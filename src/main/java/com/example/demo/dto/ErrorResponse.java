package com.example.demo.dto;

<<<<<<< HEAD
import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class ErrorResponse {

	private OffsetDateTime timestamp;
	private int status;
	private String error;
	private String message;
	private String path;
=======
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private String module;  // Replaces 'details' from your first DTO
    private int status;
>>>>>>> origin/Vedasri
}