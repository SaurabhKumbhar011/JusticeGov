package com.example.demo.dto;

import com.example.demo.model.enums.JudgementStatus;
import lombok.*;

import java.time.LocalDate;

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
