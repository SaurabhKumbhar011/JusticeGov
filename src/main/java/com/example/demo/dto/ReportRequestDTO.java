package com.example.demo.dto;


import lombok.Data;
 
import java.time.LocalDate;


@Data
public class ReportRequestDTO {
 
    private String scope;
    private LocalDate startDate;
    private LocalDate endDate;
}
 