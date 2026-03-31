package com.justicegov.demo.model;

import java.time.LocalDate;

import com.justicegov.demo.model.enums.LegalDocType;
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
@Table(name = "LegalDocument", indexes = {
        @Index(name = "idx_legaldoc_citizen", columnList = "CitizenID"),
        @Index(name = "idx_legaldoc_lawyer", columnList = "LawyerID")
})
public class LegalDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DocumentID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CitizenID")
    @ToString.Exclude
    private Citizen citizen;

    @ManyToOne
    @JoinColumn(name = "LawyerID")
    @ToString.Exclude
    private Lawyer lawyer;

    @Enumerated(EnumType.STRING)
    @Column(name = "DocType", nullable = false, length = 50)
    private LegalDocType docType;

    @Column(name = "FileURI", nullable = false, length = 500)
    private String fileUri;

    @Column(name = "UploadedDate", nullable = false)
    private LocalDate uploadedDate;
    
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "VerificationStatus", nullable = false, length = 50)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;
}