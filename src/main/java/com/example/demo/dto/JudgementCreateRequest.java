package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.enums.JudgementStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JudgementCreateRequest {

	@NotNull(message = "judgeId is required")
	private Long judgeId;

	@NotBlank(message = "summary is required")
	private String summary;

	@NotNull(message = "date is required")
	@PastOrPresent(message = "Judgement date cannot be in the future")
	private LocalDate date;

	private JudgementStatus status;
}