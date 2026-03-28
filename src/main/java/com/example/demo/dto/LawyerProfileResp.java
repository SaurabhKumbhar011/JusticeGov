package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LawyerProfileResp {
    private Long id;
    private String name;
    private String barId;
    private String status;
    private String contactInfo;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate; // Required for audit visibility
}