package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.enums.JudgementStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JudgementPatchRequest {
	private String summary;
	private LocalDate date;
	private JudgementStatus status;
}