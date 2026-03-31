package com.justicegov.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justicegov.demo.dto.CaseDocumentRequestDTO;
import com.justicegov.demo.dto.CaseDocumentResponseDTO;
import com.justicegov.demo.model.Case;
import com.justicegov.demo.model.CaseDocument;
import com.justicegov.demo.repository.CaseDocumentRepository;
import com.justicegov.demo.repository.CaseRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CaseDocumentServiceImpl implements CaseDocumentService {
    @Autowired
    private CaseDocumentRepository caseDocumentRepository;
    @Autowired
    private CaseRepository caseRepository;

    @Override
    public CaseDocumentResponseDTO addDocument(CaseDocumentRequestDTO dto) {
        Case caseRef = caseRepository.findById(dto.getCaseId())
            .orElseThrow(() -> new RuntimeException("Case not found"));
        CaseDocument doc = CaseDocument.builder()
            .caseRef(caseRef)
            .docType(dto.getDocumentType())
            .fileUri(dto.getFileUrl())
            .uploadedDate(dto.getUploadDate())
            .verificationStatus(dto.getVerificationStatus())
            .build();
        CaseDocument saved = caseDocumentRepository.save(doc);
        return toResponseDTO(saved);
    }

    @Override
    public List<CaseDocumentResponseDTO> getDocumentsByCaseId(Long caseId) {
        return caseDocumentRepository.findByCaseRefId(caseId)
                .stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public CaseDocumentResponseDTO getDocumentById(Long docId) {
        CaseDocument doc = caseDocumentRepository.findById(docId)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        String cwd = System.getProperty("user.dir");
        doc.setFileUri(cwd +"\\"+doc.getFileUri());
        return toResponseDTO(doc);
    }

    @Override
    public void deleteDocument(Long docId) {
        caseDocumentRepository.deleteById(docId);
    }

    private CaseDocumentResponseDTO toResponseDTO(CaseDocument doc) {
        CaseDocumentResponseDTO dto = new CaseDocumentResponseDTO();
        dto.setId(doc.getId());
        dto.setCaseId(doc.getCaseRef().getId());
        // dto.setDocumentName(doc.getDocumentName()); // Not present in model
        dto.setDocumentType(doc.getDocType());
        dto.setFileUrl(doc.getFileUri());
        dto.setUploadDate(doc.getUploadedDate());
        dto.setVerificationStatus(doc.getVerificationStatus());
        return dto;
    }
}
