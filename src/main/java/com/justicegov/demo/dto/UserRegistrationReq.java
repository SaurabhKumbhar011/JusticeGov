package com.justicegov.demo.dto;

import com.justicegov.demo.model.enums.Role;

import lombok.Data;

@Data
public class UserRegistrationReq {
    private String name;
    private String email;
    private String phone;
    private String password;
    private Role role; 
}