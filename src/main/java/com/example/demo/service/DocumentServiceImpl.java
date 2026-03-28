package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.model.*;
import com.example.demo.model.enums.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository docRepo;
    private final CitizenRepository cRepo;
    private final LawyerRepository lRepo;

    @Override
    @Transactional
    public String upload(DocumentUploadReq req) {
        // 1. Case-Insensitive Document Type Validation
        LegalDocType type;
        String input = req.getDocType().toUpperCase().replace("_", "").trim();

        if (input.contains("IDPROOF")) {
            type = LegalDocType.ID_PROOF;
        } else if (input.contains("BARCERTIFICATE")) {
            type = LegalDocType.BAR_CERTIFICATE;
        } else {
            throw new IllegalArgumentException("Invalid DocType: " + req.getDocType() + ". Use 'IDProof' or 'BarCertificate'.");
        }

        // 2. Initialize LegalDocument
        LegalDocument doc = new LegalDocument();
        doc.setDocType(type);
        doc.setFileUri(req.getFileUri());
        doc.setUploadedDate(LocalDate.now());
        doc.setVerificationStatus(VerificationStatus.PENDING);

        // 3. Link to Entity using ownerId from DTO
        if ("CITIZEN".equalsIgnoreCase(req.getRole())) {
            Citizen c = cRepo.findById(req.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Citizen not found with ID: " + req.getOwnerId()));
            doc.setCitizen(c);
        } else if ("LAWYER".equalsIgnoreCase(req.getRole())) {
            Lawyer l = lRepo.findById(req.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Lawyer not found with ID: " + req.getOwnerId()));
            doc.setLawyer(l);
        } else {
            throw new IllegalArgumentException("Invalid Role. Use 'LAWYER' or 'CITIZEN'.");
        }

        docRepo.save(doc);
        return "Document uploaded and linked successfully to " + req.getRole() + " ID: " + req.getOwnerId();
    }

    @Override
    @Transactional
    public String verify(Long docId, String status) {
        LegalDocument doc = docRepo.findById(docId)
                .orElseThrow(() -> new RuntimeException("Document ID " + docId + " not found."));

        try {
            doc.setVerificationStatus(VerificationStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status. Use: VERIFIED or REJECTED.");
        }

        docRepo.save(doc);
        return "Verification status updated to: " + status.toUpperCase();
    }

    @Override
    public List<DocumentResp> getByEntity(Long id, String role) {
        // Find documents based on role
        List<LegalDocument> docs = "CITIZEN".equalsIgnoreCase(role) ? 
                                    docRepo.findByCitizenId(id) : docRepo.findByLawyerId(id);

        List<DocumentResp> responseList = new ArrayList<>();
        for (LegalDocument d : docs) {
            DocumentResp dto = new DocumentResp();
            dto.setId(d.getId());
            dto.setDocType(d.getDocType().name());
            dto.setFileUri(d.getFileUri());
            dto.setVerificationStatus(d.getVerificationStatus().name());
            responseList.add(dto);
        }
        return responseList;
    }
}