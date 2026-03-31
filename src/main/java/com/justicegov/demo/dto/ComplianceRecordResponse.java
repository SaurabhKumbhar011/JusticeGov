package com.justicegov.demo.dto;

import lombok.Data;
import java.time.LocalDate;

import com.justicegov.demo.model.enums.*;

@Data
public class ComplianceRecordResponse {
    private Long complianceID;
    private Long entityId;
    private ComplianceType type;
    private ComplianceResult result;
    private LocalDate date;
    private String notes;
    
  
    private Boolean isResolved; 
    private String correctiveActionNotes;
}