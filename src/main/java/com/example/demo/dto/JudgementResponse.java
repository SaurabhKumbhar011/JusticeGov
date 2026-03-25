package com.example.demo.dto;

import com.example.demo.model.enums.JudgementStatus;
import lombok.*;

import java.time.LocalDate;

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
