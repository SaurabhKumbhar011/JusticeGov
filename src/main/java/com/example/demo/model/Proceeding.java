package com.example.demo.model;

import java.time.LocalDate;

import com.example.demo.model.enums.ProceedingStatus;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "Proceeding", indexes = {
        @Index(name = "idx_proceeding_hearing", columnList = "HearingID")
})
public class Proceeding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProceedingID")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "HearingID", nullable = false)
    
    @ToString.Exclude
    private Hearing hearing;

    @Lob
    @Column(name = "Notes")
    private String notes;

    @Column(name = "Date", nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 50)
    private ProceedingStatus status = ProceedingStatus.IN_SESSION;
}