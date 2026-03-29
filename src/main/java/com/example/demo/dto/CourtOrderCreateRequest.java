package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.enums.OrderStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourtOrderCreateRequest {

	@NotNull(message = "caseId is required")
	private Long caseId;

	@NotNull(message = "judgeId is required")
	private Long judgeId;

	@NotBlank(message = "description is required")
	private String description;

	@NotNull(message = "date is required")
	@PastOrPresent(message = "Court order date cannot be in the future")
	private LocalDate date;

	private OrderStatus status;
}