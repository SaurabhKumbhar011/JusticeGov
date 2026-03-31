package com.justicegov.demo.dto;

import lombok.Data;

@Data // This generates the getStatus() and setStatus() methods automatically
public class ProfileUpdateReq {
    private String address;
    private String contactInfo;
    private String status; // ADD THIS LINE TO FIX THE ERROR
}