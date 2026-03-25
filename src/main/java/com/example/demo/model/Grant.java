package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.demo.model.enums.GrantStatus;

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
@Table(name = "research_grant", indexes = {
        @Index(name = "idx_grant_project", columnList = "ProjectID"),
        @Index(name = "idx_grant_researcher", columnList = "ResearcherID")
})
public class Grant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GrantID")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ProjectID", nullable = false)
    @ToString.Exclude
    private ResearchProject project;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ResearcherID", nullable = false)
    private User researcher;

    @Column(name = "Amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "Date", nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 50)
    private GrantStatus status = GrantStatus.ALLOCATED;
}