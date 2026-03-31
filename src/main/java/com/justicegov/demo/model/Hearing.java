package com.justicegov.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.justicegov.demo.model.enums.HearingStatus;

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
@ToString(exclude = {"caseRef", "proceedings"})
@Entity
@Table(name = "Hearing", indexes = {
        @Index(name = "idx_hearing_case", columnList = "CaseID"),
        @Index(name = "idx_hearing_judge", columnList = "JudgeID")
})
public class Hearing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HearingID")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CaseID", nullable = false)
    private Case caseRef;

    @ManyToOne(optional = false)
    @JoinColumn(name = "JudgeID", nullable = false)
    private User judge; // role=JUDGE

    @Column(name = "DateTime", nullable = false)
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 50)
    private HearingStatus status = HearingStatus.SCHEDULED;

    @OneToMany(mappedBy = "hearing", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Proceeding> proceedings = new ArrayList<>();
}