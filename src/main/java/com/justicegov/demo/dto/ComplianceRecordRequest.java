package com.justicegov.demo.dto;

import lombok.Data;
import java.time.LocalDate;

import com.justicegov.demo.model.enums.ComplianceResult;
import com.justicegov.demo.model.enums.ComplianceType;


@Data
public class ComplianceRecordRequest {
    private Long entityId; 
    private ComplianceType type; 
    private ComplianceResult result; 
    private LocalDate date; 
    private String notes; 
}