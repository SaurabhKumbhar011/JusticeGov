package com.justicegov.demo.dto;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class ErrorResponse {

	private OffsetDateTime timestamp;
	private int status;
	private String error;
	private String message;
	private String path;
}