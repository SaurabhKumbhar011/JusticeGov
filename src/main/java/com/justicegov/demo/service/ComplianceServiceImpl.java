package com.justicegov.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.justicegov.demo.dto.ComplianceRecordRequest;
import com.justicegov.demo.dto.ComplianceRecordResponse;
import com.justicegov.demo.model.ComplianceRecord;
import com.justicegov.demo.model.enums.ComplianceResult;
import com.justicegov.demo.repository.ComplianceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional; // Added import for Optional

@Service
@RequiredArgsConstructor
public class ComplianceServiceImpl implements ComplianceService {

    private final ComplianceRepository repo;

    // 1. ADDED: Retrieve a specific record for policy adherence review [cite: 78, 79]
    @Override
    public ComplianceRecordResponse findRecordById(Long id) {
        Optional<ComplianceRecord> opt = repo.findById(id);
        
        if (opt.isPresent()) {
            return mapToResponse(opt.get());
        } else {
            // Descriptive error for the Judiciary Governance system [cite: 6]
            throw new RuntimeException("Compliance Record ID " + id + " not found in the system.");
        }
    }

    @Override
    public ComplianceRecordResponse createRecord(ComplianceRecordRequest req) {
        String prefix = "";
        // Requirement: Automated alerting for non-compliance [cite: 93]
        if (req.getResult() == ComplianceResult.NON_COMPLIANT) {
            prefix = "POLICY ADHERENCE ALERT: ";
        }

        ComplianceRecord record = ComplianceRecord.builder()
                .entityId(req.getEntityId())
                .type(req.getType())
                .result(req.getResult())
                .date(req.getDate())
                .notes(prefix + req.getNotes())
                .isResolved(false)
                .build();

        ComplianceRecord saved = repo.save(record);
        return mapToResponse(saved);
    }

    @Override
    public ComplianceRecordResponse resolveViolation(Long id, String notes) {
        // Traditional null check instead of lambda [cite: 139]
        ComplianceRecord record = repo.findById(id).orElse(null);
        
        if (record == null) {
            throw new RuntimeException("Compliance Record ID " + id + " not found.");
        }

        // Requirement: Track corrective actions for policy adherence [cite: 78, 133]
        record.setIsResolved(true);
        record.setCorrectiveActionNotes(notes);
        record.setResult(ComplianceResult.COMPLIANT);
        
        ComplianceRecord updated = repo.save(record);
        return mapToResponse(updated);
    }

    @Override
    public List<ComplianceRecordResponse> findAllRecords() {
        List<ComplianceRecord> allRecords = repo.findAll();
        List<ComplianceRecordResponse> responseList = new ArrayList<>();
        
        // Standard loop to satisfy non-lambda preference [cite: 139]
        for (ComplianceRecord record : allRecords) {
            responseList.add(mapToResponse(record));
        }
        return responseList;
    }

    private ComplianceRecordResponse mapToResponse(ComplianceRecord e) {
        ComplianceRecordResponse res = new ComplianceRecordResponse();
        res.setComplianceID(e.getId());
        res.setEntityId(e.getEntityId()); // Maps to Case/Hearing/Judgment/Research [cite: 82]
        res.setType(e.getType());
        res.setResult(e.getResult());
        res.setDate(e.getDate());
        res.setNotes(e.getNotes());
        res.setIsResolved(e.getIsResolved());
        res.setCorrectiveActionNotes(e.getCorrectiveActionNotes());
        return res;
    }
}