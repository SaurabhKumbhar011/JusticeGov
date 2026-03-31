package com.justicegov.demo.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Long userId;
    private String name;
    private String email;
    private String role;
    private String status;
}