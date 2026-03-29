package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.enums.JudgementStatus;

import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JudgementPatchRequest {
	private String summary;
	@PastOrPresent(message = "Judgement date cannot be in the future")
	private LocalDate date;
	private JudgementStatus status;
}