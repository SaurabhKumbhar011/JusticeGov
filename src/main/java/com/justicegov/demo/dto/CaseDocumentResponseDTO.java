package com.justicegov.demo.dto;

import lombok.Data;
import java.time.LocalDate;

import com.justicegov.demo.model.enums.CaseDocumentType;
import com.justicegov.demo.model.enums.VerificationStatus;

@Data
public class CaseDocumentResponseDTO {
    private Long id;
    private Long caseId;
    private String documentName;
    private CaseDocumentType documentType;
    private String fileUrl;
    private LocalDate uploadDate;
    private VerificationStatus verificationStatus;
}
