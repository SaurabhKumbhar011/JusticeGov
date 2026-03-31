package com.justicegov.demo.model;

import java.time.LocalDate;

import com.justicegov.demo.model.enums.ApplicationStatus;

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
@Table(name = "GrantApplication", indexes = {
        @Index(name = "idx_grantapp_researcher", columnList = "ResearcherID"),
        @Index(name = "idx_grantapp_project", columnList = "ProjectID")
})
public class GrantApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ApplicationID")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ResearcherID", nullable = false)
    @ToString.Exclude
    private User researcher;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ProjectID", nullable = false)
    @ToString.Exclude
    private ResearchProject project;

    @Column(name = "SubmittedDate", nullable = false)
    private LocalDate submittedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 50)
    private ApplicationStatus status = ApplicationStatus.SUBMITTED;
}