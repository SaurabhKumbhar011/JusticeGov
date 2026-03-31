package com.justicegov.demo.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.justicegov.demo.dto.ComplianceRecordRequest;
import com.justicegov.demo.dto.ComplianceRecordResponse;
import com.justicegov.demo.service.ComplianceService;

import java.util.List;

@RestController
@RequestMapping("/api/compliance")
@RequiredArgsConstructor
public class ComplianceController {
    
    private final ComplianceService complianceService;

    @PostMapping("/records")
    public ResponseEntity<ComplianceRecordResponse> addRecord(@RequestBody ComplianceRecordRequest request) {
        return ResponseEntity.ok(complianceService.createRecord(request));
    }

    @GetMapping("/records")
    public ResponseEntity<List<ComplianceRecordResponse>> getAll() {
        return ResponseEntity.ok(complianceService.findAllRecords());
    }
    
    @GetMapping("/records/{id}") // http://localhost:1234/api/compliance/records/{id}
    public ResponseEntity<ComplianceRecordResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(complianceService.findRecordById(id));
    }

    @PutMapping("/records/{id}/resolve")
    public ResponseEntity<ComplianceRecordResponse> resolve(@PathVariable Long id, @RequestParam String notes) {
        return ResponseEntity.ok(complianceService.resolveViolation(id, notes));
    }
}