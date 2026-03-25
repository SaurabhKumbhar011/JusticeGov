package com.example.demo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.enums.CaseStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@ToString
@Entity
@Table(name = "cases") // table name is safe (not the reserved keyword 'case')
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CaseID")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CitizenID", nullable = false)
    @ToString.Exclude
    private Citizen citizen;

    @ManyToOne
    @JoinColumn(name = "LawyerID")
    @ToString.Exclude
    private Lawyer lawyer;

    @Column(name = "Title", nullable = false, length = 300)
    private String title;

    @Lob
    @Column(name = "Description")
    private String description;

    @Column(name = "FiledDate", nullable = false)
    private LocalDate filedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 50)
    private CaseStatus status = CaseStatus.FILED;

    @OneToMany(mappedBy = "caseRef", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<CaseDocument> documents = new ArrayList<>();

    @OneToMany(mappedBy = "caseRef", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Hearing> hearings = new ArrayList<>();

    @OneToMany(mappedBy = "caseRef", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Judgement> judgements = new ArrayList<>();

    @OneToMany(mappedBy = "caseRef", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<CourtOrder> orders = new ArrayList<>();
}
