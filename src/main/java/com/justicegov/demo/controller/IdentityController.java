package com.justicegov.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justicegov.demo.dto.AuthRequest;
import com.justicegov.demo.dto.AuthResponse;
import com.justicegov.demo.dto.UserRegistrationReq;
import com.justicegov.demo.dto.UserResponse;
import com.justicegov.demo.model.AuditLog;
import com.justicegov.demo.service.IdentityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class IdentityController {

    private final IdentityService identityService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegistrationReq request) {
        return ResponseEntity.ok(identityService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        AuthResponse response = identityService.authenticate(req);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/logs")
    public ResponseEntity<List<AuditLog>> getLogs(@RequestHeader("admin-id") Long adminId) {
        return ResponseEntity.ok(identityService.getAllLogs(adminId));
    }

    @PutMapping("/suspend/{email}")
    public ResponseEntity<String> suspendUser(@PathVariable String email) {
        return ResponseEntity.ok(identityService.suspendUser(email));
    }
    
    @PutMapping("/reactivate/{email}")
    public ResponseEntity<String> reactivateUser(@PathVariable String email) {
        return ResponseEntity.ok(identityService.reactivateUser(email));
    }
}