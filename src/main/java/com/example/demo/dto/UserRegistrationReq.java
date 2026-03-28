package com.example.demo.dto;

import com.example.demo.model.enums.Role; // <-- Correct import

import lombok.Data;

@Data
public class UserRegistrationReq {
    private String name;
    private String email;
    private String phone;
    private String password;
    private Role role; 
}