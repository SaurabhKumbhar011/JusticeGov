package com.justicegov.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.justicegov.demo.dto.CaseDocumentRequestDTO;
import com.justicegov.demo.dto.CaseDocumentResponseDTO;
import com.justicegov.demo.service.CaseDocumentService;

import org.springframework.http.MediaType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/cases/{caseId}/documents")
public class CaseDocumentController {
    @Autowired
    private CaseDocumentService caseDocumentService;

    @PostMapping
    public ResponseEntity<CaseDocumentResponseDTO> addDocument(@PathVariable Long caseId, @RequestBody CaseDocumentRequestDTO request) {
        request.setCaseId(caseId);
        CaseDocumentResponseDTO response = caseDocumentService.addDocument(request);
        return ResponseEntity.ok(response);
    }

    // File upload endpoint for PDF and other files
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CaseDocumentResponseDTO> uploadDocument(
            @PathVariable Long caseId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentType") String documentType,
            @RequestParam(value = "verificationStatus", required = false, defaultValue = "PENDING") String verificationStatus
    ) throws IOException {
        // Save file to local folder (e.g., uploads/)
        String uploadDir = "uploads";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.write(filePath, file.getBytes());

        // Prepare DTO
        CaseDocumentRequestDTO dto = new CaseDocumentRequestDTO();
        dto.setCaseId(caseId);
        dto.setDocumentType(com.justicegov.demo.model.enums.CaseDocumentType.valueOf(documentType));
        dto.setFileUrl(filePath.toString());
        dto.setUploadDate(java.time.LocalDate.now());
        dto.setVerificationStatus(com.justicegov.demo.model.enums.VerificationStatus.valueOf(verificationStatus));

        CaseDocumentResponseDTO response = caseDocumentService.addDocument(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CaseDocumentResponseDTO>> getDocuments(@PathVariable Long caseId) {
        return ResponseEntity.ok(caseDocumentService.getDocumentsByCaseId(caseId));
    }

    @GetMapping("/{docId}")
    public ResponseEntity<CaseDocumentResponseDTO> getDocument(@PathVariable Long caseId, @PathVariable Long docId) {
        return ResponseEntity.ok(caseDocumentService.getDocumentById(docId));
    }

    @DeleteMapping("/{docId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long caseId, @PathVariable Long docId) {
        caseDocumentService.deleteDocument(docId);
        return ResponseEntity.noContent().build();

    }
}
