package com.justicegov.demo.service;

import java.util.List;

import com.justicegov.demo.dto.AuditRequest;
import com.justicegov.demo.dto.AuditResponse;

public interface AuditService {
    AuditResponse createAudit(AuditRequest request);
    List<AuditResponse> getAllAudits();
    AuditResponse updateAudit(Long id, AuditRequest details);
    
    // ADD THIS LINE TO FIX THE ERROR
    AuditResponse getAuditById(Long id); 
}