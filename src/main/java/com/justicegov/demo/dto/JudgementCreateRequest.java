package com.justicegov.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

import com.justicegov.demo.model.enums.JudgementStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JudgementCreateRequest {

	@NotNull(message = "judgeId is required")
	private Long judgeId;

	@NotBlank(message = "summary is required")
	private String summary;

	@NotNull(message = "date is required")
	private LocalDate date;

	private JudgementStatus status;
}