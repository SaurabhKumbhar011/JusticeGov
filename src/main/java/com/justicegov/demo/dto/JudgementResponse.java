package com.justicegov.demo.dto;

import lombok.*;

import java.time.LocalDate;

import com.justicegov.demo.model.enums.JudgementStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JudgementResponse {
	private Long id;
	private Long caseId;
	private Long judgeId;
	private String summary;
	private LocalDate date;
	private JudgementStatus status;
}
