package com.example.demo.service;

import com.example.demo.dto.AuditRequest;
import com.example.demo.dto.AuditResponse;
import java.util.List;

public interface AuditService {
    AuditResponse createAudit(AuditRequest request);
    List<AuditResponse> getAllAudits();
    AuditResponse updateAudit(Long id, AuditRequest details);
    
    // ADD THIS LINE TO FIX THE ERROR
    AuditResponse getAuditById(Long id); 
}