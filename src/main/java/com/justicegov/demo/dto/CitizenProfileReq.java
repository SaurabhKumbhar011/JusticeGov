package com.justicegov.demo.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

import com.justicegov.demo.model.enums.Gender;

@Getter 
@Setter 
public class CitizenProfileReq {
    private String name;
    private LocalDate dob;
    private Gender gender;
    private String address;
    private String contactInfo;
}