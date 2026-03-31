package com.justicegov.demo.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CaseResponseDTO {
    private Long id;
    private Long citizenId;
    private Long lawyerId;
    private String title;
    private String description;
    private LocalDate filedDate;
    private String status;
    
}