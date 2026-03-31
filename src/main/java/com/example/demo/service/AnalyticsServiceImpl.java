package com.example.demo.service;

import com.example.demo.dto.AnalyticsResponseDTO;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
 
@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {
 
    private final CaseRepository caseRepo;
    private final HearingRepository hearingRepo;
    private final JudgmentRepository judgmentRepo;
    private final ComplianceRepository complianceRepo;
 
    @Override
    public AnalyticsResponseDTO getDashboardAnalytics() {
 
        long totalCases = caseRepo.count();
        long openCases = caseRepo.countByStatus("OPEN");
        long closedCases = caseRepo.countByStatus("CLOSED");
 
        long totalHearings = hearingRepo.count();
        long pendingHearings = hearingRepo.countByStatus("PENDING");
 
        long totalJudgments = judgmentRepo.count();
        long approvedJudgments = judgmentRepo.countByStatus("APPROVED");
 
        long completed = complianceRepo.countByStatus("COMPLETED");
        long total = complianceRepo.count();
 
        
        
        double complianceRate = total == 0 ? 0 :
                (completed * 100.0 / total);
 
        return AnalyticsResponseDTO.builder()
                .totalCases(totalCases)
                .openCases(openCases)
                .closedCases(closedCases)
                .totalHearings(totalHearings)
                .pendingHearings(pendingHearings)
                .totalJudgments(totalJudgments)
                .approvedJudgments(approvedJudgments)
                .complianceRate(complianceRate)
                .build();
    }
}
 