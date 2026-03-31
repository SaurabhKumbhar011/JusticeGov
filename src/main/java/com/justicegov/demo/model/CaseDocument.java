package com.justicegov.demo.model;

import java.time.LocalDate;

import com.justicegov.demo.model.enums.CaseDocumentType;
import com.justicegov.demo.model.enums.VerificationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "CaseDocument", indexes = {
        @Index(name = "idx_casedoc_case", columnList = "CaseID")
})
public class CaseDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DocumentID")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CaseID", nullable = false)
    @ToString.Exclude
    private Case caseRef;

    @Enumerated(EnumType.STRING)
    @Column(name = "DocType", nullable = false, length = 50)
    private CaseDocumentType docType;

    @Column(name = "FileURI", nullable = false, length = 500)
    private String fileUri;

    @Column(name = "UploadedDate", nullable = false)
    private LocalDate uploadedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "VerificationStatus", nullable = false, length = 50)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;
}