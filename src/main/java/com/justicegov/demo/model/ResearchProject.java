package com.justicegov.demo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.justicegov.demo.model.enums.ProjectStatus;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@ToString(exclude = {"applications", "grants"})
@Entity
@Table(name = "ResearchProject", indexes = {
        @Index(name = "idx_project_researcher", columnList = "ResearcherID")
})
public class ResearchProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectID")
    private Long id;

    @Column(name = "Title", nullable = false, length = 300)
    private String title;

    @Lob
    @Column(name = "Description")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ResearcherID", nullable = false)
    private User researcher; // role=RESEARCHER

    @Column(name = "StartDate")
    private LocalDate startDate;

    @Column(name = "EndDate")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 50)
    private ProjectStatus status = ProjectStatus.DRAFT;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<GrantApplication> applications = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Grant> grants = new ArrayList<>();
}