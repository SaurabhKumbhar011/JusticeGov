package com.justicegov.demo.service;

import java.util.List;

import com.justicegov.demo.dto.CaseRequestDTO;
import com.justicegov.demo.dto.CaseResponseDTO;

public interface CaseService {
    CaseResponseDTO fileCase(CaseRequestDTO caseRequestDTO);
    List<CaseResponseDTO> getAllCases();
    CaseResponseDTO getCaseById(Long id);
    // Additional methods can be added as needed
}