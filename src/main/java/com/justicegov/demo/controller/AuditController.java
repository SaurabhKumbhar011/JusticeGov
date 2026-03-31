package com.justicegov.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.justicegov.demo.dto.AuditRequest;
import com.justicegov.demo.dto.AuditResponse;
import com.justicegov.demo.service.AuditService;

import java.util.List;

@RestController
@RequestMapping("/api/audits") // Updated for consistency
@RequiredArgsConstructor
public class AuditController {
    private final AuditService auditService;

    @PostMapping
    public ResponseEntity<AuditResponse> create(@RequestBody AuditRequest req) {
        return ResponseEntity.ok(auditService.createAudit(req));
    }

    @GetMapping
    public ResponseEntity<List<AuditResponse>> list() {
        return ResponseEntity.ok(auditService.getAllAudits());
    }
    
    @GetMapping("/{id}") // http://localhost:1234/api/audits/{id}
    public ResponseEntity<AuditResponse> getById(@PathVariable Long id) {
        // You'll need to add this method to your AuditService and AuditServiceImpl
        return ResponseEntity.ok(auditService.getAuditById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuditResponse> conduct(@PathVariable Long id, @RequestBody AuditRequest req) {
        return ResponseEntity.ok(auditService.updateAudit(id, req));
    }
}