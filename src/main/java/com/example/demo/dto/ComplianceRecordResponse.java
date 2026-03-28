package com.example.demo.dto;

import com.example.demo.model.enums.*;
import lombok.Data;
import java.time.LocalDate;

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