package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDate;


@Data
public class AuditRequest {
    private Long officerId;  
    private String scope; 
    private String findings; 
    private LocalDate date;  
    private String status; 
}