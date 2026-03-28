package com.example.demo.model;

import java.time.LocalDate;

import com.example.demo.model.enums.ComplianceResult;
import com.example.demo.model.enums.ComplianceType;
import com.example.demo.model.enums.TargetEntityType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "ComplianceRecord", indexes = {
        @Index(name = "idx_compliance_target", columnList = "EntityID,Type")
})
public class ComplianceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ComplianceID")
    private Long id;

    @Column(name = "EntityID", nullable = false)
    private Long entityId; // polymorphic target id

    @Enumerated(EnumType.STRING)
    @Column(name = "Type", nullable = false, length = 50)
    private ComplianceType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "Result", nullable = false, length = 50)
    private ComplianceResult result;

    @Column(name = "Date", nullable = false)
    private LocalDate date;

    @Lob
    @Column(name = "Notes")
    private String notes;
    
	@Column(name = "IsResolved")
	private Boolean isResolved = false;

	@Column(name = "CorrectiveActionNotes")
	private String correctiveActionNotes;
    @Enumerated(EnumType.STRING)
    @Column(name = "EntityType", length = 50)
    private TargetEntityType entityType; // optional helper
}