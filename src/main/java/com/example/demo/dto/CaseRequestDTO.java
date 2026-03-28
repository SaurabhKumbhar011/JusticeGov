package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
public class CaseRequestDTO {
    private Long citizenId;
    private Long lawyerId; // Optional
    private String title;
    private String description;
    private LocalDate filedDate;
}