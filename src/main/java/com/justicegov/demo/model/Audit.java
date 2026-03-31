package com.justicegov.demo.model;

import java.time.LocalDate;

import com.justicegov.demo.model.enums.AuditStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Data
@Entity
@Table(name = "Audit", indexes = {
        @Index(name = "idx_audit_officer", columnList = "OfficerID")
})
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AuditID")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "OfficerID", nullable = false)
    private User officer; // role=COMPLIANCE

    @Column(name = "Scope", nullable = false, length = 300)
    private String scope;

    @Lob
    @Column(name = "Findings")
    private String findings;

    @Column(name = "Date", nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 50)
    private AuditStatus status = AuditStatus.OPEN;
}