package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnalyticsResponseDTO {

    private long totalCases;
    private long openCases;
    private long closedCases;
 
    private long totalHearings;
    private long pendingHearings;
 
    private long totalJudgments;
    private long approvedJudgments;
 
    private double complianceRate;
}
 