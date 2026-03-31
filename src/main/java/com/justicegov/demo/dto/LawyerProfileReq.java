package com.justicegov.demo.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LawyerProfileReq {
    private String name;         
    private LocalDate dob;       
    private String barId;       
    private String contactInfo; 
}