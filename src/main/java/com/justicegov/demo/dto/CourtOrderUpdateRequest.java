package com.justicegov.demo.dto;

import java.time.LocalDate;

import com.justicegov.demo.model.enums.OrderStatus;

import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourtOrderUpdateRequest {
	private String description;
	@PastOrPresent(message = "Court order date cannot be in the future")
	private LocalDate date;
	private OrderStatus status;
}