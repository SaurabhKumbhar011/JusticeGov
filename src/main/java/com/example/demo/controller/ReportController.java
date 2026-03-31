package com.example.demo.controller;
import com.example.demo.dto.ReportRequestDTO;
import com.example.demo.model.Report;
import com.example.demo.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
 
	
    private static final Logger log = LoggerFactory.getLogger(ReportController.class);
 
    private final ReportService reportService;
 
    @PostMapping("/generate")
    public Report generateReport(@RequestBody ReportRequestDTO req) {
 
        log.info("API called: Generate Report");
 
        return reportService.generateReport(req);
    }
    
 
    @GetMapping("/{id}")
    public Report getReport(@PathVariable Long id) {
 
        log.info("API called: Get Report by ID {}", id);
 
        
        return reportService.getReportById(id);
    }
}