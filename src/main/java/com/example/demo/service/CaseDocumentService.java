package com.example.demo.service;

import com.example.demo.dto.CaseDocumentRequestDTO;
import com.example.demo.dto.CaseDocumentResponseDTO;
import java.util.List;

public interface CaseDocumentService {
    CaseDocumentResponseDTO addDocument(CaseDocumentRequestDTO dto);
    List<CaseDocumentResponseDTO> getDocumentsByCaseId(Long caseId);
    CaseDocumentResponseDTO getDocumentById(Long docId);
    void deleteDocument(Long docId);
}
