package com.example.demo.service;

import com.example.demo.dto.ComplianceRecordRequest;
import com.example.demo.dto.ComplianceRecordResponse;
import java.util.List;

public interface ComplianceService {

    // 1. Create a new record (Requirement: Maintain compliance records) [cite: 79]
    ComplianceRecordResponse createRecord(ComplianceRecordRequest request);

    // 2. View all records (Requirement: Adherence to judicial policies) 
    List<ComplianceRecordResponse> findAllRecords();

    // 3. Update a violation (Requirement: Corrective action/Adherence) [cite: 78, 83]
    ComplianceRecordResponse resolveViolation(Long id, String resolutionNotes);

    // 4. ADD THIS: Retrieve a specific record by ID [cite: 82]
    ComplianceRecordResponse findRecordById(Long id);
}