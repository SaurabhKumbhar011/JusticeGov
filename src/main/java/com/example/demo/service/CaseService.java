package com.example.demo.service;

import com.example.demo.dto.CaseRequestDTO;
import com.example.demo.dto.CaseResponseDTO;
import java.util.List;

public interface CaseService {
    CaseResponseDTO fileCase(CaseRequestDTO caseRequestDTO);
    List<CaseResponseDTO> getAllCases();
    CaseResponseDTO getCaseById(Long id);
    // Additional methods can be added as needed
}