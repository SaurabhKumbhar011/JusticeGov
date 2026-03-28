package com.example.demo.dto;

import com.example.demo.model.enums.CaseDocumentType;
import com.example.demo.model.enums.VerificationStatus;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CaseDocumentRequestDTO {
    private Long caseId;
    private String documentName;
    private CaseDocumentType documentType;
    private String fileUrl;
    private LocalDate uploadDate;
    private VerificationStatus verificationStatus;
}
