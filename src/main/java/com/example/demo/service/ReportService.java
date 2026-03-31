package com.example.demo.service;


import com.example.demo.dto.ReportRequestDTO;
import com.example.demo.model.Report;
 
public interface ReportService {
 
    Report generateReport(ReportRequestDTO req);
    Report getReportById(Long reportId);
}
 