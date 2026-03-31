package com.example.demo.service;
import com.example.demo.dto.ReportRequestDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Report;
import com.example.demo.repository.CaseRepository;
import com.example.demo.repository.HearingRepository;
import com.example.demo.repository.JudgmentRepository;
import com.example.demo.repository.ComplianceRepository;
import com.example.demo.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
 
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
 
    private static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);
 
    private final CaseRepository caseRepo;
    private final HearingRepository hearingRepo;
    private final JudgmentRepository judgmentRepo;
    private final ComplianceRepository complianceRepo;
    private final ReportRepository reportRepo;
 
    @Override
    public Report generateReport(ReportRequestDTO req) {
 
        log.info("Entered generateReport method");
 
        if (req == null) {
            log.error("Report request is null");
            throw new IllegalArgumentException("Request cannot be null");
        }
 
        Map<String, Object> metrics = new HashMap<>();
 
        LocalDate start = req.getStartDate();
        LocalDate end = req.getEndDate();
        String scope = req.getScope();
 
        log.info("Generating report for scope: {}, startDate: {}, endDate: {}", scope, start, end);
 
        switch (scope.toUpperCase()) {
 
            case "CASE":
                log.info("Processing CASE metrics");
                metrics.put("totalCases", caseRepo.count());
                metrics.put("openCases", caseRepo.countByStatus("OPEN"));
                metrics.put("closedCases", caseRepo.countByStatus("CLOSED"));
                break;
 
            case "HEARING":
                log.info("Processing HEARING metrics");
                metrics.put("totalHearings", hearingRepo.count());
                metrics.put("pendingHearings", hearingRepo.countByStatus("PENDING"));
                break;
 
            case "JUDGMENT":
                log.info("Processing JUDGMENT metrics");
                metrics.put("totalJudgments", judgmentRepo.count());
                metrics.put("approvedJudgments", judgmentRepo.countByStatus("APPROVED"));
                break;
 
            case "ALL":
                log.info("Processing ALL metrics");
 
                // CASE
                metrics.put("totalCases", caseRepo.count());
                metrics.put("openCases", caseRepo.countByStatus("OPEN"));
                metrics.put("closedCases", caseRepo.countByStatus("CLOSED"));
 
                // HEARING
                metrics.put("totalHearings", hearingRepo.count());
                metrics.put("pendingHearings", hearingRepo.countByStatus("PENDING"));
 
                // JUDGMENT
                metrics.put("totalJudgments", judgmentRepo.count());
                metrics.put("approvedJudgments", judgmentRepo.countByStatus("COMPLETED"));
 
                // COMPLIANCE
                long completed = complianceRepo.countByStatus("COMPLETED");
                long total = complianceRepo.count();
 
                double rate = total == 0 ? 0 : (completed * 100.0 / total);
                metrics.put("complianceRate", rate);
 
                log.info("Compliance rate calculated: {}%", rate);
                break;
 
            default:
                log.error("Invalid scope value received: {}", scope);
                throw new IllegalArgumentException("Invalid scope value");
        }
 
        Report report = new Report();
        report.setScope(scope);
        report.setMetrics(metrics.toString());
        report.setStartDate(start);
        report.setEndDate(end);
        report.setGeneratedDate(LocalDate.now());
 
        Report saved = reportRepo.save(report);
 
        log.info("Report generated successfully with ID: {}", saved.getReportId());
 
        return saved;
    }
 
    @Override
    public Report getReportById(Long reportId) {
 
        log.info("Fetching report with ID: {}", reportId);
     // Check if ID is null
        if (reportId == null) {
            log.error("Report ID is null");
            throw new IllegalArgumentException("Report ID cannot be null");
        }
 
        Report report = reportRepo.findById(reportId)
                .orElseThrow(() -> {
                    log.error("Report not found with ID: {}", reportId);
                    return new ResourceNotFoundException("Report not found with id: " + reportId);
                });
 
        log.info("Report fetched successfully with ID: {}", reportId);
        // Fetch from DB
        return report;
    }
}
    
	
       
     
       
       
     

 