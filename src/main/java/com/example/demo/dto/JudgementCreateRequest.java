package com.example.demo.dto;

import com.example.demo.model.enums.JudgementStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

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