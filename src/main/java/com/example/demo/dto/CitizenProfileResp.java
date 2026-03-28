package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CitizenProfileResp {
    private Long id; 
    private String name; 
    private String status; 
    private String address;     
    private String contactInfo; 
    private LocalDateTime createdDate; 
    private LocalDateTime lastModifiedDate; 
}