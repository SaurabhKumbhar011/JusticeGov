package com.justicegov.demo.service;

import java.util.List;

import com.justicegov.demo.dto.CaseDocumentRequestDTO;
import com.justicegov.demo.dto.CaseDocumentResponseDTO;

public interface CaseDocumentService {
    CaseDocumentResponseDTO addDocument(CaseDocumentRequestDTO dto);
    List<CaseDocumentResponseDTO> getDocumentsByCaseId(Long caseId);
    CaseDocumentResponseDTO getDocumentById(Long docId);
    void deleteDocument(Long docId);
}
