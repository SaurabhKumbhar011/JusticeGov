package com.example.demo.dto;

import com.example.demo.model.enums.ComplianceResult;
import com.example.demo.model.enums.ComplianceType;
import lombok.Data;
import java.time.LocalDate;


@Data
public class ComplianceRecordRequest {
    private Long entityId; 
    private ComplianceType type; 
    private ComplianceResult result; 
    private LocalDate date; 
    private String notes; 
}