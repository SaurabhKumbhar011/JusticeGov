package com.justicegov.demo.dto;

import lombok.*;

import java.time.LocalDate;

import com.justicegov.demo.model.enums.JudgementStatus;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class JudgementResponse {
    private Long id;
    private Long caseId;
    private Long judgeId;
    //private String judgeName;
    private String summary;
    private LocalDate date;
    private JudgementStatus status;
}
