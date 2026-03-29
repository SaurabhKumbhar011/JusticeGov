package com.example.demo.dto;

import com.example.demo.model.enums.Gender;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter 
@Setter 
public class CitizenProfileReq {
    private String name;
    private LocalDate dob;
    private Gender gender;
    private String address;
    private String contactInfo;
}